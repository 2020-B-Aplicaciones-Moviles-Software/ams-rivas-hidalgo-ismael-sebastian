package com.example.firebase.dto

data class FirestoreRestaurantesDto (
    var uid: String = "",
    var nombre: String = ""
){
    override fun toString(): String {
        return nombre
    }
}
