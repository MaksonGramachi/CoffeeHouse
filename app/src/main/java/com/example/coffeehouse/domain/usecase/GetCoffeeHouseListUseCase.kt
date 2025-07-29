package com.example.coffeehouse.domain.usecase

import com.example.coffeehouse.domain.model.CoffeeHouse
import com.example.coffeehouse.domain.repository.CoffeeHouseRepository
import javax.inject.Inject

class GetCoffeeHouseListUseCase @Inject constructor(
    private val coffeeHouseRepository: CoffeeHouseRepository
) {
    suspend operator fun invoke(): List<CoffeeHouse> {
        return coffeeHouseRepository.getCoffeeHouseList()
    }
}