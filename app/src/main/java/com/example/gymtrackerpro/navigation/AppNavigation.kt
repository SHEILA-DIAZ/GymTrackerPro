package com.example.gymtrackerpro.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gymtrackerpro.screens.*
import com.example.gymtrackerpro.viewmodel.GymViewModel

@Composable
fun AppNavigation(viewModel: GymViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController, viewModel)
        }
        composable("registro") {
            RegistroScreen(navController, viewModel)
        }
        composable("menu") {
            MenuPrincipalScreen(navController, viewModel)
        }
        composable("agregar_rutina") {
            AgregarRutinaScreen(navController, viewModel)
        }
        composable("lista_rutinas") {
            ListaRutinasScreen(navController, viewModel)
        }
        composable(
            route = "detalle_rutina/{rutinaId}",
            arguments = listOf(navArgument("rutinaId") { type = NavType.IntType })
        ) { backStackEntry ->
            val rutinaId = backStackEntry.arguments?.getInt("rutinaId") ?: 0
            DetalleRutinaScreen(navController, viewModel, rutinaId)
        }
        composable("perfil") {
            PerfilUsuarioScreen(navController, viewModel)
        }
    }
}
