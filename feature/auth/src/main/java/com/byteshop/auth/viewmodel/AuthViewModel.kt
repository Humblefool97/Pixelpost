package com.byteshop.auth.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewModelScope
import com.byteshop.auth.User
import com.byteshop.auth.validation.AuthValidationError
import com.byteshop.auth.validation.AuthValidator
import com.byteshop.auth.validation.ValidationResult
import com.byteshop.data.AuthRepository
import com.byteshop.data.DefaultAuthRepository
import com.byteshop.network.auth.FirebaseAuthNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


internal sealed interface AuthViewState {
    object Idle : AuthViewState
    object Loading : AuthViewState
    data class Authenticated(val user: User) : AuthViewState
    object Unauthenticated : AuthViewState
}

data class AuthFormState(
    val usernameOrEmailOrPhone: String = "",
    val password: String = "",
    val usernameOrEmailOrPhoneError: AuthValidationError? = null,
    val passwordError: AuthValidationError? = null,
    val isFormValid: Boolean = false
)

internal class AuthViewModel(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val _viewState = MutableStateFlow<AuthViewState>(AuthViewState.Idle)
    val viewState = _viewState.asStateFlow()
    
    // Initialize form state from SavedStateHandle for process death recovery
    private val _formState = MutableStateFlow(
        AuthFormState(
            usernameOrEmailOrPhone = savedStateHandle.get<String>(KEY_USERNAME) ?: "",
            password = savedStateHandle.get<String>(KEY_PASSWORD) ?: ""
        )
    )
    val formState = _formState.asStateFlow()


    fun authenticateUser(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _viewState.emit(AuthViewState.Loading)

            authRepository
                .signIn(email, password)
                .onSuccess {
                    _viewState.emit(AuthViewState.Authenticated(it.toUser()))
                }
                .onFailure {
                    _viewState.emit(AuthViewState.Unauthenticated)
                }
        }

    }

    private fun com.byteshop.data.model.User.toUser() = User(
        name = displayName!!,
        email = email!!,
        photoUrl = photoUrl!!
    )

    fun onUsernameChange(value: String) {
        // Save to SavedStateHandle for process death recovery
        savedStateHandle[KEY_USERNAME] = value
        
        // Validate
        val result = AuthValidator.validateUsernameOrEmailOrPhone(value)
        _formState.update {
            it.copy(
                usernameOrEmailOrPhone = value,
                usernameOrEmailOrPhoneError = if (result is ValidationResult.Error) result.error else null,
                isFormValid = result is ValidationResult.Success && it.passwordError == null
            )
        }
    }

    fun onPasswordChange(value: String) {
        // Save to SavedStateHandle for process death recovery
        savedStateHandle[KEY_PASSWORD] = value
        
        // Validate
        val result = AuthValidator.validatePassword(value)
        _formState.update {
            it.copy(
                password = value,
                passwordError = if (result is ValidationResult.Error) result.error else null,
                isFormValid = result is ValidationResult.Success && it.usernameOrEmailOrPhoneError == null
            )
        }
    }

    companion object {
        // State restoration keys
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
        
        /**
         * Factory for creating AuthViewModel instances using modern Android practices.
         * Uses the viewModelFactory DSL for cleaner, more idiomatic code.
         * 
         * This builds the dependency chain:
         * FirebaseAuthNetworkDataSource -> DefaultAuthRepository -> AuthViewModel
         */
        fun Factory(
            authRepository: AuthRepository? = null
        ): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repository = authRepository ?: run {
                    val authNetworkDataSource = FirebaseAuthNetworkDataSource.getInstance(Dispatchers.IO)
                    DefaultAuthRepository.getInstance(
                        authNetworkDataSource = authNetworkDataSource,
                        dispatcher = Dispatchers.IO
                    )
                }
                AuthViewModel(
                    authRepository = repository,
                    savedStateHandle = createSavedStateHandle()
                )
            }
        }
    }
}