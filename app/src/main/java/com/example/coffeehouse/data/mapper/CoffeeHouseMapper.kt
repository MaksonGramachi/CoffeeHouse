package com.example.coffeehouse.data.mapper

import com.example.coffeehouse.data.model.CoffeeHouseDto
import com.example.coffeehouse.domain.model.CoffeeHouse

fun CoffeeHouseDto.toDomain() = CoffeeHouse(
    id = id!!,
    name = name.orEmpty(),
    point = CoffeeHouse.Point(point?.latitude, point?.longitude)
)