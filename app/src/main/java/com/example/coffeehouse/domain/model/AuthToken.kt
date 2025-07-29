package com.example.coffeehouse.domain.model

data class AuthToken(
    val token: String,
    val tokenLifeTime: Long,
)
