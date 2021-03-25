package com.example.proyecto2bappmov.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.proyecto2bappmov.InicioActivity
import com.example.proyecto2bappmov.R
import com.example.proyecto2bappmov.dto.FirebaseCartItemDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.single_cart_item.view.*

class CartAdapter (private val mContext: Context, private val listaCartItems: MutableList<FirebaseCartItemDto> ): ArrayAdapter<FirebaseCartItemDto>(mContext, 0, listaCartItems){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.single_cart_item, parent, false)

        Log.i("firebase-consultas", "En el adaptador: ${listaCartItems}")

        val cartItem = listaCartItems[position]

        layout.tv_single_cart_nombre.text = cartItem.nombre
        layout.tv_single_cart_precio.text = "$${cartItem.precio}"
        layout.tv_single_cart_desarrolladora.text = cartItem.desarrolladora

        Glide.with(context).load(listaCartItems[position].img_url).into(layout.iv_single_cart_item)

        layout.iv_eliminar_cart
                .setOnClickListener{
                    eliminarCartItem(cartItem, position)
                }

        return layout
    }

    fun eliminarCartItem(cartItem: FirebaseCartItemDto, position: Int){
        val db = FirebaseFirestore.getInstance()
        val referenciaGroup = db.collection("carrito")
        referenciaGroup
                .whereEqualTo("nombre", cartItem.nombre)
                .get()
                .addOnSuccessListener {
                    for(item in it) {
                        item.reference
                                .delete()
                                .addOnSuccessListener {
                                    listaCartItems.removeAt(position)
                                    notifyDataSetChanged()
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