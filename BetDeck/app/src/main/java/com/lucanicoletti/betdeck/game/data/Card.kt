package com.lucanicoletti.betdeck.game.data

data class Card (
    val cardValue: String,
    val cardSuit: String,
    val imageUrl: String,
)

const val CARD_IMAGE_BASE_URL = "https://deckofcardsapi.com/static/img/"
const val CARD_BACK_IMAGE_URL = "https://deckofcardsapi.com/static/img/back.png"
const val CARD_IMAGE_EXTENSION = ".png"