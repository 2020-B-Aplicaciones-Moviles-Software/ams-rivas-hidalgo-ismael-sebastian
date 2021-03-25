package com.example.firebase

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.dto.FirestoreOrdenesDto
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListaOrdenes : AppCompatActivity() {

    private lateinit var ultimoRegistroGlobal: QueryDocumentSnapshot
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_ordenes)

        //val listaOrdenes = mostrarOrdenes()
        var listaFin: MutableList<FirestoreOrdenesDto> = mutableListOf()
        val db = Firebase.firestore
        var ordenX: FirestoreOrdenesDto
        val referencia = db.collection("orden")

        referencia
            .limit(2)
            .get()
            .addOnSuccessListener {
                for (orden in it) {
                    val mapa = orden.getData()
                    ordenX = FirestoreOrdenesDto(mapa.get("restaurante").toString(), Integer.parseInt(mapa.get("review").toString()), mapa.get("tiposComida").toString())
                    listaFin.add(ordenX)
                }

                Log.i("firebase-consultas", "despues del for: ${listaFin}")
                val adapter = OrdenAdapter(this, listaFin)
                val lista = findViewById<ListView>(R.id.lv_ordenes)

                lista.adapter = adapter

                ultimoRegistroGlobal = it.last()
            }
            .addOnFailureListener {
                Log.i("firebase-consultas", "Error")
            }
        
        val botonMasOrdenes = findViewById<Button>(R.id.btn_mas_ordenes)
        botonMasOrdenes
            .setOnClickListener {
                referencia
                    .limit(2)
                    .startAfter(ultimoRegistroGlobal)
                    .get().addOnSuccessListener {
                        for (orden in it) {
                            val mapa = orden.getData()
                            val orden1 = FirestoreOrdenesDto(mapa.get("restaurante").toString(), Integer.parseInt(mapa.get("review").toString()), mapa.get("tiposComida").toString())
                            listaFin.add(orden1)
                        }
                        Log.i("firebase-consultas", "mas: ${listaFin}")
                        val adapter = OrdenAdapter(this, listaFin)
                        val lista = findViewById<ListView>(R.id.lv_ordenes)

                        lista.adapter = adapter

                        ultimoRegistroGlobal = it.last()

                    }
                    .addOnFailureListener {
                        Log.i("firebase-consultas", "Error")
                    }

            }

    }

    fun mostrarOrdenes(): List<FirestoreOrdenesDto>{
        val db = Firebase.firestore
        val referencia = db.collection("orden")
        //var listaOrdenes: List<FirestoreOrdenesDto> = ArrayList()
        var listaOrdenes: MutableList<FirestoreOrdenesDto> = mutableListOf()

        var orden1: FirestoreOrdenesDto

        referencia
            .limit(2)
            .get()
            .addOnSuccessListener {
                for (orden in it) {
                    //Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    //val orden1 = orden.toObject(FirestoreOrdenesDto::class.java)
                    val mapa = orden.getData()
                    //Log.i("firebase-consultas", "${mapa.get("restaurante")}")
                    orden1 = FirestoreOrdenesDto(mapa.get("restaurante").toString(), Integer.parseInt(mapa.get("review").toString()), mapa.get("tiposComida").toString())
                    //Log.i("firebase-consultas", "${orden1}")
                    listaOrdenes.add(orden1)
                }
                Log.i("firebase-consultas", "despues del for: ${listaOrdenes}")
                val ordenesFin = listaOrdenes
                ultimoRegistroGlobal = it.last()
            }
            .addOnFailureListener {
                Log.i("firebase-consultas", "Error")
            }
        Log.i("firebase-consultas", "dentro mostrarOrdenes(): ${listaOrdenes}")

        return listaOrdenes
    }

    fun mostrarMasOrdenes(ultimoRegistro: QueryDocumentSnapshot, listaOrdenes: MutableList<FirestoreOrdenesDto>): MutableList<FirestoreOrdenesDto>{
        val db = Firebase.firestore
        val referencia = db.collection("orden")

        referencia
            .limit(2)
            .startAfter(ultimoRegistro)
            .get().addOnSuccessListener {
                for (orden in it) {
                    val mapa = orden.getData()
                    val orden1 = FirestoreOrdenesDto(mapa.get("restaurante").toString(), Integer.parseInt(mapa.get("review").toString()), mapa.get("tiposComida").toString())
                    listaOrdenes.add(orden1)
                }
            }
            .addOnFailureListener {
                Log.i("firebase-consultas", "Error")
            }

        return listaOrdenes
    }
}