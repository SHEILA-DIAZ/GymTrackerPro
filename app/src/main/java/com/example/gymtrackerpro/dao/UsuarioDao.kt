package com.example.gymtrackerpro.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.gymtrackerpro.model.Usuario

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertar(usuario: Usuario)

    @Query("SELECT * FROM usuario WHERE nombre_usuario = :user AND password = :pass LIMIT 1")
    suspend fun buscarPorCredenciales(
        user: String,
        pass: String
    ): Usuario?

    @Query("SELECT * FROM usuario WHERE id = :id")
    suspend fun buscarPorId(id: Int): Usuario?

    @Query("SELECT * FROM usuario WHERE email = :email LIMIT 1")
    suspend fun existeUsuarioPorEmail(email: String): Usuario?

    @Query("SELECT * FROM usuario WHERE nombre_usuario = :username LIMIT 1")
    suspend fun existeUsuarioPorNombre(username: String): Usuario?
}
