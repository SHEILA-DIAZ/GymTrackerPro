package com.example.gymtrackerpro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymtrackerpro.model.Rutina
import com.example.gymtrackerpro.model.Usuario
import com.example.gymtrackerpro.repository.GymRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GymViewModel(private val repository: GymRepository) : ViewModel() {

    private val _usuarioLogueado = MutableStateFlow<Usuario?>(null)
    val usuarioLogueado: StateFlow<Usuario?> = _usuarioLogueado.asStateFlow()

    fun login(user: String, pass: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val usuario = repository.buscarUsuarioPorCredenciales(user, pass)
            if (usuario != null) {
                _usuarioLogueado.value = usuario
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    fun registrarUsuario(usuario: Usuario, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            if (repository.existeUsuarioPorEmail(usuario.email) != null) {
                onResult("El email ya está registrado")
                return@launch
            }
            if (repository.existeUsuarioPorNombre(usuario.nombreUsuario) != null) {
                onResult("El nombre de usuario ya existe")
                return@launch
            }
            repository.insertarUsuario(usuario)
            onResult(null)
        }
    }

    fun logout() {
        _usuarioLogueado.value = null
    }

    fun getRutinas(usuarioId: Int) = repository.listarRutinasPorUsuario(usuarioId)

    fun agregarRutina(rutina: Rutina) {
        viewModelScope.launch {
            repository.insertarRutina(rutina)
        }
    }

    fun eliminarRutina(rutina: Rutina) {
        viewModelScope.launch {
            repository.eliminarRutina(rutina)
        }
    }

    fun actualizarRutina(rutina: Rutina) {
        viewModelScope.launch {
            repository.actualizarRutina(rutina)
        }
    }

    suspend fun buscarRutinaPorId(id: Int) = repository.buscarRutinaPorId(id)

    suspend fun buscarUsuarioPorId(id: Int) = repository.buscarUsuarioPorId(id)

    suspend fun getEstadisticas(usuarioId: Int): Pair<Int, Double> {
        val count = repository.contarRutinasPorUsuario(usuarioId)
        val sum = repository.sumPesoTotalPorUsuario(usuarioId) ?: 0.0
        return Pair(count, sum)
    }
}
