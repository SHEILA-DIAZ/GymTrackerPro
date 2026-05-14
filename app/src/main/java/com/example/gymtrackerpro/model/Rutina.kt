package com.example.gymtrackerpro.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rutina")
data class Rutina(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,

    val series: Int,

    val repeticiones: Int,

    val pesoKg: Double,

    val usuarioId: Int
)