package com.example.gymtrackerpro.model // Define paquete -> Ubicación en el proyecto

import androidx.room.ColumnInfo // Importa ColumnInfo -> Room
import androidx.room.Entity // Importa Entity -> Room
import androidx.room.PrimaryKey // Importa PrimaryKey -> Room

@Entity(tableName = "usuario") // Define tabla "usuario" -> SQLite
data class Usuario( // Clase de datos -> Estructura de Usuario
    @PrimaryKey(autoGenerate = true) // Clave autoincremental -> SQLite
    val id: Int = 0, // ID único -> Identificador del registro

    @ColumnInfo(name = "nombre_usuario") // Columna username -> SQLite
    val nombreUsuario: String, // Alias login -> Recibe de Registro

    @ColumnInfo(name = "password") // Columna password -> SQLite
    val password: String, // Clave acceso -> Recibe de Registro

    @ColumnInfo(name = "nombre_completo") // Columna nombre real -> SQLite
    val nombreCompleto: String, // Nombre persona -> Recibe de Registro

    @ColumnInfo(name = "email") // Columna correo -> SQLite
    val email: String, // Correo contacto -> Recibe de Registro

    @ColumnInfo(name = "edad") // Columna edad -> SQLite
    val edad: Int, // Años usuario -> Recibe de Registro

    @ColumnInfo(name = "fecha_registro") // Columna fecha -> SQLite
    val fechaRegistro: String // Momento creación -> Generado en Registro
)
