package com.example.gymtrackerpro.dao // Define el paquete -> Ubicación en el proyecto

import androidx.room.Dao // Importa componente DAO -> Librería Room
import androidx.room.Delete // Importa operación borrar -> Librería Room
import androidx.room.Insert // Importa operación insertar -> Librería Room
import androidx.room.Query // Importa consultas SQL -> Librería Room
import androidx.room.Update // Importa operación actualizar -> Librería Room
import com.example.gymtrackerpro.model.Rutina // Importa clase Rutina -> Capa de modelos
import kotlinx.coroutines.flow.Flow // Importa flujo asíncrono -> Coroutines

@Dao // Marca interfaz como DAO -> Room Compiler
interface RutinaDao {
    @Insert // Define inserción -> SQLite
    suspend fun insertar(rutina: Rutina) // Guarda rutina -> Recibe objeto de Repository

    @Query("SELECT * FROM rutina WHERE usuario_id = :usuarioId ORDER BY fecha DESC") // Consulta SQL -> SQLite
    fun listarPorUsuario(usuarioId: Int): Flow<List<Rutina>> // Obtiene lista reactiva -> Envía Flow a Repository

    @Delete // Define borrado -> SQLite
    suspend fun eliminar(rutina: Rutina) // Borra registro -> Recibe objeto de Repository

    @Query("SELECT * FROM rutina WHERE id = :id") // Consulta búsqueda -> SQLite
    suspend fun buscarPorId(id: Int): Rutina? // Busca por ID -> Envía objeto a Repository

    @Update // Define actualización -> SQLite
    suspend fun actualizar(rutina: Rutina) // Modifica registro -> Recibe objeto de Repository

    @Query("SELECT COUNT(*) FROM rutina WHERE usuario_id = :usuarioId") // Consulta conteo -> SQLite
    suspend fun contarRutinasPorUsuario(usuarioId: Int): Int // Cuenta registros -> Envía entero a Repository

    @Query("SELECT SUM(peso_kg * series * repeticiones) FROM rutina WHERE usuario_id = :usuarioId") // Consulta suma -> SQLite
    suspend fun sumPesoTotalPorUsuario(usuarioId: Int): Double? // Suma totales -> Envía valor a Repository
}