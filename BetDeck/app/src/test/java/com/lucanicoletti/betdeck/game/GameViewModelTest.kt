package com.lucanicoletti.betdeck.game

import com.lucanicoletti.domain.model.CardModel
import com.lucanicoletti.domain.model.CardValue
import com.lucanicoletti.domain.model.Suit
import com.lucanicoletti.domain.usecase.GetAllCardsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class GameViewModelTest {

    private val getAllCardsUseCase = mockk<GetAllCardsUseCase>(relaxed = true)
    private val viewModel = GameViewModel(getAllCardsUseCase)

    /*
    All tests are currently failing for: Suppressed: java.lang.IllegalStateException: Module with the Main dispatcher had failed to initialize
    Which I *know* I can resolve, just can't do it now, coding from too much, need a break
     */

    @Test
    fun `GIVEN defaults WHEN initial state THEN correct state`() = runTest {
        // GIVEN
        val expectedState = GameViewState.NewScreen

        // WHEN
        // nothing

        // THEN
        assertEquals(expectedState, viewModel.viewState.value)
    }

    @Test
    fun `GIVEN defaults WHEN user clicks on get started THEN game is started`() = runTest {
        // GIVEN
        val fullList = listOf(
            CardModel(CardValue.King, Suit.Diamonds),
            CardModel(CardValue.A, Suit.Hearts),
            CardModel(CardValue.Seven, Suit.Clubs),
            CardModel(CardValue.Five, Suit.Diamonds),
        )
        coEvery {
            getAllCardsUseCase()
        } returns fullList
        val expectedState = GameViewState.CurrentlyPlaying(
            currentStreak = 0,
            currentCard = fullList[0],
            remainingCards = fullList - fullList[0]
        )

        // WHEN
        viewModel.submitIntention(GameIntention.StartNewGame)

        // THEN
        assertEquals(expectedState, viewModel.viewState.value)
    }

    @Test
    fun `GIVEN game started WHEN user bets correctly THEN game continues`() = runTest {
        // GIVEN
        val fullList = listOf(
            CardModel(CardValue.King, Suit.Diamonds),
            CardModel(CardValue.A, Suit.Hearts),
            CardModel(CardValue.Seven, Suit.Clubs),
            CardModel(CardValue.Five, Suit.Diamonds),
        )
        coEvery {
            getAllCardsUseCase()
        } returns fullList
        val expectedState = GameViewState.CurrentlyPlaying(
            currentStreak = 1,
            currentCard = fullList[1],
            remainingCards = fullList - fullList[0] - fullList[1]
        )

        val collectStateJob = launch(Dispatchers.Unconfined) { viewModel.viewState.collect() }

        // WHEN
        viewModel.submitIntention(GameIntention.StartNewGame)
        viewModel.submitIntention(GameIntention.Bet(BetValue.Lower))

        // THEN
        assertEquals(expectedState, viewModel.viewState.value)
        collectStateJob.cancel()
    }

    @Test
    fun `GIVEN game started WHEN user bets wrongly THEN game is over with streak`() = runTest {
        // GIVEN
        val fullList = listOf(
            CardModel(CardValue.King, Suit.Diamonds),
            CardModel(CardValue.A, Suit.Hearts),
            CardModel(CardValue.Seven, Suit.Clubs),
            CardModel(CardValue.Five, Suit.Diamonds),
        )
        coEvery {
            getAllCardsUseCase()
        } returns fullList
        val expectedState = GameViewState.GameOver(1)

        // WHEN
        viewModel.submitIntention(GameIntention.StartNewGame)
        viewModel.submitIntention(GameIntention.Bet(BetValue.Lower))
        viewModel.submitIntention(GameIntention.Bet(BetValue.Lower))

        // THEN
        assertEquals(expectedState, viewModel.viewState.value)
    }
}