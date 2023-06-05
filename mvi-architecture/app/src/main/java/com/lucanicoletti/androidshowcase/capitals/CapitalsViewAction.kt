package com.lucanicoletti.androidshowcase.capitals

import com.lucanicoletti.domain.model.Capital

sealed interface CapitalsViewAction {
    data class NavigateToCurrentWeather(val capital: Capital): CapitalsViewAction
}