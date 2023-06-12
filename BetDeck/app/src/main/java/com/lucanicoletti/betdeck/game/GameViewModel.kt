package com.lucanicoletti.betdeck.game

import androidx.lifecycle.viewModelScope
import com.lucanicoletti.betdeck.base.BaseViewModel
import com.lucanicoletti.domain.model.CardModel
import com.lucanicoletti.domain.usecase.GetAllCardsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val getAllCardsUseCase: GetAllCardsUseCase,
) : BaseViewModel<GameViewState, GameIntention, GameViewAction>(
    initialState = GameViewState.NewScreen
) {

    override fun handleIntention(intention: GameIntention) {
        when (intention) {
            GameIntention.BackClicked -> {

            }
            GameIntention.StartNewGame -> fetchDeckAndStartGame()
            is GameIntention.Bet -> checkNextCard(intention.bet)
        }
    }

    private fun checkNextCard(betValue: BetValue) {
        when (val oldState = viewState.value) {
            is GameViewState.CurrentlyPlaying -> {
                val nextCard = oldState.remainingCards[0]
                if (betValue == BetValue.Higher) {
                    if (oldState.currentCard.value <= nextCard.value) {
                        onCorrectGuess(oldState = oldState, nextCard = nextCard)
                    } else {
                        onWrongGuess(oldState = oldState)
                    }
                } else {
                    if (oldState.currentCard.value <= nextCard.value) {
                        onWrongGuess(oldState = oldState)
                    } else {
                        onCorrectGuess(oldState = oldState, nextCard = nextCard)
                    }
                }
            }
            else -> {
                //nothing to do here, should never end up here
            }
        }
    }

    private fun onCorrectGuess(oldState: GameViewState.CurrentlyPlaying, nextCard: CardModel) {
        val remaining = oldState.remainingCards - nextCard
        updateState {
            GameViewState.CurrentlyPlaying(nextCard, remaining)
        }
    }

    private fun onWrongGuess(oldState: GameViewState.CurrentlyPlaying) {
        updateState {
            GameViewState.GameOver(51 - oldState.remainingCards.size)
        }
    }

    private fun fetchDeckAndStartGame() {
        updateState { GameViewState.Loading }
        viewModelScope.launch {
            val cards = getAllCardsUseCase()
            val currentCard = cards[0]
            val remainingCards = cards - currentCard
            updateState {
                GameViewState.CurrentlyPlaying(currentCard, remainingCards)
            }
        }
    }
}