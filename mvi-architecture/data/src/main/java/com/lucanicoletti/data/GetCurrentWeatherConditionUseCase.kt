package com.lucanicoletti.data

import com.lucanicoletti.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherConditionUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(latitude: Float, longitude: Float) =
        weatherRepository.getCurrentWeather(latitude, longitude)
}