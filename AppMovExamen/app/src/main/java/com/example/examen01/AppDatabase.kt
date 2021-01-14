package com.example.examen01

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.examen01.DAO.GeneroDAO
import com.example.examen01.DAO.PeliculaDAO
import com.example.examen01.Modelo.Genero
import com.example.examen01.Modelo.Pelicula

@Database(entities = [Genero::class, Pelicula::class], version = 2)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun generos(): GeneroDAO
    abstract fun peliculas(): PeliculaDAO

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()

                INSTANCE = instance

                return instance
            }
        }
    }
}