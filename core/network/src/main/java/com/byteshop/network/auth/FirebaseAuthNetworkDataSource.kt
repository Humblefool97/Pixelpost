package com.byteshop.network.auth

import com.byteshop.network.model.NetworkAuthUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.concurrent.Volatile

class FirebaseAuthNetworkDataSource(
    private val dispatcher: CoroutineDispatcher
) : AuthNetworkDataSource {
    private val auth = FirebaseAuth.getInstance()

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<NetworkAuthUser> {
        return withContext(dispatcher) {
            try {
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
    }

    override suspend fun signOut(): Result<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): NetworkAuthUser {
        TODO("Not yet implemented")
    }

    companion object {
        @Volatile
        private var instance: FirebaseAuthNetworkDataSource? = null

        fun getInstance(dispatcher: CoroutineDispatcher): FirebaseAuthNetworkDataSource {
            return instance ?: synchronized(this) {
                instance ?: FirebaseAuthNetworkDataSource(dispatcher).also {
                    instance = it
                }
            }
        }
    }
}

private fun FirebaseUser?.toNetworkUser() = NetworkAuthUser(
    uid = this?.uid ?: "",
    email = this?.email,
    displayName = this?.displayName,
    photoUrl = this?.photoUrl?.toString()
)
