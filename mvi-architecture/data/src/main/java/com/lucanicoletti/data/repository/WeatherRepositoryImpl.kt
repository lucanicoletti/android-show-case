package com.lucanicoletti.data.repository

import com.lucanicoletti.data.WeatherApi
import com.lucanicoletti.data.entity.CurrentWeather
import com.lucanicoletti.domain.model.WeatherModel
import com.lucanicoletti.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherApi: WeatherApi,
) : WeatherRepository {
    override suspend fun getCurrentWeather(latitude: Float, longitude: Float): WeatherModel? =
        weatherApi.getCurrentWeather(latitude, longitude).currentWeather?.mapToDomain()

}

private fun CurrentWeather.mapToDomain(): WeatherModel = WeatherModel(
    this.temperature,
    this.windSpeed,
    this.weatherCode,
    this.windDirection,
    this.isDay,
    this.time,
)