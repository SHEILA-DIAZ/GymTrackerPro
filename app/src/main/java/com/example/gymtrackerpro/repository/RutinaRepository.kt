package com.example.gymtrackerpro.repository

import com.example.gymtrackerpro.dao.RutinaDao
import com.example.gymtrackerpro.model.RutinaEntity
import kotlinx.coroutines.flow.Flow

class RutinaRepository(private val rutinaDao: RutinaDao) {
    val allRutinas: Flow<List<RutinaEntity>> = rutinaDao.getRutinas()

    suspend fun insert(rutina: RutinaEntity) {
        rutinaDao.insertRutina(rutina)
    }

    suspend fun delete(rutina: RutinaEntity) {
        rutinaDao.deleteRutina(rutina)
    }

    suspend fun getRutinaById(id: Int): RutinaEntity? {
        return rutinaDao.getRutinaById(id)
    }
}
