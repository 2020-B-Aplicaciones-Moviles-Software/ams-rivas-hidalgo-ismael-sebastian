package com.example.aplicacionmovil_software

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //BBaseDeDatos.inicializarEntrenadores()

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

        val botonIrIntentExplicitoParametros = findViewById<Button>(R.id.btn_ir_intent_explicito_parametros)
        botonIrIntentExplicitoParametros
            .setOnClickListener{
                val intentExplicito = Intent(
                        this,
                        CIntentExplicitoParametros::class.java
                )
                intentExplicito.putExtra("nombre", "Ismael")
                intentExplicito.putExtra("apellido", "Rivas")
                intentExplicito.putExtra("edad", 31)

                val ligaPokemon = DLiga("Liga Kanto", "Kanto")
                val ash = BEntrenador("Ash", "Pueblo Paleta", ligaPokemon)

                intentExplicito.putExtra("entrenador", ash)

                startActivityForResult(intentExplicito, 102)

                /*val parametros = arrayListOf<Pair<String, *>>(
                    Pair("nombre", "Ismael"),
                    Pair("apellido", "Rivas"),
                    Pair("edad", 22)
                )
                irAActividad(CIntentExplicitoParametros::class.java, parametros, 102)
                 */
            }

        EBaseDeDatos.TablaUsuario = ESqliteHelperUsuario(this)
        if(EBaseDeDatos.TablaUsuario != null){
            val usuarioEncontrado = EBaseDeDatos.TablaUsuario?.consultarUsuarioPorId(1)

            Log.i(
                    "bdd", "ID: ${usuarioEncontrado?.id} Nombre: ${usuarioEncontrado?.nombre}" +
                    "Descripcion: ${usuarioEncontrado?.descripcion}"
            )

            if(usuarioEncontrado?.id == 0){
                val resultadoCrear = EBaseDeDatos.TablaUsuario
                        ?.crearUsuarioFormulario("Ismael", "Estudiante")
                if(resultadoCrear!= null){
                    if(resultadoCrear){
                        Log.i("bdd", "Se crea correctamente")
                    } else {
                        Log.i("bdd", "hubo errores")
                    }
                }
            } else {
                val resultadoActualizar = EBaseDeDatos.TablaUsuario
                        ?.actualizarUsuarioFormulario(
                                "Sebastián",
                                Date().time.toString(),
                                1
                        )
                if(resultadoActualizar != null) {
                    if(resultadoActualizar) {
                        Log.i("bdd", "Se actualizo")
                    }else{
                        Log.i("bdd", "Errores")
                    }
                }
            }
        }

    } // fin onCreate

    fun irAActividad(
        clase: Class<*>,
        parametros: ArrayList<Pair<String, *>>? = null,
        codigo: Int? = null
    ){
         val intentExplicito = Intent(
             this,
             clase
        )

        if (parametros != null){
            parametros.forEach{
                val nombreVariable = it.first
                val valorVariable = it.second

                var tipoDato = false

                tipoDato = it.second is String
                if(tipoDato == true){
                    intentExplicito.putExtra(nombreVariable, valorVariable as String)
                    tipoDato = false
                }

                tipoDato = it.second is Int
                if(tipoDato == true){
                    intentExplicito.putExtra(nombreVariable, valorVariable as Int)
                    tipoDato = false
                }
            }
        }

        if (codigo != null){
            startActivityForResult(intentExplicito, codigo)
        }else{
            startActivity(intentExplicito)
        }
    }

    override fun onActivityResult(
        requestCode: Int,   //Codigo ejm: 102
        resultCode: Int,    //Resultado -> OK o CANCELED
        data: Intent?       // Inten (datos)
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            102 -> {
                if(resultCode == RESULT_OK){
                    if(data != null){
                        val nombre = data.getStringExtra("nombre")
                        val edad = data.getIntExtra("edad", 0)

                        Log.i("intent-explicito", "Nombre: ${nombre}  Edad: ${edad}")
                    }
                }else{
                    Log.i("intent-explicito", "Usuario no lleno de datos")
                }
            }
        }
    }
}