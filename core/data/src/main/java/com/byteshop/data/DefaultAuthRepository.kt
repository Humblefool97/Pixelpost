package com.byteshop.data

import com.byteshop.data.model.User
import com.byteshop.data.model.toUser
import com.byteshop.network.auth.AuthNetworkDataSource

class DefaultAuthRepository(
    private val authNetworkDataSource: AuthNetworkDataSource
): AuthRepository {
    override suspend fun signIn(
        email: String,
        password: String
    ): Result<User> {
        return authNetworkDataSource.signIn(
            email = email,
            password = password
        ).map {
            it.toUser()
        }
    }

    override suspend fun getCurrentUser(): User {
        return authNetworkDataSource.getCurrentUser().toUser()
    }
}