package com.example.coffeehouse.domain.usecase

import com.example.coffeehouse.domain.model.CoffeeHouseMenu
import com.example.coffeehouse.domain.repository.CoffeeHouseRepository
import javax.inject.Inject

class GetCoffeeHouseMenuUseCase @Inject constructor(
    private val coffeeHouseRepository: CoffeeHouseRepository
) {
    suspend operator fun invoke(id: Long): List<CoffeeHouseMenu> {
        return coffeeHouseRepository.getCoffeeHouseMenu(id)
    }
}