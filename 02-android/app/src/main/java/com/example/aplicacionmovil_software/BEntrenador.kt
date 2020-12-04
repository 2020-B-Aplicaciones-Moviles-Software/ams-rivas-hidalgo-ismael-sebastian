package com.example.aplicacionmovil_software

class BEntrenador {

    //propiedad Nombre public String
    //propiedad Descripción public String

    var nombre: String
    var descripción: String

    constructor(nombre: String, descripción: String) {
        this.nombre = nombre
        this.descripción = descripción
    }

    override fun toString(): String {
        return "${nombre} ${descripción}"
        
    }
}