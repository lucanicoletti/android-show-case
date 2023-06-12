package com.lucanicoletti.betdeck.di.module

import com.lucanicoletti.data.CardsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    fun providesWeatherApi(retrofit: Retrofit): CardsApi = retrofit.create(CardsApi::class.java)
}