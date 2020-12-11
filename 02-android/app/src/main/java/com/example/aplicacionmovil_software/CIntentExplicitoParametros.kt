package com.example.aplicacionmovil_software

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_intent_explicito_parametros)

        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad", 0)

        val entrenador = intent.getParcelableExtra<BEntrenador>("entrenador")

        Log.i("intent-explicito", "${entrenador?.nombre}" +
                "\"${entrenador?.descripcion}\""+
                "\"${entrenador?.liga!!.nombre}\""+
                "\"${entrenador?.liga!!.descripcion}\""
        )

        if(nombre != null && apellido != null && edad != 0){
            Log.i(
                "intent-explicito", "Nombre: ${nombre + " " + apellido} " + "Edad: ${edad}"
            )
        }else{
            Log.i(
                "intent-explicito", "ERROR no encontramos parámetros"
            )
        }

        val botonResponder = findViewById<Button>(R.id.btn_devolver_respuesta_intent)
        botonResponder
            .setOnClickListener{
                val nombreModificado = "Sebastian"
                val edadModificada = 30

                val intentResponder = Intent()
                intentResponder.putExtra("nombre", nombreModificado)
                intentResponder.putExtra("edad", edadModificada)

                this.setResult(
                    RESULT_OK,
                    intentResponder
                )
                this.finish()
            }
    }
}