package com.example.proyecto2bappmov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bumptech.glide.Glide
import com.example.proyecto2bappmov.adapters.CategoriaAdapter
import com.example.proyecto2bappmov.dto.FirebaseCartItemDto
import com.example.proyecto2bappmov.dto.FirebaseDestacadoDto
import com.example.proyecto2bappmov.dto.FirebaseItemsDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class DetallesActivity : AppCompatActivity() {

    private lateinit var imagenItem: ImageView
    private lateinit var nombreItem: TextView
    private lateinit var precioItem: TextView
    private lateinit var desarrolladoraItem: TextView
    private lateinit var distribuidoraItem: TextView
    private lateinit var descripcionItem: TextView
    private lateinit var itemsReviewBar: RatingBar
    private lateinit var obj: Any

    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)
        obj = intent.getSerializableExtra("detalle") as Any
        var destacado: FirebaseDestacadoDto? = null
        //val destacado: FirebaseDestacadoDto = intent.getSerializableExtra("detalle") as FirebaseDestacadoDto
        var item: FirebaseItemsDto? = null
        val user= FirebaseAuth.getInstance().currentUser
        var img_url_item: String? = null
        var precio_item: Double? = null

        db = FirebaseFirestore.getInstance()

        imagenItem = findViewById(R.id.iv_imagen_item)
        nombreItem = findViewById(R.id.tv_nombre_item)
        precioItem = findViewById(R.id.tv_precio_item)

        desarrolladoraItem = findViewById(R.id.tv_desarrolladora_item)
        distribuidoraItem = findViewById(R.id.tv_distribuidora_item)
        descripcionItem = findViewById(R.id.tv_descripcion_item)
        itemsReviewBar = findViewById(R.id.rating_detalles)

        if(obj is FirebaseDestacadoDto){
            destacado = obj as FirebaseDestacadoDto
            Glide.with(applicationContext).load(destacado?.img_url).into(imagenItem)
            nombreItem.text = destacado.nombre
            precioItem.text = "$${destacado.precio}"
            desarrolladoraItem.text = destacado.desarrolladora
            distribuidoraItem.text = destacado.distribuidora
            descripcionItem.text = destacado.descripcion
            img_url_item = destacado.img_url
            precio_item = destacado.precio
            itemsReviewBar.rating = destacado.review.toFloat()

        }else{
            item = obj as FirebaseItemsDto
            Glide.with(applicationContext).load(item.img_url).into(imagenItem)
            nombreItem.text = item.nombre
            precioItem.text = "$${item.precio}"
            desarrolladoraItem.text = item.desarrolladora
            distribuidoraItem.text = item.distribuidora
            descripcionItem.text = item.descripcion
            img_url_item = item?.img_url
            precio_item = item.precio
            itemsReviewBar.rating = item.review.toFloat()
        }

        val botonAddCarrito = findViewById<Button>(R.id.btn_add_carrito_item)
        botonAddCarrito
                .setOnClickListener{

                    val cartItem = FirebaseCartItemDto(user!!.uid, img_url_item, nombreItem.text.toString(), precio_item, desarrolladoraItem.text.toString())
                    db.collection("carrito")
                            .document()
                            .set(cartItem)
                            .addOnCompleteListener {

                            }

                }
    }
}