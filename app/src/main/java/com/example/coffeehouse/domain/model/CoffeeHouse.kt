package com.example.coffeehouse.domain.model

data class CoffeeHouse(
    val id: Long,
    val name: String,
    val point: Point?,
) {
    data class Point(
        val latitude: Double?,
        val longitude: Double?,
    )
}
