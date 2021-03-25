package com.example.firebase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.firebase.dto.FirestoreRestaurantesDto
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

        //buscarOrdenes()
        popularDatos()
        //eliminacion()
        //eliminarDocumentoMedianteConsulta()
        //eliminarDocumentoMedianteConsulta2()
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
        /*
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
         */

        /*
        val referenciaCiudad = db.collection("cities")
        val referenciaLandBJ = referenciaCiudad.document("BJ").collection("landmark")

        referenciaLandBJ
                .whereEqualTo("landmarkType", "park")
                .get()
                .addOnSuccessListener {
                    for(ciudad in it) {
                        Log.i("firebase-consultas", "${ciudad.id} ${ciudad.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-consultas", "Error")
                }

        */
        //Todos los landmarks de tipo park
        /*
        val referenciaLandmark = db.collectionGroup("landmark")
        referenciaLandmark
                .whereEqualTo("landmarkType", "park")
                .get()
                .addOnSuccessListener {
                    for(city in it) {
                        Log.i("firebase-consultas", "${city.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore", "${it}")
                }
         */
        referencia
                .limit(2)
                .get()
                .addOnSuccessListener {
                    for (orden in it) {
                        Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                    }
                    val ultimoRegistro: QueryDocumentSnapshot = it.last()

                    referencia
                            .limit(2)
                            .startAfter(ultimoRegistro)
                            .get().addOnSuccessListener {
                                for (orden in it) {
                                    Log.i("firebase-consultas", "${orden.id} ${orden.data}")
                                }
                            }
                            .addOnFailureListener {
                                Log.i("firebase-consultas", "Error")
                            }
                }
                .addOnFailureListener {
                    Log.i("firebase-consultas", "Error")
                }

        // Crear una actividad
        // ListView para mostrar las ordenes
        // Boton para cargar mas registros




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

    fun popularDatos(){

        val db = Firebase.firestore

        val cities = db.collection("cities")
        /*
        val data1 = hashMapOf("name" to "San Francisco","state" to "CA","country" to "USA","capital" to false,"population" to 860000,"regions" to listOf("west_coast", "norcal"))
        cities.document("SF").set(data1)
        val data2 = hashMapOf("name" to "Los Angeles","state" to "CA","country" to "USA","capital" to false,"population" to 3900000,"regions" to listOf("west_coast", "socal"))
        cities.document("LA").set(data2)
        val data3 = hashMapOf("name" to "Washington D.C.","state" to null,"country" to "USA","capital" to true,"population" to 680000,"regions" to listOf("east_coast"))
        cities.document("DC").set(data3)
        val data4 = hashMapOf("name" to "Tokyo","state" to null,"country" to "Japan","capital" to true,"population" to 9000000,"regions" to listOf("kanto", "honshu"))
        cities.document("TOK").set(data4)
        val data5 = hashMapOf("name" to "Beijing","state" to null,"country" to "China","capital" to true,"population" to 21500000,"regions" to listOf("jingjinji", "hebei"))
        cities.document("BJ").set(data5)
        */
        /*
        val landmark1 = hashMapOf("landmarkType" to "park", "name" to "parque1")
        cities.document("BJ").collection("landmark").add(landmark1)

        val landmark2 = hashMapOf("landmarkType" to "park", "name" to "parque2")
        cities.document("BJ").collection("landmark").add(landmark2)

        val landmark3 = hashMapOf("landmarkType" to "park", "name" to "parque3")
        cities.document("BJ").collection("landmark").add(landmark3)

        val landmark4 = hashMapOf("landmarkType" to "museo", "name" to "xddd")
        cities.document("BJ").collection("landmark").add(landmark4)

        val landmark6 = hashMapOf("landmarkType" to "park", "name" to "parqueDC")
        cities.document("DC").collection("landmark").add(landmark6)

        val landmark7 = hashMapOf("landmarkType" to "park", "name" to "parqueLA")
        cities.document("LA").collection("landmark").add(landmark7)

        val landmark8 = hashMapOf("landmarkType" to "park", "name" to "parqueSF")
        cities.document("SF").collection("landmark").add(landmark8)

         */
        val landmark9 = hashMapOf("landmarkType" to "museo", "name" to "museo1")
        cities.document("BJ").collection("landmark").add(landmark9)

        val landmark10 = hashMapOf("landmarkType" to "museo", "name" to "museo2")
        cities.document("BJ").collection("landmark").add(landmark10)

        val landmark11 = hashMapOf("landmarkType" to "museo", "name" to "museo3")
        cities.document("BJ").collection("landmark").add(landmark11)

        val landmark12 = hashMapOf("landmarkType" to "museo", "name" to "museo3")
        cities.document("DC").collection("landmark").add(landmark12)

        val landmark13 = hashMapOf("landmarkType" to "museo", "name" to "museo3")
        cities.document("DC").collection("landmark").add(landmark13)

        val landmark14 = hashMapOf("landmarkType" to "museo", "name" to "museo3")
        cities.document("LA").collection("landmark").add(landmark14)

        val landmark15 = hashMapOf("landmarkType" to "museo", "name" to "museo3")
        cities.document("SF").collection("landmark").add(landmark15)

    }

    fun eliminacion(){
        val db = Firebase.firestore

        val docRef = db
                .collection("cities")
                .document("BJ")
                .collection("landmark")
                .document("CDtHIr2tZc1xPsRqMsWt")
        /*
        val eliminarCampo = hashMapOf<String, Any>(
                "name" to FieldValue.delete()
        )
        docRef
                .update(eliminarCampo)
                .addOnSuccessListener {
                    Log.i("firebase-delete", "${it}")
                }
                .addOnFailureListener {
                    Log.i("firebase-delete", "Error eliminando campo")
                }

        */

        docRef
                .delete()
                .addOnSuccessListener {
                    Log.i("firebase-delete", "${it}")
                }
                .addOnFailureListener {
                    Log.i("firebase-delete", "Error eliminando campo")
                }
    }

    fun eliminarDocumentoMedianteConsulta(){

        val db = Firebase.firestore

        val referenciaLandmark = db.collectionGroup("landmark")
        referenciaLandmark
                .whereEqualTo("landmarkType", "museo")
                .get()
                .addOnSuccessListener {
                    for(city in it) {
                        Log.i("firebase-consultas", "id: ${city.id}  ${city.data}")
                        val docRef = db

                        docRef
                                .collection("cities")
                                .document("BJ")
                                .collection("landmark")
                                .document("${city.id}")
                                .delete()
                                .addOnSuccessListener {
                                    Log.i("firebase-eliminacion", "Eliminado con exito")
                                }
                                .addOnCanceledListener {
                                    Log.i("firebase-eliminacion", "Error eliminando")
                                }
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore", "${it}")
                }

    }

    fun eliminarDocumentoMedianteConsulta2(){

        val db = Firebase.firestore

        val referenciaLandmark = db.collectionGroup("landmark")
        referenciaLandmark
                .whereEqualTo("landmarkType", "museo")
                .get()
                .addOnSuccessListener {
                    for(city in it) {

                        city.reference
                                .delete()
                                .addOnSuccessListener {
                                    Log.i("firebase-eliminacion", "Eliminado con exito")
                                }
                                .addOnCanceledListener {
                                    Log.i("firebase-eliminacion", "Error eliminando")
                                }
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore", "${it}")
                }
    }
}