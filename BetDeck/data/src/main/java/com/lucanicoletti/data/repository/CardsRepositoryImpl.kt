package com.lucanicoletti.data.repository

import com.lucanicoletti.data.CardsApi
import com.lucanicoletti.domain.model.CardModel
import com.lucanicoletti.domain.model.CardValue
import com.lucanicoletti.domain.model.Suit
import com.lucanicoletti.domain.repository.CardsRepository
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val cardsApi: CardsApi,
) : CardsRepository {
    override suspend fun getAllCards(): List<CardModel> = cardsApi.getAllCards().map { entity ->
        CardModel(
            entity.value.toCardValue(),
            entity.suit.toCardSuit(),
        )
    }
}

internal fun String.toCardValue(): CardValue =
    when (this.lowercase()) {
        "a" -> CardValue.A
        "2" -> CardValue.Two
        "3" -> CardValue.Three
        "4" -> CardValue.Four
        "5" -> CardValue.Five
        "6" -> CardValue.Six
        "7" -> CardValue.Seven
        "8" -> CardValue.Eight
        "9" -> CardValue.Nine
        "10" -> CardValue.Ten
        "jack" -> CardValue.Jack
        "queen" -> CardValue.Queen
        "king" -> CardValue.King
        else -> throw TypeCastException("unrecognised value for card received")
    }


internal fun String.toCardSuit(): Suit =
    when (this.lowercase()) {
        "spades" -> Suit.Spades
        "hearts" -> Suit.Hearts
        "clubs" -> Suit.Clubs
        "diamonds" -> Suit.Diamonds
        else -> throw TypeCastException("unrecognised suit for card received")
    }

