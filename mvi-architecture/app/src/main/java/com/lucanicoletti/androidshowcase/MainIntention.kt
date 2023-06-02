package com.lucanicoletti.androidshowcase

sealed interface MainIntention {
    object ScreenStarted: MainIntention
    object Retry: MainIntention
}