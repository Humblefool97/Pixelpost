package com.byteshop.network.auth

import com.byteshop.network.model.NetworkAuthUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class FirebaseAuthNetworkDataSource : AuthNetworkDataSource {
    private val auth = FirebaseAuth.getInstance()

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<NetworkAuthUser> {
        return try {
            val result = auth
                .signInWithEmailAndPassword(email, password)
                .await()
            val user = result.user?.toNetworkUser()
            user?.let {
                Result.success(it)
            } ?: run {
                Result.failure(Exception("User is null"))
            }
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun signOut(): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): NetworkAuthUser {
        TODO("Not yet implemented")
    }
}

private fun FirebaseUser?.toNetworkUser() = NetworkAuthUser(
    uid = this?.uid ?: "",
    email = this?.email,
    displayName = this?.displayName,
    photoUrl = this?.photoUrl?.toString()
)
