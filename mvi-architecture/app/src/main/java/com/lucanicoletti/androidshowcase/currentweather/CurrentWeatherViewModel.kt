package com.lucanicoletti.androidshowcase.currentweather

import androidx.lifecycle.viewModelScope
import com.lucanicoletti.androidshowcase.base.BaseViewModel
import com.lucanicoletti.domain.usecase.GetCurrentWeatherConditionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val getCurrentWeatherConditionUseCase: GetCurrentWeatherConditionUseCase
) : BaseViewModel<CurrentWeatherViewState, CurrentWeatherIntention>(CurrentWeatherViewState.Loading) {

    private fun fetchCurrentWeather(lat: Float, lng: Float) {
        viewModelScope.launch {
            try {
                val result = getCurrentWeatherConditionUseCase(lat, lng)
                result?.let {
                    _viewState.value = CurrentWeatherViewState.Success(
                        temperature = it.temperature,
                        windSpeed = it.windSpeed,
                        windDirection = it.windDirection,
                        weatherDescription = it.weatherDescription
                    )
                } ?: run {
                    postError(lat, lng)
                }
            } catch (e: Exception) {
                postError(lat, lng)
            }
        }
    }

    private fun postError(lat: Float, lng: Float) {
        _viewState.value = CurrentWeatherViewState.Error(
            message = "Failed loading current weather",
            intention = CurrentWeatherIntention.Retry(lat, lng)
        )
    }

    override fun handleIntention(intention: CurrentWeatherIntention) {
        when (intention) {
            is CurrentWeatherIntention.ScreenStarted -> fetchCurrentWeather(intention.lat, intention.lng)
            is CurrentWeatherIntention.Retry -> fetchCurrentWeather(intention.lat, intention.lng)
        }
    }

}