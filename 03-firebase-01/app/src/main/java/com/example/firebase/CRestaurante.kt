package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CRestaurante : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_restaurante)

        val botonCrear = findViewById<Button>(R.id.btn_crear_restaurante)

        botonCrear
                .setOnClickListener {
                    crearRestaurante()
                }

    }

    fun crearRestaurante(){
        val editTextNombre = findViewById<EditText>(R.id.et_nombre_restaurante)

        val nuevoRestaurante = hashMapOf<String, Any>(
                "nombre" to editTextNombre.text.toString()
        )
        Log.i("firebase-firestore", "$nuevoRestaurante")

        val db = Firebase.firestore
        val referencia = db.collection("restaurante")
                .document()
        referencia
                .set(nuevoRestaurante)
                .addOnSuccessListener {}
                .addOnFailureListener {}
    }
}