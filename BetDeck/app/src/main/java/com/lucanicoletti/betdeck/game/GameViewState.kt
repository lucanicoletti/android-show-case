package com.lucanicoletti.betdeck.game

import com.lucanicoletti.domain.model.CardModel

sealed interface GameViewState {
    object NewScreen: GameViewState
    object Loading: GameViewState
    data class CurrentlyPlaying(val currentStreak: Int = 0, val currentCard: CardModel, val remainingCards: List<CardModel>): GameViewState
    data class GameOver(val score: Int): GameViewState
}
