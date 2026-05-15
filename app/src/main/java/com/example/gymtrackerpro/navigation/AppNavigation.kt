package com.example.gymtrackerpro.navigation // Define paquete -> Ubicación en el proyecto

import androidx.compose.runtime.Composable // Importa Composable -> Jetpack Compose
import androidx.navigation.NavType // Importa NavType -> Definición de tipos de argumentos
import androidx.navigation.compose.NavHost // Importa NavHost -> Contenedor de navegación
import androidx.navigation.compose.composable // Importa composable -> Define destino de ruta
import androidx.navigation.compose.rememberNavController // Importa rememberNavController -> Controlador de estado
import androidx.navigation.navArgument // Importa navArgument -> Parámetros entre pantallas
import com.example.gymtrackerpro.screens.* // Importa todas las pantallas -> Capa UI
import com.example.gymtrackerpro.viewmodel.GymViewModel // Importa ViewModel -> Capa lógica

@Composable // Define componente de UI -> Función composable
fun AppNavigation(viewModel: GymViewModel) { // Función de navegación -> Recibe ViewModel compartido
    val navController = rememberNavController() // Inicializa controlador -> Gestiona historial de navegación

    NavHost(navController = navController, startDestination = "login") { // Configura el grafo de navegación -> Define punto de partida
        composable("login") { // Define ruta login -> ID de pantalla
            LoginScreen(navController, viewModel) // Carga pantalla login -> Envía controlador y VM
        }
        composable("registro") { // Define ruta registro -> ID de pantalla
            RegistroScreen(navController, viewModel) // Carga pantalla registro -> Envía controlador y VM
        }
        composable("menu") { // Define ruta menú -> ID de pantalla
            MenuPrincipalScreen(navController, viewModel) // Carga pantalla principal -> Envía controlador y VM
        }
        composable("agregar_rutina") { // Define ruta agregar -> ID de pantalla
            AgregarRutinaScreen(navController, viewModel) // Carga formulario -> Envía controlador y VM
        }
        composable("lista_rutinas") { // Define ruta lista -> ID de pantalla
            ListaRutinasScreen(navController, viewModel) // Carga historial -> Envía controlador y VM
        }
        composable( // Configura ruta con parámetro -> Detalle de ítem
            route = "detalle_rutina/{rutinaId}", // Define URL interna -> Recibe ID variable
            arguments = listOf(navArgument("rutinaId") { type = NavType.IntType }) // Especifica tipo de dato -> Convierte String a Int
        ) { backStackEntry -> // Bloque de construcción -> Captura argumentos de navegación
            val rutinaId = backStackEntry.arguments?.getInt("rutinaId") ?: 0 // Recupera el ID -> Extrae dato del bundle
            DetalleRutinaScreen(navController, viewModel, rutinaId) // Carga pantalla detalle -> Envía ID al componente
        }
        composable("perfil") { // Define ruta perfil -> ID de pantalla
            PerfilUsuarioScreen(navController, viewModel) // Carga datos usuario -> Envía controlador y VM
        }
    }
}
