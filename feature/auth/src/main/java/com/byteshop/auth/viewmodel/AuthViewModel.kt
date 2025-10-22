package com.byteshop.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.byteshop.auth.User
import com.byteshop.auth.validation.AuthValidationError
import com.byteshop.auth.validation.AuthValidator
import com.byteshop.auth.validation.ValidationResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


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

internal class AuthViewModel : ViewModel() {
    private val _viewState = MutableStateFlow<AuthViewState>(AuthViewState.Idle)
    val viewState = _viewState.asStateFlow()
    private val _formState = MutableStateFlow(AuthFormState())
    val formState = _formState.asStateFlow()


    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun authenticateUser(
        email: String,
        password: String
    ) {
        auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    Log.d("AuthViewModel", "authenticateUser: ${task.isSuccessful}")
                else
                    Log.d("AuthViewModel", "authenticateUser: ${task.exception}")
            }
    }

    fun onUsernameChange(value: String) {
        //Validate
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
        //Validate
        val result = AuthValidator.validatePassword(value)
        _formState.update {
            it.copy(
                password = value,
                passwordError = if (result is ValidationResult.Error) result.error else null,
                isFormValid = result is ValidationResult.Success && it.usernameOrEmailOrPhoneError == null
            )
        }
    }
}