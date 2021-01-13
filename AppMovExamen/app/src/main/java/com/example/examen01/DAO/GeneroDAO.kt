package com.example.examen01.DAO

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.examen01.GeneroWithPeliculas
import com.example.examen01.Modelo.Genero

@Dao
interface GeneroDAO {
    @Query("SELECT * FROM genero")
    fun getAllGeneros(): LiveData<List<Genero>>

    @Query("SELECT * FROM genero WHERE idGenero = :id")
    fun getGenero(id: Int): LiveData<Genero>

    @Insert
    fun insertAllGeneros(vararg generos: Genero)

    @Update
    fun updateGenero(genero: Genero)

    @Delete
    fun delete(genero: Genero)

    @Transaction
    @Query("SELECT * FROM genero WHERE idGenero = :id")
    fun getGeneroWithPeliculas(id: Int): List<GeneroWithPeliculas>
}