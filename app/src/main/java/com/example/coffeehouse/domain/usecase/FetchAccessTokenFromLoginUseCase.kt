package com.example.coffeehouse.domain.usecase

import com.example.coffeehouse.domain.repository.CoffeeHouseRepository
import com.example.coffeehouse.data.TokenDataStore
import javax.inject.Inject

class FetchAccessTokenFromLoginUseCase @Inject constructor(
    private val coffeeHouseRepository: CoffeeHouseRepository,
    private val dataStore: TokenDataStore
) {
    suspend operator fun invoke(login: String, password: String) {
        dataStore.updateToken(coffeeHouseRepository.getAuthTokenFromLogin(login, password).token)
    }
}