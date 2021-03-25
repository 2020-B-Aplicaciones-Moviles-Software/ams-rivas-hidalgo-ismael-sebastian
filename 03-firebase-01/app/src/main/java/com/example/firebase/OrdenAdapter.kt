package com.example.firebase

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.firebase.dto.FirestoreOrdenesDto
import kotlinx.android.synthetic.main.item_orden.view.*

class OrdenAdapter (private val mContext: Context, private val listaOrdenes: List<FirestoreOrdenesDto> ): ArrayAdapter<FirestoreOrdenesDto>(mContext, 0, listaOrdenes){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_orden, parent, false)

        Log.i("firebase-consultas", "En el adaptador: ${listaOrdenes}")

        val orden = listaOrdenes[position]

        layout.tv_restaurante.text = "${orden.restaurante}"
        layout.tv_review.text = "${orden.review}"
        layout.tv_tipos_comida_item.text = "${orden.tiposComida}"
        return layout
    }
}