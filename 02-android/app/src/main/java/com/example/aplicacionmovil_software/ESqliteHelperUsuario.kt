package com.example.aplicacionmovil_software

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ESqliteHelperUsuario (
        contexto: Context?
) : SQLiteOpenHelper(
        contexto,
        "moviles",
        null,
        1
){
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptCrearTablaUsuario =
                """
                CREATE TABLE USUARIO(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    descripcion VARCHAR(50) 
                )
                """.trimIndent()

        Log.i("bdd", "Creando la tabla de usuario")
        db?.execSQL(scriptCrearTablaUsuario)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun consultarUsuarioPorId(id: Int): EUsuarioBDD{
        val scriptConsultaUsuario = "SELECT * FROM USUARIO WHERE id = ${id}"

        val conexionLectura = readableDatabase

        val resultadoConsulta = conexionLectura

                .rawQuery(
                        scriptConsultaUsuario,
                        null
                )
        val existeUsuario = resultadoConsulta.moveToFirst()
        val usuarioEncontrado = EUsuarioBDD(0, "", "")

        if(existeUsuario){
            do{
                val id = resultadoConsulta.getInt(0)
                val nombre = resultadoConsulta.getString(1)
                val descripcion = resultadoConsulta.getString(2)
                if(id != null){
                    usuarioEncontrado.id = id
                    usuarioEncontrado.nombre = nombre
                    usuarioEncontrado.descripcion = descripcion
                }
            }while(resultadoConsulta.moveToNext())
        }

        resultadoConsulta.close()
        conexionLectura.close()

        return usuarioEncontrado
    }

    fun crearUsuarioFormulario(
            nombre: String,
            descripcion: String
    ) : Boolean{
        val conexionEscritura = writableDatabase

        val valoresAGuardar = ContentValues()

        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)

        val resultadoEscritura: Long = conexionEscritura
                .insert(
                        "USUARIO",
                        null,
                        valoresAGuardar
                )
        conexionEscritura.close()
        return if (resultadoEscritura.toInt() == -1) false else true

    }

    fun actualizarUsuarioFormulario(
            nombre: String,
            descripcion: String,
            idActualizar: Int
    ) : Boolean{
        val conexionEscritura = writableDatabase

        val valoresAActualizar = ContentValues()

        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)

        val resultadoActualizacion= conexionEscritura
                .update(
                        "USUARIO",
                        valoresAActualizar,
                        "id = ?",
                        arrayOf(
                                idActualizar.toString()
                        )
                )
        conexionEscritura.close()
        return if (resultadoActualizacion.toInt() == -1) false else true
    }

    fun eliminarUsuarioFormulario(id: Int): Boolean{

        val conexionEscritura = writableDatabase
        val resultadoEliminacion = conexionEscritura
                .delete(
                        "USUARIO",
                        "id=?",
                        arrayOf(
                                id.toString()
                        )
                )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }
}