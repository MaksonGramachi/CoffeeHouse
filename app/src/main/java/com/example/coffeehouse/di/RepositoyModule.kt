package com.example.coffeehouse.di

import com.example.coffeehouse.data.repository.CoffeeHouseRepositoryImpl
import com.example.coffeehouse.domain.repository.CoffeeHouseRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindCoffeeHouseRepository(impl: CoffeeHouseRepositoryImpl): CoffeeHouseRepository
}
