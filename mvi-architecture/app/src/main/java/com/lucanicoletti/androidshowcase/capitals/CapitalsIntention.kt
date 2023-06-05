package com.lucanicoletti.androidshowcase.capitals

import com.lucanicoletti.domain.model.Capital

sealed interface CapitalsIntention {
    object ScreenStarted : CapitalsIntention
    data class CapitalClicked(val capital: Capital) : CapitalsIntention
    data class Searched(val query: String) : CapitalsIntention
}