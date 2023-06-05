package com.lucanicoletti.androidshowcase.base

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherTopAppBar(title: String, navigationIcon: @Composable () -> Unit = {}) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = navigationIcon,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CapitalSearchTopAppBar(onQueryChanged: (String) -> Unit) {
    val searching = remember { mutableStateOf(false) }
    val query = remember { mutableStateOf("") }
    TopAppBar(
        title = {
            if (searching.value) {
                TextField(value = query.value, onValueChange = { newVal ->
                    query.value = newVal
                    onQueryChanged(query.value)
                })
            } else {
                Text("Capitals")
            }
        },
        navigationIcon = {
            if (searching.value) {
                Icon(Icons.Filled.ArrowBack, null)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        actions = {
            if (!searching.value) {
                Icon(
                    Icons.Filled.Search,
                    modifier = Modifier.clickable { searching.value = true },
                    contentDescription = null
                )
            } else {
                Icon(
                    Icons.Filled.Clear,
                    modifier = Modifier.clickable { searching.value = false },
                    contentDescription = null
                )
            }
        }
    )
}