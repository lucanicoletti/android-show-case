package com.lucanicoletti.androidshowcase

import androidx.lifecycle.viewModelScope
import com.lucanicoletti.androidshowcase.base.BaseViewModel
import com.lucanicoletti.data.GetCurrentWeatherConditionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentWeatherConditionUseCase: GetCurrentWeatherConditionUseCase
) : BaseViewModel<MainViewState, MainIntention>(MainViewState.Loading) {

    private fun fetchCurrentWeather() {
        viewModelScope.launch {
            try {
                val result = getCurrentWeatherConditionUseCase(0f, 0f)
                result?.let { it ->
                    _viewState.value = MainViewState.Success(
                        temperature = it.temperature,
                        windSpeed = it.windSpeed,
                        windDirection = it.windDirection,
                        weatherDescription = it.weatherDescription
                    )
                } ?: run {
                    postError()
                }
            } catch (e: Exception) {
                postError()
            }
        }
    }

    private fun postError() {
        _viewState.value = MainViewState.Error(
            message = "Failed loading current weather",
            intention = MainIntention.Retry
        )
    }

    override fun handleIntention(intention: MainIntention) {
        when (intention) {
            MainIntention.ScreenStarted,
            MainIntention.Retry -> fetchCurrentWeather()
        }
    }

}