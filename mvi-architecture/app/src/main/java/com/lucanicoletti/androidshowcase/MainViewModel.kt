package com.lucanicoletti.androidshowcase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucanicoletti.data.GetCurrentWeatherConditionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentWeatherConditionUseCase: GetCurrentWeatherConditionUseCase
) : ViewModel() {

    val viewState: MutableStateFlow<String> = MutableStateFlow("")

    init {
        viewModelScope.launch {
            val result = getCurrentWeatherConditionUseCase(0f, 0f)
            viewState.value = result?.weatherDescription.orEmpty()
        }
    }

}