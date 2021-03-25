package com.example.proyecto2bappmov.dto

import java.io.Serializable

data class FirebaseItemsDto (
        var descripcion: String,
        var img_url: String,
        var nombre: String,
        var precio: Double,
        var review: Int,
        var desarrolladora: String,
        var distribuidora: String,
        var categoria: String
): Serializable{

}