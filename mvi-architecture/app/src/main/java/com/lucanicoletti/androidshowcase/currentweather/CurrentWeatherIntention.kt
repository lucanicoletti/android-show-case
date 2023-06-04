package com.lucanicoletti.androidshowcase.currentweather

sealed interface CurrentWeatherIntention {
    data class ScreenStarted(val lat: Float, val lng: Float) : CurrentWeatherIntention
    data class Retry(val lat: Float, val lng: Float) : CurrentWeatherIntention
}