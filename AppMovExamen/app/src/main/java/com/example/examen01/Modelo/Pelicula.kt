package com.example.examen01.Modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.examen01.DateTypeConverter
import java.io.Serializable
import java.util.*

@Entity(tableName = "pelicula")
class Pelicula (
    val nombrePelicula: String,

    @TypeConverters(DateTypeConverter::class)
    val fechaEstreno: Date,

    val gananciasPelicula: Double,
    val enCartelera: Boolean,
    var idGenero: Int,

    @PrimaryKey(autoGenerate = true)
    var idPelicula: Int = 0
) :Serializable