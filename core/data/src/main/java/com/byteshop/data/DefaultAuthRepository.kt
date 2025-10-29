package com.byteshop.data

import com.byteshop.data.model.User
import com.byteshop.data.model.toUser
import com.byteshop.network.auth.AuthNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DefaultAuthRepository(
    private val authNetworkDataSource: AuthNetworkDataSource,
    private val dispatcher: CoroutineDispatcher
) : AuthRepository {
    override suspend fun signIn(
        email: String,
        password: String
    ): Result<User> {
        return withContext(dispatcher) {
            authNetworkDataSource.signIn(
                email = email,
                password = password
            ).map {
                it.toUser()
            }
        }
    }

    override suspend fun getCurrentUser(): User {
        return authNetworkDataSource.getCurrentUser().toUser()
    }

    companion object {
        @Volatile
        private var instance: DefaultAuthRepository? = null

        fun getInstance(
            authNetworkDataSource: AuthNetworkDataSource,
            dispatcher: CoroutineDispatcher
        ): DefaultAuthRepository {
            return instance ?: synchronized(this) {
                instance ?: DefaultAuthRepository(
                    authNetworkDataSource = authNetworkDataSource,
                    dispatcher = dispatcher
                )
            }
        }
    }
}