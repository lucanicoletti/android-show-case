package com.lucanicoletti.data

import com.lucanicoletti.data.entity.CurrentWeather
import com.lucanicoletti.data.entity.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/forecast")
    suspend fun getCurrentWeather(
        @Query("latitude") lat: Float,
        @Query("longitude") lng: Float,
        @Query("current_weather") currentWeather: Boolean = true,
        @Query("timezone") timezone: String = "auto",
    ) : CurrentWeatherResponse
}