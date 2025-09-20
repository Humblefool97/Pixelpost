package com.byteshop.auth

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val email: String,
    val photoUrl: String
) : Parcelable
