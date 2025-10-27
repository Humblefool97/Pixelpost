package com.byteshop.data.model

import com.byteshop.network.model.NetworkAuthUser

fun NetworkAuthUser.toUser() = User(
    id = uid,
    email = email,
    displayName = displayName,
    photoUrl = photoUrl
)