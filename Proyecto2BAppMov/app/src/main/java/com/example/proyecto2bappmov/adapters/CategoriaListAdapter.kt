package com.example.proyecto2bappmov.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.proyecto2bappmov.R
import com.example.proyecto2bappmov.VariosItemsActivity
import com.example.proyecto2bappmov.dto.FirebaseCategoriaDto
import kotlinx.android.synthetic.main.categoria_item_list.view.*

class CategoriaListAdapter (private val mContext: Context, private val listaCategoria: List<FirebaseCategoriaDto> ): ArrayAdapter<FirebaseCategoriaDto>(mContext, 0, listaCategoria){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.categoria_item_list, parent, false)

        Log.i("firebase-consultas", "En el adaptador: ${listaCategoria}")

        val categoria = listaCategoria[position]

        layout.tv_cat_item_list.text = categoria.tipo
        Glide.with(context).load(listaCategoria[position].img_url).into(layout.iv_cat_item_list)
        layout.ly_single_category
                .setOnClickListener {
                    val intent = Intent(this.context, VariosItemsActivity::class.java)
                    intent.putExtra("tipo", listaCategoria.get(position).tipo)
                    context.startActivity(intent)
                }

        return layout
    }
}