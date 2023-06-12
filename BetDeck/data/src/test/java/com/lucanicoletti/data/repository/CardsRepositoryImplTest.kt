package com.lucanicoletti.data.repository

import com.lucanicoletti.data.CardsApi
import com.lucanicoletti.data.entity.CardEntity
import com.lucanicoletti.domain.model.CardModel
import com.lucanicoletti.domain.model.CardValue
import com.lucanicoletti.domain.model.Suit
import com.lucanicoletti.domain.repository.CardsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CardsRepositoryImplTest {

    private val cardsApi = mockk<CardsApi>(relaxed = true)
    private val cardsRepository: CardsRepository = CardsRepositoryImpl(cardsApi)

    @ParameterizedTest
    @ValueSource(strings = ["A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"])
    fun `GIVEN correct values WHEN receiving a card entity THEN mapping is correct`(value: String) =
        runTest {
            // GIVEN
            val expectedValue = value.toCardValue()
            val expectedSuit = Suit.Diamonds
            val expectedResult = CardModel(expectedValue, expectedSuit)

            coEvery {
                cardsApi.getAllCards()
            } returns listOf(CardEntity(value, "diamonds"))

            // WHEN
            val result = cardsRepository.getAllCards().first()

            // THEN
            assertEquals(/* expected = */ expectedResult, /* actual = */ result)
        }

    @ParameterizedTest
    @ValueSource(strings = ["diamonds", "hearts", "clubs", "spades"])
    fun `GIVEN correct suits WHEN receiving a card entity THEN mapping is correct`(suit: String) =
        runTest {
            // GIVEN
            val expectedSuit = suit.toCardSuit()
            val expectedValue = CardValue.King
            val expectedResult = CardModel(expectedValue, expectedSuit)

            coEvery {
                cardsApi.getAllCards()
            } returns listOf(CardEntity("king", suit))

            // WHEN
            val result = cardsRepository.getAllCards().first()

            // THEN
            assertEquals(/* expected = */ expectedResult, /* actual = */ result)
        }
}