package com.example.proyecto2bappmov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.example.proyecto2bappmov.adapters.CategoriaListAdapter
import com.example.proyecto2bappmov.dto.FirebaseCategoriaDto
import com.google.firebase.firestore.FirebaseFirestore

class ListaCategoriasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_categorias)

        var listaFin: MutableList<FirebaseCategoriaDto> = mutableListOf()
        val db = FirebaseFirestore.getInstance()
        var categoriaX: FirebaseCategoriaDto
        val referencia = db.collection("categoria")

        referencia
            .get()
            .addOnSuccessListener {
                for (categoria in it) {
                    val mapa = categoria.getData()
                    categoriaX = FirebaseCategoriaDto(mapa.get("tipo").toString(), mapa.get("img_url").toString())
                    listaFin.add(categoriaX)
                }

                Log.i("firebase-consultas", "despues del for: ${listaFin}")
                val adapter = CategoriaListAdapter(this, listaFin)
                val lista = findViewById<ListView>(R.id.lv_lista_categorias)

                lista.adapter = adapter
            }
            .addOnFailureListener {
                Log.i("firebase-consultas", "Error")
            }
    }
}