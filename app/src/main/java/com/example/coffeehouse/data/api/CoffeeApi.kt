package com.example.coffeehouse.data.api

import com.example.coffeehouse.data.model.AuthRequest
import com.example.coffeehouse.data.model.AuthTokenDto
import com.example.coffeehouse.data.model.CoffeeHouseDto
import com.example.coffeehouse.data.model.CoffeeHouseMenuDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CoffeeApi {
    @GET("locations")
    suspend fun getCoffeeHouseList(): Response<List<CoffeeHouseDto>>

    @GET("location/{id}/menu")
    suspend fun getCoffeeHouseMenu(
        @Path("id") id: Long,
    ): Response<List<CoffeeHouseMenuDto>>

    @POST("auth/login")
    suspend fun loginUser(@Body authRequest: AuthRequest): Response<AuthTokenDto>

    @POST("auth/register")
    suspend fun registerUser(@Body authRequest: AuthRequest): Response<AuthTokenDto>

}