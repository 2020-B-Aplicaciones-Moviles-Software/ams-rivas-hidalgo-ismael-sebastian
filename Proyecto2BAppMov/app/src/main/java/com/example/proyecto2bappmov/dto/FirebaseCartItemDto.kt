package com.example.proyecto2bappmov.dto

import java.io.Serializable

data class FirebaseCartItemDto (
        var userid: String,
        var img_url: String,
        var nombre: String,
        var precio: Double,
        var desarrolladora: String

): Serializable {
}