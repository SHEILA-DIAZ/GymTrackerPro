package com.example.gymtrackerpro.repository // Define paquete -> Ubicación en el proyecto

import com.example.gymtrackerpro.dao.RutinaDao // Importa Dao Rutina -> Capa datos
import com.example.gymtrackerpro.dao.UsuarioDao // Importa Dao Usuario -> Capa datos
import com.example.gymtrackerpro.model.Rutina // Importa Modelo Rutina -> Entidad
import com.example.gymtrackerpro.model.Usuario // Importa Modelo Usuario -> Entidad
import kotlinx.coroutines.flow.Flow // Importa Flow -> Stream de datos

class GymRepository( // Clase repositorio -> Mediador entre ViewModel y Datos
    private val usuarioDao: UsuarioDao, // Recibe Dao Usuario -> Inyección de dependencias
    private val rutinaDao: RutinaDao // Recibe Dao Rutina -> Inyección de dependencias
) {
    // Usuario
    suspend fun insertarUsuario(usuario: Usuario) = usuarioDao.insertar(usuario) // Llama al DAO -> Envía objeto a UsuarioDao
    suspend fun buscarUsuarioPorCredenciales(user: String, pass: String) = usuarioDao.buscarPorCredenciales(user, pass) // Valida login -> Pide a UsuarioDao
    suspend fun buscarUsuarioPorId(id: Int) = usuarioDao.buscarPorId(id) // Busca perfil -> Pide a UsuarioDao
    suspend fun existeUsuarioPorEmail(email: String) = usuarioDao.existeUsuarioPorEmail(email) // Chequea email -> Pide a UsuarioDao
    suspend fun existeUsuarioPorNombre(username: String) = usuarioDao.existeUsuarioPorNombre(username) // Chequea nombre -> Pide a UsuarioDao

    // Rutina
    suspend fun insertarRutina(rutina: Rutina) = rutinaDao.insertar(rutina) // Crea rutina -> Envía objeto a RutinaDao
    fun listarRutinasPorUsuario(usuarioId: Int): Flow<List<Rutina>> = rutinaDao.listarPorUsuario(usuarioId) // Obtiene lista -> Pide flujo a RutinaDao
    suspend fun eliminarRutina(rutina: Rutina) = rutinaDao.eliminar(rutina) // Borra registro -> Envía objeto a RutinaDao
    suspend fun buscarRutinaPorId(id: Int) = rutinaDao.buscarPorId(id) // Busca registro -> Pide a RutinaDao
    suspend fun actualizarRutina(rutina: Rutina) = rutinaDao.actualizar(rutina) // Actualiza datos -> Envía objeto a RutinaDao
    suspend fun contarRutinasPorUsuario(usuarioId: Int) = rutinaDao.contarRutinasPorUsuario(usuarioId) // Obtiene conteo -> Pide a RutinaDao
    suspend fun sumPesoTotalPorUsuario(usuarioId: Int) = rutinaDao.sumPesoTotalPorUsuario(usuarioId) // Obtiene suma -> Pide a RutinaDao
}
