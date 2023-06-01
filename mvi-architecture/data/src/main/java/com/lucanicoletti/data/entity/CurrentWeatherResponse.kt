package com.lucanicoletti.data.entity

import com.google.gson.annotations.SerializedName

class CurrentWeatherResponse(
    @SerializedName("latitude") val latitude: Double? = null,
    @SerializedName("longitude") val longitude: Double? = null,
    @SerializedName("generationtime_ms") val generationTimeMs: Double? = null,
    @SerializedName("utc_offset_seconds") val utcOffsetSeconds: Int? = null,
    @SerializedName("timezone") val timezone: String? = null,
    @SerializedName("timezone_abbreviation") val timezoneAbbreviation: String? = null,
    @SerializedName("elevation") val elevation: Int? = null,
    @SerializedName("current_weather") val currentWeather: CurrentWeather? = null,
)