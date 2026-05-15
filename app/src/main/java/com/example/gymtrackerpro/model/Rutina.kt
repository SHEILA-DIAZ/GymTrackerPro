package com.example.gymtrackerpro.model // Define paquete -> Ubicación en el proyecto

import androidx.room.ColumnInfo // Importa ColumnInfo -> Room
import androidx.room.Entity // Importa Entity -> Room
import androidx.room.PrimaryKey // Importa PrimaryKey -> Room

@Entity(tableName = "rutina") // Define tabla "rutina" -> SQLite
data class Rutina( // Clase de datos -> Estructura de Rutina
    @PrimaryKey(autoGenerate = true) // Clave primaria autoincremental -> SQLite
    val id: Int = 0, // ID único -> Identificador del registro

    @ColumnInfo(name = "usuario_id") // Columna relación -> SQLite
    val usuarioId: Int, // ID del dueño -> Vincula con Usuario

    @ColumnInfo(name = "ejercicio") // Columna ejercicio -> SQLite
    val ejercicio: String, // Nombre ejercicio -> Texto del usuario

    @ColumnInfo(name = "grupo_muscular") // Columna músculo -> SQLite
    val grupoMuscular: String, // Grupo muscular -> Texto del usuario

    @ColumnInfo(name = "series") // Columna series -> SQLite
    val series: Int, // Número de series -> Valor numérico

    @ColumnInfo(name = "repeticiones") // Columna reps -> SQLite
    val repeticiones: Int, // Número reps -> Valor numérico

    @ColumnInfo(name = "peso_kg") // Columna peso -> SQLite
    val pesoKg: Double, // Kilos usados -> Valor decimal

    @ColumnInfo(name = "fecha") // Columna fecha -> SQLite
    val fecha: String // Día de registro -> Texto con fecha
)
