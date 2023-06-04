package com.lucanicoletti.androidshowcase.capitals

import com.lucanicoletti.domain.model.Capital

sealed interface CapitalsViewState {
    object Loading: CapitalsViewState
    data class Error(val message: String): CapitalsViewState
    data class CapitalsList(val list: List<Capital>): CapitalsViewState
}