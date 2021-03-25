package com.example.proyecto2bappmov

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.proyecto2bappmov.adapters.CartAdapter
import com.example.proyecto2bappmov.adapters.CategoriaListAdapter
import com.example.proyecto2bappmov.dto.FirebaseCartItemDto
import com.example.proyecto2bappmov.dto.FirebaseCategoriaDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CartActivity : AppCompatActivity() {

    lateinit var myDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        var precioTotal = 0.0

        myDialog = Dialog(this)

        var listaFin: MutableList<FirebaseCartItemDto> = mutableListOf()
        val db = FirebaseFirestore.getInstance()
        val user= FirebaseAuth.getInstance().currentUser
        var itemX: FirebaseCartItemDto
        val referencia = db.collection("carrito")

        referencia
                .whereEqualTo("userid", user?.uid)
                .get()
                .addOnSuccessListener {
                    for (cartItem in it) {
                        val mapa = cartItem.getData()
                        itemX = FirebaseCartItemDto(mapa.get("userid").toString(), mapa.get("img_url").toString(), mapa.get("nombre").toString(), mapa.get("precio").toString().toDouble(), mapa.get("desarrolladora").toString())
                        listaFin.add(itemX)
                        precioTotal += itemX.precio
                    }

                    Log.i("firebase-consultas", "despues del for: ${listaFin}")
                    val tv_precioTotal = findViewById<TextView>(R.id.tv_precio_total_cart)
                    val precioTotalstr = String.format("%.2f", precioTotal)
                    tv_precioTotal.text = "$${precioTotalstr}"
                    val adapter = CartAdapter(this, listaFin)
                    val lista = findViewById<ListView>(R.id.lv_lista_cart_items)

                    lista.adapter = adapter
                    adapter.notifyDataSetChanged()
                }
                .addOnFailureListener {
                    Log.i("firebase-consultas", "Error")
                }

        val botonComprar = findViewById<Button>(R.id.btn_comprar_cart)
        botonComprar
                .setOnClickListener {
                    val referenciaGroup = db.collection("carrito")
                    referenciaGroup
                            .whereEqualTo("userid", user?.uid)
                            .get()
                            .addOnSuccessListener {
                                for(item in it) {
                                    item.reference
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

                    showPopup()
                }
    }

    fun showPopup(){
        myDialog.setContentView(R.layout.pago_popup)
        val botonContinuar = myDialog.findViewById<Button>(R.id.btn_continuar_cart)
        botonContinuar
                .setOnClickListener {
                    val intent = Intent(this, InicioActivity::class.java)
                    startActivity(intent)
                }
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
    }
}