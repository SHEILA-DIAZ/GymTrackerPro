package com.example.gymtrackerpro.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rutinas")
data class RutinaEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val ejercicios: String,
    val series: Int,
    val repeticiones: Int,
    val peso: Double,
    val fecha: String
)
