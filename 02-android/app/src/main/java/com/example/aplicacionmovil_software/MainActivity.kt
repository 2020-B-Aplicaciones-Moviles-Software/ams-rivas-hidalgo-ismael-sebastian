package com.example.aplicacionmovil_software

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BBaseDeDatos.inicializarEntrenadores()

        val botonCicloVida = findViewById<Button>(R.id.btn_ir_ciclo_vida)
        botonCicloVida
                .setOnClickListener{
                    irAActividad(ACicloVida::class.java)
                }
        val botonlistView = findViewById<Button>(R.id.btn_ir_list_view)
        botonlistView
            .setOnClickListener{
                irAActividad(BListView::class.java)
            }
    }

    fun irAActividad(clase: Class<*>){
         val intentExplicito = Intent(
             this,
             clase
        )
        startActivity(intentExplicito)
    }
}