package com.example.proyecto2bappmov.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto2bappmov.ItemActivity
import com.example.proyecto2bappmov.R
import com.example.proyecto2bappmov.VariosItemsActivity
import com.example.proyecto2bappmov.dto.FirebaseCategoriaDto

class CategoriaAdapter(val context: Context?, val listaCategorias: ArrayList<FirebaseCategoriaDto>):
    RecyclerView.Adapter<CategoriaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val typeImage :ImageView? = itemView.findViewById(R.id.iv_categoria)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_categoria_item, parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaCategorias!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context!!).load(listaCategorias.get(position).img_url).into(holder.typeImage!!)
        holder.typeImage.setOnClickListener(View.OnClickListener {
            val intent = Intent(this.context, VariosItemsActivity::class.java)
            intent.putExtra("tipo", listaCategorias.get(position).tipo)
            context.startActivity(intent)
        })
    }
}