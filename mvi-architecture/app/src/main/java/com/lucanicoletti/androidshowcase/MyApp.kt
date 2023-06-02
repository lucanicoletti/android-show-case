package com.lucanicoletti.androidshowcase

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lucanicoletti.androidshowcase.base.ObserverLifecycleEvents

@Composable
fun MyApp(viewModel: MainViewModel) {

    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserverLifecycleEvents(
        onStart = {
            viewModel.submitIntention(MainIntention.ScreenStarted)
        },
    )

    when (viewState) {
        MainViewState.Loading -> LoadingMainView()
        is MainViewState.Error -> ErrorMainView(errorState = viewState) {
            viewModel.submitIntention(viewState.intention)
        }

        is MainViewState.Success -> SuccessMainView(viewState = viewState)
    }
}

@Composable
fun LoadingMainView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun SuccessMainView(viewState: MainViewState.Success) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
        ) {
            Text(
                text = "Current weather conditions:",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = viewState.weatherDescription,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Temperature: ")
                Text(String.format("%,.2f ºC", viewState.temperature))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Wind direction: ")
                Text(text = viewState.windDirection.toString())
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Wind speed: ")
                Text(String.format("%,.2f km/h", viewState.windSpeed))
            }
        }
    }
}

@Composable
fun ErrorMainView(errorState: MainViewState.Error, onButtonClick: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(errorState.message)
            OutlinedButton(onClick = onButtonClick) {
                Text(text = "Retry")
            }
        }
    }
}

@Preview
@Composable
fun LoadingMainViewPreview() {
    LoadingMainView()
}

@Preview
@Composable
fun SuccessMainViewPreview() {
    SuccessMainView(
        MainViewState.Success(
            temperature = 23.0,
            windSpeed = 15.0,
            windDirection = 3,
            weatherDescription = "Sunny"
        )
    )
}

@Preview
@Composable
fun ErrorMainViewPreview() {
    ErrorMainView(
        errorState = MainViewState.Error("Cannot load current weather data", MainIntention.Retry),
        onButtonClick = { }
    )
}