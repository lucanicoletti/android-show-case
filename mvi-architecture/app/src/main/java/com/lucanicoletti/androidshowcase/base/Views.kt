package com.lucanicoletti.androidshowcase.base

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}


@Composable
fun ErrorView(errorMessage: String, onButtonClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(errorMessage)
            OutlinedButton(onClick = onButtonClick) {
                Text(text = "Retry")
            }
        }
    }
}

@Preview
@Composable
fun LoadingMainViewPreview() {
    LoadingView()
}


@Preview
@Composable
fun ErrorMainViewPreview() {
    ErrorView(
        errorMessage = "Cannot load current weather data",
        onButtonClick = { }
    )
}
