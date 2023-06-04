package com.lucanicoletti.androidshowcase.currentweather

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lucanicoletti.androidshowcase.base.ErrorView
import com.lucanicoletti.androidshowcase.base.LoadingView
import com.lucanicoletti.androidshowcase.base.ObserverLifecycleEvents
import com.lucanicoletti.androidshowcase.base.WeatherTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentWeather(
    viewModel: CurrentWeatherViewModel,
    navigateBack: () -> Unit,
    name: String,
    lat: Float,
    lng: Float,
) {

    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value

    ObserverLifecycleEvents(
        onStart = {
            viewModel.submitIntention(CurrentWeatherIntention.ScreenStarted(lat, lng))
        },
    )

    Scaffold(
        topBar = {
            WeatherTopAppBar(name) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { navigateBack() },
                )
            }
        }
    ) { innerPadding ->
        when (viewState) {
            CurrentWeatherViewState.Loading -> LoadingView()
            is CurrentWeatherViewState.Error -> ErrorView(errorMessage = viewState.message) {
                viewModel.submitIntention(viewState.intention)
            }

            is CurrentWeatherViewState.Success -> SuccessMainView(
                modifier = Modifier.padding(
                    innerPadding
                ),
                viewState = viewState,
            )
        }
    }
}


@Composable
fun SuccessMainView(modifier: Modifier = Modifier, viewState: CurrentWeatherViewState.Success) {
    Box(modifier = modifier.fillMaxSize()) {
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


@Preview
@Composable
fun SuccessMainViewPreview() {
    SuccessMainView(
        modifier = Modifier,
        CurrentWeatherViewState.Success(
            temperature = 23.0,
            windSpeed = 15.0,
            windDirection = 3,
            weatherDescription = "Sunny"
        )
    )
}
