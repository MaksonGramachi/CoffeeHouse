package com.example.coffeehouse.domain.usecase

import com.example.coffeehouse.data.TokenDataStore
import com.example.coffeehouse.domain.repository.CoffeeHouseRepository
import javax.inject.Inject

class FetchAccessTokenFromRegisterUseCase @Inject constructor(
    private val coffeeHouseRepository: CoffeeHouseRepository,
    private val dataStore: TokenDataStore
) {
    suspend operator fun invoke(login: String, password: String) {
        dataStore.updateToken(coffeeHouseRepository.getAuthTokenFromRegister(login, password).token)
    }
}