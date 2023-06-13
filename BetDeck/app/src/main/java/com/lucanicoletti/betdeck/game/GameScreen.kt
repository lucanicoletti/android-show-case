package com.lucanicoletti.betdeck.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.lucanicoletti.betdeck.game.data.CARD_BACK_IMAGE_URL
import com.lucanicoletti.betdeck.game.data.CARD_IMAGE_BASE_URL
import com.lucanicoletti.betdeck.game.data.CARD_IMAGE_EXTENSION
import com.lucanicoletti.betdeck.game.data.Card
import com.lucanicoletti.domain.model.CardModel
import com.lucanicoletti.domain.model.CardValue
import com.lucanicoletti.domain.model.Suit
import java.util.Locale

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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "You lost!",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )

        Text(
            text = "You guessed correctly $score times! Nice job",
            style = MaterialTheme.typography.bodyMedium
        )

        Button(onClick = onStartOverClicked) {
            Text(text = "Try again")
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
        val uiCard = viewState.currentCard.toUI()
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AsyncImage(
                modifier = Modifier
                    .weight(2f)
                    .padding(8.dp),
                model = uiCard.imageUrl, contentDescription = null,
            )
            AsyncImage(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                model = CARD_BACK_IMAGE_URL, contentDescription = null
            )

        }
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = betOnLowerClicked) {
                Text("Bet lower")
            }
            Button(onClick = betOnHigherClicked) {
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
        Text(
            text = "BetDeck",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
        Text(text = "Bet on the value of the next card!", modifier = Modifier.padding(8.dp))

        Text(text = "Will it be higher or lover?!", modifier = Modifier.padding(8.dp))

        Button(onClick = onNewGameClicked) {
            Text(text = "Let's play!")
        }
    }
}

private fun CardModel.toUI() = Card(
    cardValue = this.value.toString()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
    cardSuit = this.suit.toString()
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
    imageUrl = CARD_IMAGE_BASE_URL + this.value.toUI() + this.suit.toUI() + CARD_IMAGE_EXTENSION
)

private fun Suit.toUI() = when (this) {
    Suit.Spades -> "S"
    Suit.Diamonds -> "D"
    Suit.Hearts -> "H"
    Suit.Clubs -> "C"
}

private fun CardValue.toUI() = when (this) {
    CardValue.A -> "A"
    CardValue.Two -> "2"
    CardValue.Three -> "3"
    CardValue.Four -> "4"
    CardValue.Five -> "5"
    CardValue.Six -> "6"
    CardValue.Seven -> "7"
    CardValue.Eight -> "8"
    CardValue.Nine -> "9"
    CardValue.Ten -> "0"
    CardValue.Jack -> "J"
    CardValue.Queen -> "Q"
    CardValue.King -> "K"
}

