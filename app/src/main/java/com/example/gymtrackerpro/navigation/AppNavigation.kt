package com.example.gymtrackerpro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gymtrackerpro.screens.AddRutinaScreen
import com.example.gymtrackerpro.screens.DetailScreen
import com.example.gymtrackerpro.screens.HomeScreen
import com.example.gymtrackerpro.viewmodel.RutinaViewModel

@Composable
fun AppNavigation(viewModel: RutinaViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController, viewModel)
        }
        composable("add") {
            AddRutinaScreen(navController, viewModel)
        }
        composable(
            route = "detail/{rutinaId}",
            arguments = listOf(navArgument("rutinaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val rutinaId = backStackEntry.arguments?.getInt("rutinaId") ?: 0
            DetailScreen(navController, viewModel, rutinaId)
        }
    }
}
