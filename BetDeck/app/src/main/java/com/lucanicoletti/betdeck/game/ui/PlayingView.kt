package com.lucanicoletti.betdeck.game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lucanicoletti.betdeck.game.GameViewState
import com.lucanicoletti.betdeck.game.data.CARD_BACK_IMAGE_URL
import com.lucanicoletti.betdeck.game.data.CARD_IMAGE_BASE_URL
import com.lucanicoletti.betdeck.game.data.CARD_IMAGE_EXTENSION
import com.lucanicoletti.betdeck.game.data.Card
import com.lucanicoletti.domain.model.CardModel
import com.lucanicoletti.domain.model.CardValue
import com.lucanicoletti.domain.model.Suit
import java.util.Locale


@Composable
fun PlayingView(
    viewState: GameViewState.CurrentlyPlaying,
    betOnHigherClicked: () -> Unit,
    betOnLowerClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Current streak: ${viewState.currentStreak}",
            style = MaterialTheme.typography.headlineMedium
        )
        CardsView(viewState = viewState)
        BetButtons(betOnLowerClicked = betOnLowerClicked, betOnHigherClicked = betOnHigherClicked)
    }
}

@Composable
fun CardsView(viewState: GameViewState.CurrentlyPlaying) {
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
}

@Composable
fun BetButtons(
    betOnLowerClicked: () -> Unit,
    betOnHigherClicked: () -> Unit,
) {
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