package com.example.coffeehouse.data.model

import com.google.gson.annotations.SerializedName

data class AuthTokenDto (
    @SerializedName("token")
    val token: String,
    @SerializedName("tokenLifeTime")
    val tokenLifeTime: Long,
    )