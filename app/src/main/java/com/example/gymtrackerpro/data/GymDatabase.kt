package com.example.gymtrackerpro.data // Define paquete -> Ubicación en el proyecto

import android.content.Context // Importa contexto -> Sistema Android
import androidx.room.Database // Importa Database -> Room
import androidx.room.Room // Importa Room -> SQLite Wrapper
import androidx.room.RoomDatabase // Importa clase base -> Room
import com.example.gymtrackerpro.dao.RutinaDao // Importa Dao Rutina -> Capa datos
import com.example.gymtrackerpro.dao.UsuarioDao // Importa Dao Usuario -> Capa datos
import com.example.gymtrackerpro.model.Rutina // Importa Modelo Rutina -> Entidad
import com.example.gymtrackerpro.model.Usuario // Importa Modelo Usuario -> Entidad

@Database(entities = [Usuario::class, Rutina::class], version = 2, exportSchema = false) // Configura base de datos -> SQLite
abstract class GymDatabase : RoomDatabase() { // Clase abstracta de BD -> Room
    abstract fun usuarioDao(): UsuarioDao // Expone Dao Usuario -> Capa datos
    abstract fun rutinaDao(): RutinaDao // Expone Dao Rutina -> Capa datos

    companion object { // Objeto estático -> Singleton
        @Volatile // Garantiza visibilidad -> Memoria RAM
        private var INSTANCE: GymDatabase? = null // Instancia única -> App Lifecycle

        fun getDatabase(context: Context): GymDatabase { // Obtiene BD -> Recibe Contexto
            return INSTANCE ?: synchronized(this) { // Controla acceso concurrente -> Bloqueo de hilos
                val instance = Room.databaseBuilder( // Crea constructor -> Builder de Room
                    context.applicationContext, // Usa contexto global -> Sistema Android
                    GymDatabase::class.java, // Clase de BD -> Definición propia
                    "gym_database" // Nombre de archivo -> Almacenamiento interno
                )
                .fallbackToDestructiveMigration() // Maneja cambios de versión -> Borra datos previos
                .build() // Construye instancia -> Memoria RAM
                INSTANCE = instance // Guarda instancia -> Variable estática
                instance // Retorna objeto -> Envía a Repository
            }
        }
    }
}
