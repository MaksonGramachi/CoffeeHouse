package com.example.coffeehouse.data.repository

import com.example.coffeehouse.data.api.CoffeeApi
import com.example.coffeehouse.data.mapper.toDomain
import com.example.coffeehouse.data.model.AuthRequest
import com.example.coffeehouse.domain.NullBodyException
import com.example.coffeehouse.domain.UserAlreadyExistsException
import com.example.coffeehouse.domain.UserNotExistsException
import com.example.coffeehouse.domain.UserTokenIsNotAvailableException
import com.example.coffeehouse.domain.model.AuthToken
import com.example.coffeehouse.domain.model.CoffeeHouse
import com.example.coffeehouse.domain.model.CoffeeHouseMenu
import com.example.coffeehouse.domain.repository.CoffeeHouseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoffeeHouseRepositoryImpl @Inject constructor(
    private val api: CoffeeApi
): CoffeeHouseRepository {

    private suspend fun <T> handleApiCall(apiCall: suspend () -> Response<T>): T {
        val response = apiCall()
        return when {
            response.isSuccessful -> {
                response.body() ?: throw NullBodyException("Response body is null")
            }
            response.code() == 404 -> {
                throw UserNotExistsException("Not found")
            }
            response.code() == 406 -> {
                throw UserAlreadyExistsException("User with this login is logged in")
            }
            response.code() == 401 -> {
                throw UserTokenIsNotAvailableException("User token time out")
            }
            else -> {
                throw Exception(
                    response.code().toString() + " - " + response.message()
                )
            }
        }
    }

    override suspend fun getAuthTokenFromLogin(login: String, password: String): AuthToken = withContext(Dispatchers.IO) {
        val authTokenDto = handleApiCall { api.loginUser(AuthRequest(login, password)) }
        authTokenDto.toDomain()
    }

    override suspend fun getAuthTokenFromRegister(login: String, password: String): AuthToken = withContext(Dispatchers.IO) {
        val authTokenDto = handleApiCall { api.registerUser(AuthRequest(login, password)) }
        authTokenDto.toDomain()
    }

    override suspend fun getCoffeeHouseList(): List<CoffeeHouse> = withContext(Dispatchers.IO) {
        val coffeeHouseDto = handleApiCall { api.getCoffeeHouseList() }
        coffeeHouseDto.map { it.toDomain() }
    }

    override suspend fun getCoffeeHouseMenu(id: Long): List<CoffeeHouseMenu> = withContext(Dispatchers.IO) {
        val coffeeHouseMenuDto = handleApiCall { api.getCoffeeHouseMenu(id) }
        coffeeHouseMenuDto.map { it.toDomain() }
    }
}