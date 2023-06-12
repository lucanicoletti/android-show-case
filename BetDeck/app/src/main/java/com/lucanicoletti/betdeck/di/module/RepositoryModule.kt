package com.lucanicoletti.betdeck.di.module

import com.lucanicoletti.data.repository.CardsRepositoryImpl
import com.lucanicoletti.domain.repository.CardsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindWeatherRepository(repository: CardsRepositoryImpl): CardsRepository
}