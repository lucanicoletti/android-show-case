package com.lucanicoletti.betdeck.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GameScreen(viewModel: GameViewModel) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    BackHandler {
        viewModel.submitIntention(GameIntention.BackClicked)
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            when (viewState) {
                GameViewState.NewScreen -> NewScreen {
                    viewModel.submitIntention(GameIntention.StartNewGame)
                }

                is GameViewState.CurrentlyPlaying -> PlayingScreen(
                    viewState = viewState,
                    betOnHigherClicked = {
                        viewModel.submitIntention(GameIntention.Bet(BetValue.Higher))
                    },
                    betOnLowerClicked = {
                        viewModel.submitIntention(GameIntention.Bet(BetValue.Lower))
                    }
                )

                is GameViewState.GameOver -> GameOverScreen(
                    viewState.score
                ) {
                    viewModel.submitIntention(GameIntention.StartNewGame)
                }

                GameViewState.Loading -> LoadingScreen()
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    CircularProgressIndicator()
}

@Composable
fun GameOverScreen(score: Int, onStartOverClicked: () -> Unit) {
    Column {
        Text(text = score.toString())
        OutlinedButton(onClick = onStartOverClicked) {
            Text(text = "Start over")
        }
    }
}

@Composable
fun PlayingScreen(
    viewState: GameViewState.CurrentlyPlaying,
    betOnHigherClicked: () -> Unit,
    betOnLowerClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = viewState.currentCard.value.toString() + " " + viewState.currentCard.suit.toString())
        Row {
            OutlinedButton(onClick = betOnLowerClicked) {
                Text("Bet lower")
            }
            OutlinedButton(onClick = betOnHigherClicked) {
                Text("Bet higher")
            }
        }
    }
}

@Composable
fun NewScreen(onNewGameClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "BetDeck", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Bet on the value of the next card!")

        Text(text = "Will it be higher or lover?!")

        OutlinedButton(onClick = onNewGameClicked) {
            Text(text = "Let's play!")
        }
    }
}

