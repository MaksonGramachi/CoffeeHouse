package com.example.coffeehouse.data.mapper

import com.example.coffeehouse.data.model.CoffeeHouseMenuDto
import com.example.coffeehouse.domain.model.CoffeeHouseMenu

fun CoffeeHouseMenuDto.toDomain() = CoffeeHouseMenu(
    id = id!!,
    name = name.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    price = price
)
