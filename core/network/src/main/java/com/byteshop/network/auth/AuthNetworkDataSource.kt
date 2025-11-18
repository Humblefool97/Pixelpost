package com.byteshop.network.auth

import com.byteshop.network.model.NetworkAuthUser

interface AuthNetworkDataSource {
    suspend fun signIn(email: String, password: String): Result<NetworkAuthUser>
    suspend fun signOut(): Result<Unit>
    suspend fun getCurrentUser(): NetworkAuthUser
}