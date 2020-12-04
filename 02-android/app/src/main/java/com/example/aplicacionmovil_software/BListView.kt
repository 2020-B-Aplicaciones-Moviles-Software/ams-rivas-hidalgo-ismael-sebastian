package com.example.aplicacionmovil_software

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView

class BListView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_list_view)

        val listView = findViewById<ListView>(R.id.lv_list_view)
        val arreglo = arrayListOf(1, 2, 3, 5)



        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,    //cómo se va a ver
            BBaseDeDatos.arregloEntrenadores //Arreglo
        )

        listView.adapter = adaptador

        val botonListView = findViewById<Button>(R.id.btn_list_view)
        botonListView
            .setOnClickListener{
                anadirItem(BBaseDeDatos.arregloEntrenadores, adaptador)
            }
    }

    fun anadirItem(
        arreglo: ArrayList<BEntrenador>,
        adaptador: ArrayAdapter<*>
    ) {
        BBaseDeDatos.anadirItemAlArreglo(BEntrenador("Ash", "Pueblo Paleta"))
        adaptador.notifyDataSetChanged()
    }
}