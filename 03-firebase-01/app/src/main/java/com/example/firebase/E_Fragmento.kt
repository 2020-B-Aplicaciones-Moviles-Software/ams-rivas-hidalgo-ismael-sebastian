package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment

class E_Fragmento : AppCompatActivity() {

    lateinit var fragmentoActual: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_e__fragmento)

        val botonPrimer = findViewById<Button>(R.id.btn_primer_fragmento)
        botonPrimer
            .setOnClickListener { crearFragmentoUno() }
        val botonSegundo = findViewById<Button>(R.id.btn_segundo_fragmento)
        botonSegundo
            .setOnClickListener { crearFragmentoDos() }
    }

    fun crearFragmentoUno(){
        //Manager
        val fragmentManager = supportFragmentManager
        //Transacciones
        val fragmentTransaction = fragmentManager.beginTransaction()
        //Crear instancia del fragmento
        val primerFragmento = PrimerFragmento()
        //Argumentos
        val argumentos = Bundle()
        argumentos.putString("nombre", "Ismael Rivas")
        argumentos.putInt("edad", 31)
        primerFragmento.arguments = argumentos

        //Anadir Fragmentos

        fragmentTransaction.replace(R.id.rl_fragmento, primerFragmento)
        fragmentoActual = primerFragmento
        fragmentTransaction.commit()
    }

    fun crearFragmentoDos(){
        //Manager
        val fragmentManager = supportFragmentManager
        //Transacciones
        val fragmentTransaction = fragmentManager.beginTransaction()
        //Crear instancia del fragmento
        val segundoFragmento = SegundoFragmento()
        //Argumentos
        val argumentos = Bundle()
        //argumentos.putString("nombre", "Ismael Rivas")
        //argumentos.putInt("edad", 31)
        segundoFragmento.arguments = argumentos

        //Anadir Fragmentos

        fragmentTransaction.replace(R.id.rl_fragmento, segundoFragmento)
        fragmentoActual = segundoFragmento
        fragmentTransaction.commit()
    }
}