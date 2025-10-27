package com.byteshop.data

import com.byteshop.data.model.User

interface AuthRepository{
    suspend fun signIn(email: String, password: String): Result<User>
    suspend fun getCurrentUser(): User
}