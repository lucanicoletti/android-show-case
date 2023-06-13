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
import com.lucanicoletti.betdeck.game.ui.GameOverView
import com.lucanicoletti.betdeck.game.ui.LoadingView
import com.lucanicoletti.betdeck.game.ui.NewGameView
import com.lucanicoletti.betdeck.game.ui.PlayingView
import com.lucanicoletti.domain.model.CardModel
import com.lucanicoletti.domain.model.CardValue
import com.lucanicoletti.domain.model.Suit
import java.util.Locale

@Composable
fun GameScreen(viewModel: GameViewModel) {
    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center,
        ) {
            when (viewState) {
                GameViewState.NewScreen -> NewGameView {
                    viewModel.submitIntention(GameIntention.StartNewGame)
                }

                is GameViewState.CurrentlyPlaying -> PlayingView(
                    viewState = viewState,
                    betOnHigherClicked = {
                        viewModel.submitIntention(GameIntention.Bet(BetValue.Higher))
                    },
                    betOnLowerClicked = {
                        viewModel.submitIntention(GameIntention.Bet(BetValue.Lower))
                    }
                )

                is GameViewState.GameOver -> GameOverView(
                    viewState.score
                ) {
                    viewModel.submitIntention(GameIntention.StartNewGame)
                }

                GameViewState.Loading -> LoadingView()
            }
        }
    }
}
