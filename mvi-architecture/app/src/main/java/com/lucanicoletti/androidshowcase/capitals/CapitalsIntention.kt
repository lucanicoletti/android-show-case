package com.lucanicoletti.androidshowcase.capitals

sealed interface CapitalsIntention {
    object ScreenStarted: CapitalsIntention
    data class CapitalClicked(val lat: Float, val lng: Float)
}