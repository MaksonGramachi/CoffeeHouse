package com.example.coffeehouse.data.model


import com.google.gson.annotations.SerializedName

data class CoffeeHouseDto(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("point")
    val point: Point?,
) {
    data class Point(
        @SerializedName("latitude")
        val latitude: Double?,
        @SerializedName("longitude")
        val longitude: Double?,
    )
}