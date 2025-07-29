package com.example.coffeehouse.ui.menu.model

import com.example.coffeehouse.domain.model.CoffeeHouseMenu

data class ProductInfo (
    val productCard: CoffeeHouseMenu = CoffeeHouseMenu(),
    val productCount: UInt = 0u
)
