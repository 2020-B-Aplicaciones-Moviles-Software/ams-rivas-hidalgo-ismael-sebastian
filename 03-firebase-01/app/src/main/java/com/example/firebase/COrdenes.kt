package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.firebase.dto.FirestoreRestaurantesDto
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import java.util.*

class COrdenes : AppCompatActivity() {

    val arregloRestaurantes = arrayListOf<FirestoreRestaurantesDto>()
    var adaptadorRestaurantes: ArrayAdapter<FirestoreRestaurantesDto>? = null
    var restauranteSeleccionado: FirestoreRestaurantesDto? = null

    val arregloTiposComida = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_ordenes)

        if(adaptadorRestaurantes == null) {
            adaptadorRestaurantes = ArrayAdapter(this,
                    android.R.layout.simple_spinner_item,
                    arregloRestaurantes
            )
            adaptadorRestaurantes?.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item
            )
            cargarRestaurantes()
        }

        val textViewTipoComida = findViewById<TextView>(R.id.tv_tipos_comida)
        textViewTipoComida.setText("")

        val botonAnadirTipoComida = findViewById<Button>(R.id.btn_anadir_tipo_comida)
        botonAnadirTipoComida
            .setOnClickListener {
                agregarTipoComida()
            }

        val botonCrearOrden = findViewById<Button>(R.id.btn_crear_orden)
        botonCrearOrden
            .setOnClickListener {
                crearOrden()
            }

        buscarOrdenes()

    }

    fun buscarOrdenes(){
        val db = Firebase.firestore
        val referencia = db.collection("orden")
/*
        referencia
                .whereEqualTo("review", 3)
                .get()
                .addOnSuccessListener {
                    for(orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-consultas", "Error")
                }
*/
        //buscar por dos campos ==
        /*referencia
                .whereEqualTo("review", 3)
                .whereEqualTo("restaurante.nombre", "Tripas de la Floresta")
                .get()
                .addOnSuccessListener {
                    for(orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-consultas", "Error")
                }

         */

        //Buscar por dos o más elementos campo == array-contains
        /*
        referencia
                .whereEqualTo("restaurante.nombre", "Tripas de la Floresta")
                .whereArrayContains("tiposComida", "chancrosa")
                .get()
                .addOnSuccessListener {
                    for (orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-consultas", "Error")
                }


         */
        //Buscar por dos o más elementos campo '==' '>='
        /*
        referencia
                .whereEqualTo("restaurante.nombre", "Tripas de la Floresta")
                .whereGreaterThanOrEqualTo("review", 3)
                .get()
                .addOnSuccessListener {
                    for (orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-consultas", "Error")
                }
        */

        //Buscar por dos o más elementos campo '==' '>='
        /*
        referencia
                .whereEqualTo("restaurante.nombre", "Tripas de la Floresta")
                //.whereGreaterThanOrEqualTo("review", 2)
                .whereEqualTo("usuario", "b@b.com")
                .get()
                .addOnSuccessListener {
                    for (orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-consultas", "Error")
                }

         */

        //Buscar por dos o más elementos campo '==' '>=' ordenar descendente los nombres
        /*
        referencia
                .whereEqualTo("restaurante.nombre", "Tripas de la Floresta")
                .whereGreaterThanOrEqualTo("review", 2)
                .orderBy("review", Query.Direction.DESCENDING)  //Enviar la búsqueda NO IGUAL primero
                .get()
                .addOnSuccessListener {
                    for (orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-consultas", "Error")
                }
         */
        //Buscar por dos o más elementos campo '==' '>=' ordenar descendente los nombres
        /*
        referencia
                .whereEqualTo("restaurante.nombre", "Tifozzi")
                .whereArrayContainsAny("tiposComida", arrayListOf("Italiana", "Ecuatoriana"))
                .get()
                .addOnSuccessListener {
                    for (orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-consultas", "Error")
                }
        */
        //WHERE IN
        referencia
                .whereIn("restaurante.nombre", arrayListOf("Tifozzi", "Tripas de la Floresta", "pepito"))
                .whereGreaterThanOrEqualTo("review", 1)
                .get()
                .addOnSuccessListener {
                    for (orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-consultas", "Error")
                }
    }

    fun cargarRestaurantes(){

        val spinnerRestaurante = findViewById<Spinner>(R.id.sp_restaurantes)

        spinnerRestaurante.adapter = adaptadorRestaurantes

        spinnerRestaurante
                .onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.i("firebase-firestore", "No seleccionó nada")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                restauranteSeleccionado = arregloRestaurantes[position]
            }
        }


        val db = Firebase.firestore
        val referencia = db.collection("restaurante")

        referencia.get()
                .addOnSuccessListener {     //query snapshot, me regresa una serie de resultados
                    for(restaurante in it){
                        val restauranteCasteado = restaurante.toObject(
                                FirestoreRestaurantesDto::class.java
                        )
                        restauranteCasteado.uid = restaurante.id
                        arregloRestaurantes.add(restauranteCasteado)
                    }
                    adaptadorRestaurantes?.notifyDataSetChanged()
                }
                .addOnFailureListener{
                    Log.i("firebase-firestore", "Error ${it}")
                }
    }

    fun agregarTipoComida(){
        val etTipoComida = findViewById<EditText>(R.id.et_tipo_comida)
        val textoTipoComida = etTipoComida.text.toString()

        arregloTiposComida.add(textoTipoComida)

        val textViewTipoComida = findViewById<TextView>(R.id.tv_tipos_comida)

        val textoAnterior = textViewTipoComida.text.toString()

        textViewTipoComida.setText("${textoAnterior}. ${textoTipoComida}")
        etTipoComida.setText("")
    }

    fun crearOrden(){
        if(restauranteSeleccionado != null && FirebaseAuth.getInstance().currentUser!=null){

            val restaurante = restauranteSeleccionado
            val usuario = BAuthUsuario.usuario
            val editTextReview = findViewById<EditText>(R.id.et_review)

            val nuevaOrden = hashMapOf<String, Any>(
                "restaurante" to restaurante.toString(),
                "usuario" to usuario.toString(),
                "review" to editTextReview.text.toString().toInt(),
                "tiposComida" to arregloTiposComida,
                "fechaCreacion" to Timestamp(Date())
            )

            val db = Firebase.firestore
            val referencia = db.collection("orden")
                .document()

            referencia
                .set(nuevaOrden)
                .addOnSuccessListener {  }
                .addOnFailureListener {  }
        }
    }
}