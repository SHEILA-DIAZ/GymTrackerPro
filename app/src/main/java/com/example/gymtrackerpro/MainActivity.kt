package com.example.gymtrackerpro // Define paquete -> Ubicación raíz del proyecto

import android.os.Bundle // Importa Bundle -> Manejo de estado de Activity
import androidx.activity.ComponentActivity // Importa Activity base -> Ciclo de vida de la app
import androidx.activity.compose.setContent // Importa setContent -> Integración con Jetpack Compose
import androidx.compose.material3.MaterialTheme // Importa MaterialTheme -> Sistema de diseño
import androidx.compose.material3.Surface // Importa Surface -> Contenedor base de UI
import androidx.lifecycle.ViewModel // Importa clase ViewModel -> Arquitectura Android
import androidx.lifecycle.ViewModelProvider // Importa Factory de ViewModel -> Creación de componentes
import com.example.gymtrackerpro.data.GymDatabase // Importa base de datos -> Capa datos
import com.example.gymtrackerpro.navigation.AppNavigation // Importa navegación -> Gestión de pantallas
import com.example.gymtrackerpro.repository.GymRepository // Importa repositorio -> Capa lógica
import com.example.gymtrackerpro.viewmodel.GymViewModel // Importa ViewModel -> Capa presentación

class MainActivity : ComponentActivity() { // Clase principal de la app -> Punto de entrada
    override fun onCreate(savedInstanceState: Bundle?) { // Método de creación -> Se ejecuta al abrir la app
        super.onCreate(savedInstanceState) // Llama al constructor padre -> Inicialización del sistema

        val database = GymDatabase.getDatabase(this) // Inicializa BD -> Obtiene instancia de GymDatabase
        val repository = GymRepository(database.usuarioDao(), database.rutinaDao()) // Crea repositorio -> Inyecta DAOs al GymRepository
        
        val viewModelFactory = object : ViewModelProvider.Factory { // Crea fábrica personalizada -> Necesaria para inyectar repository al ViewModel
            override fun <T : ViewModel> create(modelClass: Class<T>): T { // Método de creación de VM -> Define cómo instanciar GymViewModel
                return GymViewModel(repository) as T // Retorna nueva instancia -> Envía repositorio al ViewModel
            }
        }
        
        val viewModel = ViewModelProvider(this, viewModelFactory)[GymViewModel::class.java] // Obtiene ViewModel -> Instancia persistente al giro de pantalla

        setContent { // Define interfaz de usuario -> Renderiza UI con Compose
            MaterialTheme { // Aplica tema visual -> Estilos globales
                Surface(color = MaterialTheme.colorScheme.background) { // Crea fondo de pantalla -> Color según el tema
                    AppNavigation(viewModel) // Inicia navegación -> Pasa el ViewModel a las pantallas
                }
            }
        }
    }
}
