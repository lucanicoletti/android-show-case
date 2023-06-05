package com.lucanicoletti.androidshowcase.capitals

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lucanicoletti.androidshowcase.base.CapitalSearchTopAppBar
import com.lucanicoletti.androidshowcase.base.ErrorView
import com.lucanicoletti.androidshowcase.base.LoadingView
import com.lucanicoletti.androidshowcase.base.ObserverLifecycleEvents
import com.lucanicoletti.androidshowcase.base.WeatherTopAppBar
import com.lucanicoletti.domain.model.Capital
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CapitalList(
    viewModel: CapitalsViewModel,
    onNavigateToCurrentWeather: (Capital) -> Unit
) {

    val viewState = viewModel.viewState.collectAsStateWithLifecycle().value


    LaunchedEffect(0) {
        viewModel.viewActions.collectLatest {
            it?.let { viewAction ->
                when (viewAction) {
                    is CapitalsViewAction.NavigateToCurrentWeather ->
                        onNavigateToCurrentWeather(viewAction.capital)
                }
            }
        }
    }

    ObserverLifecycleEvents(
        onStart = {
            viewModel.submitIntention(CapitalsIntention.ScreenStarted)
        },
    )

    Scaffold(
        topBar = {
            CapitalSearchTopAppBar {
                viewModel.submitIntention(CapitalsIntention.Searched(it))
            }
        }
    ) { innerPadding ->
        when (viewState) {
            CapitalsViewState.Loading -> LoadingView()
            is CapitalsViewState.Error -> ErrorView(errorMessage = viewState.message) { }
            is CapitalsViewState.CapitalsList -> CapitalsListView(
                Modifier.padding(innerPadding),
                viewState.list,
            ) { capital ->
                viewModel.submitIntention(CapitalsIntention.CapitalClicked(capital = capital))
            }
        }
    }
}

@Composable
fun CapitalsListView(
    modifier: Modifier = Modifier,
    list: List<Capital>,
    onCapitalClicked: (Capital) -> Unit
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .scrollable(rememberScrollState(), Orientation.Vertical)
    ) {
        items(list) { capital ->
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable {
                        onCapitalClicked(capital)
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = capital.name,
                )
                Icon(Icons.Filled.KeyboardArrowRight, null)
            }
        }
    }
}
