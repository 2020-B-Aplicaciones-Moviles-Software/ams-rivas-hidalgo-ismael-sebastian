package com.example.aplicacionmovil_software

class BEntrenador (nombre: String, descripcion: String) {

    var nombre: String = nombre
    var descripción: String = descripcion

    override fun toString(): String {
        return "${nombre} ${descripción}"
    }
}