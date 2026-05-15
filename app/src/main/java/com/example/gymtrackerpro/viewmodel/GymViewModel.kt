package com.example.gymtrackerpro.viewmodel // Define paquete -> Ubicación en el proyecto

import androidx.lifecycle.ViewModel // Importa ViewModel -> Android Jetpack
import androidx.lifecycle.viewModelScope // Importa scope de corrutinas -> Gestión de ciclos de vida
import com.example.gymtrackerpro.model.Rutina // Importa Modelo Rutina -> Entidad
import com.example.gymtrackerpro.model.Usuario // Importa Modelo Usuario -> Entidad
import com.example.gymtrackerpro.repository.GymRepository // Importa Repositorio -> Capa lógica
import kotlinx.coroutines.flow.MutableStateFlow // Importa StateFlow mutable -> Estado interno
import kotlinx.coroutines.flow.StateFlow // Importa StateFlow inmutable -> Estado expuesto
import kotlinx.coroutines.flow.asStateFlow // Importa conversor de flujo -> Encapsulamiento
import kotlinx.coroutines.launch // Importa lanzador de corrutinas -> Tareas asíncronas

class GymViewModel(private val repository: GymRepository) : ViewModel() { // Clase ViewModel -> Conecta UI con Repositorio

    private val _usuarioLogueado = MutableStateFlow<Usuario?>(null) // Estado privado de sesión -> Guarda usuario actual
    val usuarioLogueado: StateFlow<Usuario?> = _usuarioLogueado.asStateFlow() // Estado público de sesión -> Observado por UI

    fun login(user: String, pass: String, onResult: (Boolean) -> Unit) { // Función login -> Recibe credenciales de UI
        viewModelScope.launch { // Inicia tarea asíncrona -> Segundo plano
            val usuario = repository.buscarUsuarioPorCredenciales(user, pass) // Valida acceso -> Consulta al Repositorio
            if (usuario != null) { // Verifica éxito -> Lógica de decisión
                _usuarioLogueado.value = usuario // Actualiza sesión -> Notifica a observadores
                onResult(true) // Retorna éxito -> Envía señal a UI
            } else { // Caso error -> Lógica de decisión
                onResult(false) // Retorna fallo -> Envía señal a UI
            }
        }
    }

    fun registrarUsuario(usuario: Usuario, onResult: (String?) -> Unit) { // Función registro -> Recibe datos de UI
        viewModelScope.launch { // Inicia tarea asíncrona -> Segundo plano
            if (repository.existeUsuarioPorEmail(usuario.email) != null) { // Valida duplicado email -> Consulta al Repositorio
                onResult("El email ya está registrado") // Retorna error -> Envía mensaje a UI
                return@launch // Finaliza tarea -> Control de flujo
            }
            if (repository.existeUsuarioPorNombre(usuario.nombreUsuario) != null) { // Valida duplicado nombre -> Consulta al Repositorio
                onResult("El nombre de usuario ya existe") // Retorna error -> Envía mensaje a UI
                return@launch // Finaliza tarea -> Control de flujo
            }
            repository.insertarUsuario(usuario) // Guarda nuevo usuario -> Envía al Repositorio
            onResult(null) // Retorna éxito -> Envía señal a UI
        }
    }

    fun logout() { // Función cerrar sesión -> Acción de UI
        _usuarioLogueado.value = null // Limpia estado -> Reinicia UI
    }

    fun getRutinas(usuarioId: Int) = repository.listarRutinasPorUsuario(usuarioId) // Obtiene lista -> Pide al Repositorio

    fun agregarRutina(rutina: Rutina) { // Función añadir -> Recibe datos de UI
        viewModelScope.launch { // Inicia tarea asíncrona -> Segundo plano
            repository.insertarRutina(rutina) // Guarda rutina -> Envía al Repositorio
        }
    }

    fun eliminarRutina(rutina: Rutina) { // Función borrar -> Recibe datos de UI
        viewModelScope.launch { // Inicia tarea asíncrona -> Segundo plano
            repository.eliminarRutina(rutina) // Borra rutina -> Envía al Repositorio
        }
    }

    fun actualizarRutina(rutina: Rutina) { // Función editar -> Recibe datos de UI
        viewModelScope.launch { // Inicia tarea asíncrona -> Segundo plano
            repository.actualizarRutina(rutina) // Actualiza rutina -> Envía al Repositorio
        }
    }

    suspend fun buscarRutinaPorId(id: Int) = repository.buscarRutinaPorId(id) // Busca registro -> Pide al Repositorio

    suspend fun buscarUsuarioPorId(id: Int) = repository.buscarUsuarioPorId(id) // Busca perfil -> Pide al Repositorio

    suspend fun getEstadisticas(usuarioId: Int): Pair<Int, Double> { // Obtiene métricas -> Recibe ID usuario
        val count = repository.contarRutinasPorUsuario(usuarioId) // Obtiene cantidad -> Pide al Repositorio
        val sum = repository.sumPesoTotalPorUsuario(usuarioId) ?: 0.0 // Obtiene suma total -> Pide al Repositorio
        return Pair(count, sum) // Retorna par de valores -> Envía a UI
    }
}
