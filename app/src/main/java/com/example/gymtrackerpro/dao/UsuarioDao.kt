package com.example.gymtrackerpro.dao // Define paquete -> Ubicación en el proyecto

import androidx.room.Dao // Importa DAO -> Room
import androidx.room.Insert // Importa Insert -> Room
import androidx.room.Query // Importa Query -> Room
import com.example.gymtrackerpro.model.Usuario // Importa Usuario -> Modelo de datos

@Dao // Define interfaz de acceso -> Room
interface UsuarioDao {

    @Insert // Define inserción -> SQLite
    suspend fun insertar(usuario: Usuario) // Inserta usuario -> Recibe de Repository

    @Query("SELECT * FROM usuario WHERE nombre_usuario = :user AND password = :pass LIMIT 1") // Consulta credenciales -> SQLite
    suspend fun buscarPorCredenciales( // Busca por login -> Datos desde login screen
        user: String, // Recibe usuario -> UI/ViewModel
        pass: String // Recibe contraseña -> UI/ViewModel
    ): Usuario? // Retorna usuario o null -> Envía a Repository

    @Query("SELECT * FROM usuario WHERE id = :id") // Consulta por ID -> SQLite
    suspend fun buscarPorId(id: Int): Usuario? // Obtiene perfil -> Envía a Repository

    @Query("SELECT * FROM usuario WHERE email = :email LIMIT 1") // Verifica correo -> SQLite
    suspend fun existeUsuarioPorEmail(email: String): Usuario? // Valida registro -> Envía a Repository

    @Query("SELECT * FROM usuario WHERE nombre_usuario = :username LIMIT 1") // Verifica nombre -> SQLite
    suspend fun existeUsuarioPorNombre(username: String): Usuario? // Valida registro -> Envía a Repository
}
