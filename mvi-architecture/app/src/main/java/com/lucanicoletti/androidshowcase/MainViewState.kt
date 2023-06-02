package com.lucanicoletti.androidshowcase

import com.lucanicoletti.domain.model.WeatherModel

sealed interface MainViewState {
    object Loading : MainViewState
    data class Error(val message: String, val intention: MainIntention) : MainViewState
    data class Success(
        val temperature: Double,
        val windSpeed: Double,
        val windDirection: Int,
        val weatherDescription: String
    ) : MainViewState
}