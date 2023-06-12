package com.lucanicoletti.betdeck.game

sealed interface GameIntention {
    object StartNewGame: GameIntention
    data class Bet(val bet: BetValue): GameIntention
    object BackClicked: GameIntention
}
enum class BetValue {
    Higher, Lower
}