package com.lucanicoletti.domain.model

class WeatherModel(
    val temperature: Double? = null,
    val windSpeed: Double? = null,
    val windDirection: Int? = null,
    val weatherCode: Int? = null,
    val isDay: Int? = null,
    val time: String? = null
)