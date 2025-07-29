package com.example.coffeehouse.domain.repository

import com.example.coffeehouse.domain.model.AuthToken
import com.example.coffeehouse.domain.model.CoffeeHouse
import com.example.coffeehouse.domain.model.CoffeeHouseMenu

interface CoffeeHouseRepository {
    suspend fun getCoffeeHouseList(): List<CoffeeHouse>
    suspend fun getAuthTokenFromLogin(login: String, password: String): AuthToken
    suspend fun getAuthTokenFromRegister(login: String, password: String): AuthToken
    suspend fun getCoffeeHouseMenu(id: Long): List<CoffeeHouseMenu>
}