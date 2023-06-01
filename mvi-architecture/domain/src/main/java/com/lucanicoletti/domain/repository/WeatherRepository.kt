package com.lucanicoletti.domain.repository

import com.lucanicoletti.domain.model.WeatherModel

interface WeatherRepository {

    suspend fun getCurrentWeather(latitude: Float, longitude: Float): WeatherModel?
}