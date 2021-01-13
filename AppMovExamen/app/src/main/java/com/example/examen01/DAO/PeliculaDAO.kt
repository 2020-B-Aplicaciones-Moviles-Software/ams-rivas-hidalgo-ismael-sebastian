package com.example.examen01.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.examen01.Modelo.Pelicula

@Dao
interface PeliculaDAO {
    @Query("SELECT * FROM pelicula")
    fun getAllPeliculas(): LiveData<List<Pelicula>>

    @Query("SELECT * FROM pelicula WHERE idPelicula = :id")
    fun getPelicula(id: Int): LiveData<Pelicula>

    @Insert
    fun insertAllPeliculas(vararg peliculas: Pelicula)

    @Update
    fun updatePelicula(pelicula: Pelicula)

    @Delete
    fun delete(pelicula: Pelicula)

    @Query("SELECT * FROM pelicula WHERE idGenero = :idGenero")
    fun getAllPeliculasByGenero(idGenero: Int): LiveData<List<Pelicula>>
}