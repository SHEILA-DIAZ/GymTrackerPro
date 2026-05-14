package com.example.gymtrackerpro.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.gymtrackerpro.model.RutinaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RutinaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRutina(rutina: RutinaEntity)

    @Query("SELECT * FROM rutinas ORDER BY id DESC")
    fun getRutinas(): Flow<List<RutinaEntity>>

    @Delete
    suspend fun deleteRutina(rutina: RutinaEntity)

    @Query("SELECT * FROM rutinas WHERE id = :id")
    suspend fun getRutinaById(id: Int): RutinaEntity?
}
