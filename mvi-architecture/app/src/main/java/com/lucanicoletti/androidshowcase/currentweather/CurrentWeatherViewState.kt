package com.lucanicoletti.androidshowcase.currentweather

sealed interface CurrentWeatherViewState {
    object Loading : CurrentWeatherViewState
    data class Error(val message: String, val intention: CurrentWeatherIntention) :
        CurrentWeatherViewState
    data class Success(
        val temperature: Double,
        val windSpeed: Double,
        val windDirection: Int,
        val weatherDescription: String
    ) : CurrentWeatherViewState
}