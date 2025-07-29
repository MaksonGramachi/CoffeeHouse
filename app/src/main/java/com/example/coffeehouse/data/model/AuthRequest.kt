package com.example.coffeehouse.data.model

import com.google.gson.annotations.SerializedName

data class AuthRequest(
    val login: String,
    val password: String,
)
