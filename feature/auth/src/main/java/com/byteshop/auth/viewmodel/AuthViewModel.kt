package com.byteshop.auth.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.byteshop.auth.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


internal sealed interface AuthViewState {
    object Loading : AuthViewState
    data class Authenticated(val user: User) : AuthViewState
    object Unauthenticated : AuthViewState
}

internal class AuthViewModel : ViewModel() {
    private val _viewState = MutableStateFlow<AuthViewState>(AuthViewState.Loading)
    val viewState = _viewState.asStateFlow()

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
}