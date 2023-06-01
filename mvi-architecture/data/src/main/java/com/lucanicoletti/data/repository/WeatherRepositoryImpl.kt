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
    temperature = this.temperature,
    windSpeed = this.windSpeed,
    windDirection = this.windDirection,
    weatherDescription = weatherCodeInterpretation(this.weatherCode ?: -1),
    isDay = this.isDay,
    time = this.time,
)

private fun weatherCodeInterpretation(code: Int): String =
    when (code) {
        0 -> "Clear sky"
        1 -> "Mainly clear"
        2 -> "Partly cloudy"
        3 -> "Overcast"
        45 -> "Fog"
        48 -> "Depositing rime fog"
        51 -> "Light drizzle"
        53 -> "Moderate drizzle"
        55 -> "Dense drizzle"
        56 -> "Light freezing drizzle"
        57 -> "Dense freezing drizzle"
        61 -> "Slight rain"
            63 -> "Moderate rain"
                65 -> "Heavy rain"
        66 -> "Light freezing rain"
        67 -> "Heavy freezing rain"
        71 -> "Slight snow fall"
        73 -> "Moderate snow fall"
        75 -> "Heavy snow fall"
        77 -> "Snow grains"
        80 -> "Slight rain shower"
        81 -> "Moderate rain shower"
        82 -> "Violent rain shower"
        85 -> "Slight snow shower"
        86 -> "Heavy snow shower"
        95, 96, 99 -> "Thunderstorm"
        else -> "Unknown"
    }