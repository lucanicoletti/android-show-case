package com.lucanicoletti.androidshowcase

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MyApp(viewModel: MainViewModel) {

    val description = viewModel.viewState.collectAsStateWithLifecycle()

    Text(text = description.value)
}