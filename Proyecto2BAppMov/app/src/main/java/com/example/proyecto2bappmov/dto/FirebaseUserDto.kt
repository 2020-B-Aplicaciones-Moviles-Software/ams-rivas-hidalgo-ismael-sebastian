package com.example.proyecto2bappmov.dto

import java.io.Serializable

data class FirebaseUserDto (
    var nombre_usr: String,
    var nombre_usuario_usr: String,
    var email: String,
    var edad: Int,
    var perfil_url: String
): Serializable{

}