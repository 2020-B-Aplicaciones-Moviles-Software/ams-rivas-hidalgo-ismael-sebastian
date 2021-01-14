package com.example.examen01

import androidx.room.Embedded
import androidx.room.Relation
import com.example.examen01.Modelo.Genero
import com.example.examen01.Modelo.Pelicula

class GeneroWithPeliculas(
        @Embedded val genero: Genero,
        @Relation(
                parentColumn = "idGenero",
                entityColumn = "idGenero"
        )
        val peliculas: List<Pelicula>
)