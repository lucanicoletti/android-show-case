package com.lucanicoletti.androidshowcase

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.lucanicoletti.androidshowcase.capitals.CapitalList
import com.lucanicoletti.androidshowcase.currentweather.CurrentWeather

@Composable
fun Graph() {

    val navController = rememberNavController()
    NavHost(navController, "capitals") {
        composable("capitals") {
            CapitalList(viewModel = hiltViewModel()) { capital ->
                val route = "current_weather/${capital.name}/${capital.latLng.latitude}/${capital.latLng.longitude}"
                navController.navigate(route)
            }
        }
        composable("current_weather/{name}/{lat}/{lng}", arguments = listOf(
            navArgument("lat") { type = NavType.FloatType },
            navArgument("lng") { type = NavType.FloatType }
        )) { backStackEntry ->
            CurrentWeather(
                viewModel = hiltViewModel(),
                navigateBack = {
                    navController.popBackStack()
                },
                name = backStackEntry.arguments?.getString("name").orEmpty(),
                lat = backStackEntry.arguments?.getFloat("lat") ?: 0f,
                lng = backStackEntry.arguments?.getFloat("lng") ?: 0f
            )
        }
    }
}