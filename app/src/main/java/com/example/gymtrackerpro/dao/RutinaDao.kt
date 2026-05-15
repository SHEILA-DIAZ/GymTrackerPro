package com.example.gymtrackerpro.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.gymtrackerpro.model.Rutina
import kotlinx.coroutines.flow.Flow

@Dao
interface RutinaDao {
    @Insert
    suspend fun insertar(rutina: Rutina)

    @Query("SELECT * FROM rutina WHERE usuario_id = :usuarioId ORDER BY fecha DESC")
    fun listarPorUsuario(usuarioId: Int): Flow<List<Rutina>>

    @Delete
    suspend fun eliminar(rutina: Rutina)

    @Query("SELECT * FROM rutina WHERE id = :id")
    suspend fun buscarPorId(id: Int): Rutina?

    @Update
    suspend fun actualizar(rutina: Rutina)

    @Query("SELECT COUNT(*) FROM rutina WHERE usuario_id = :usuarioId")
    suspend fun contarRutinasPorUsuario(usuarioId: Int): Int

    @Query("SELECT SUM(peso_kg * series * repeticiones) FROM rutina WHERE usuario_id = :usuarioId")
    suspend fun sumPesoTotalPorUsuario(usuarioId: Int): Double?
}
