package com.example.firebase.dto

data class FirestoreOrdenesDto (
        var restaurante: String = "",
        var review: Int,
        var tiposComida: String = ""
){
    override fun toString(): String {
        return "Restaurante: ${restaurante} review: ${review} Tipos de comida: ${tiposComida}"
    }
}