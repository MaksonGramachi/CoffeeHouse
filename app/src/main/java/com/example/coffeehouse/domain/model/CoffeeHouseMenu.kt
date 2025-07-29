package com.example.coffeehouse.domain.model

data class CoffeeHouseMenu (
    val id: Long = 0,
    val name: String = "",
    val imageUrl: String = "",
    val price: Float? = null,
)