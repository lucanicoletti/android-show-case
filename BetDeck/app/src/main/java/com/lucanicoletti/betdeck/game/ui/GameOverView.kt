package com.lucanicoletti.betdeck.game.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun GameOverView(score: Int, onStartOverClicked: () -> Unit) {
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