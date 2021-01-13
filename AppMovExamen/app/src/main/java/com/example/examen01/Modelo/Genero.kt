package com.example.examen01.Modelo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.examen01.DateTypeConverter
import java.io.Serializable
import java.util.*

@Entity(tableName = "genero")
class Genero(
    val nombreGenero: String,

    @TypeConverters(DateTypeConverter::class)
    val fechaOrigen: Date,

    val numSubgeneros: Int,
    val gananciasGenero: Double,
    val activo: Boolean,

    @PrimaryKey(autoGenerate = true)
    var idGenero: Int = 0
) :Serializable