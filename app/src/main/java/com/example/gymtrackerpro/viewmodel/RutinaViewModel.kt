package com.example.gymtrackerpro.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymtrackerpro.model.RutinaEntity
import com.example.gymtrackerpro.repository.RutinaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RutinaViewModel(private val repository: RutinaRepository) : ViewModel() {

    val rutinas: StateFlow<List<RutinaEntity>> = repository.allRutinas
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun agregarRutina(rutina: RutinaEntity) {
        viewModelScope.launch {
            repository.insert(rutina)
        }
    }

    fun eliminarRutina(rutina: RutinaEntity) {
        viewModelScope.launch {
            repository.delete(rutina)
        }
    }

    suspend fun obtenerRutinaPorId(id: Int): RutinaEntity? {
        return repository.getRutinaById(id)
    }
}
