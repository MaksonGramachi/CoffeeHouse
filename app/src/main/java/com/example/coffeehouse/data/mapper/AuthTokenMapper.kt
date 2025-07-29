package com.example.coffeehouse.data.mapper

import com.example.coffeehouse.data.model.AuthTokenDto
import com.example.coffeehouse.domain.model.AuthToken

fun AuthTokenDto.toDomain() = AuthToken(
    token = token,
    tokenLifeTime = tokenLifeTime
)