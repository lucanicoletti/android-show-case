package com.lucanicoletti.domain.model

class WeatherModel(
    val temperature: Double,
    val windSpeed: Double,
    val windDirection: Int,
    val weatherDescription: String,
    val isDay: Int,
    val time: String,
)