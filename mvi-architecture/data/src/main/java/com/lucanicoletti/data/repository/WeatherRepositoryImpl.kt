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
    temperature = this.temperature ?: 0.0,
    windSpeed = this.windSpeed ?: 0.0,
    windDirection = this.windDirection ?: 0,
    weatherDescription = mapWeatherCodeToDescription.getOrDefault(
        this.weatherCode,
        UNKNOWN_WEATHER_DESC
    ),
    isDay = this.isDay ?: 0,
    time = this.time.orEmpty(),
)

private const val UNKNOWN_WEATHER_DESC = "Unknown"

private val mapWeatherCodeToDescription = mapOf<Int, String>(
    0 to "Clear sky",
    1 to "Mainly clear",
    2 to "Partly cloudy",
    3 to "Overcast",
    45 to "Fog",
    48 to "Depositing rime fog",
    51 to "Light drizzle",
    53 to "Moderate drizzle",
    55 to "Dense drizzle",
    56 to "Light freezing drizzle",
    57 to "Dense freezing drizzle",
    61 to "Slight rain",
    63 to "Moderate rain",
    65 to "Heavy rain",
    66 to "Light freezing rain",
    67 to "Heavy freezing rain",
    71 to "Slight snow fall",
    73 to "Moderate snow fall",
    75 to "Heavy snow fall",
    77 to "Snow grains",
    80 to "Slight rain shower",
    81 to "Moderate rain shower",
    82 to "Violent rain shower",
    85 to "Slight snow shower",
    86 to "Heavy snow shower",
    95 to "Thunderstorm",
    99 to "Thunderstorm",
)