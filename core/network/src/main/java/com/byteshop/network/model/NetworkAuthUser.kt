package com.byteshop.network.model

data class NetworkAuthUser(
    val uid: String,
    val email: String?,
    val displayName: String?,
    val photoUrl: String?
)
