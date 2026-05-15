package com.example.gymtrackerpro.repository

import com.example.gymtrackerpro.dao.RutinaDao
import com.example.gymtrackerpro.dao.UsuarioDao
import com.example.gymtrackerpro.model.Rutina
import com.example.gymtrackerpro.model.Usuario
import kotlinx.coroutines.flow.Flow

class GymRepository(
    private val usuarioDao: UsuarioDao,
    private val rutinaDao: RutinaDao
) {
    // Usuario
    suspend fun insertarUsuario(usuario: Usuario) = usuarioDao.insertar(usuario)
    suspend fun buscarUsuarioPorCredenciales(user: String, pass: String) = usuarioDao.buscarPorCredenciales(user, pass)
    suspend fun buscarUsuarioPorId(id: Int) = usuarioDao.buscarPorId(id)
    suspend fun existeUsuarioPorEmail(email: String) = usuarioDao.existeUsuarioPorEmail(email)
    suspend fun existeUsuarioPorNombre(username: String) = usuarioDao.existeUsuarioPorNombre(username)

    // Rutina
    suspend fun insertarRutina(rutina: Rutina) = rutinaDao.insertar(rutina)
    fun listarRutinasPorUsuario(usuarioId: Int): Flow<List<Rutina>> = rutinaDao.listarPorUsuario(usuarioId)
    suspend fun eliminarRutina(rutina: Rutina) = rutinaDao.eliminar(rutina)
    suspend fun buscarRutinaPorId(id: Int) = rutinaDao.buscarPorId(id)
    suspend fun actualizarRutina(rutina: Rutina) = rutinaDao.actualizar(rutina)
    suspend fun contarRutinasPorUsuario(usuarioId: Int) = rutinaDao.contarRutinasPorUsuario(usuarioId)
    suspend fun sumPesoTotalPorUsuario(usuarioId: Int) = rutinaDao.sumPesoTotalPorUsuario(usuarioId)
}
