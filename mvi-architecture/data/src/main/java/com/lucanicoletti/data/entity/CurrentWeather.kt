package com.lucanicoletti.data.entity

import com.google.gson.annotations.SerializedName

data class CurrentWeather(
    @SerializedName("temperature") val temperature: Double? = null,
    @SerializedName("windspeed") val windSpeed: Double? = null,
    @SerializedName("winddirection") val windDirection: Int? = null,
    @SerializedName("weathercode") val weatherCode: Int? = null,
    @SerializedName("is_day") val isDay: Int? = null,
    @SerializedName("time") val time: String? = null,
)
