package com.example.proyecto2bappmov.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto2bappmov.DetallesActivity
import com.example.proyecto2bappmov.R
import com.example.proyecto2bappmov.dto.FirebaseDestacadoDto

class DestacadoAdapter (val context: Context?, val listaDestacados: ArrayList<FirebaseDestacadoDto>):
        RecyclerView.Adapter<DestacadoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val destacadoImage = itemView.findViewById<ImageView>(R.id.iv_destacado)
        val destacadoNombre = itemView.findViewById<TextView>(R.id.tv_nombre_destacado)
        val destacadoPrecio = itemView.findViewById<TextView>(R.id.tv_precio_destacado)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.single_destacado_item, parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaDestacados.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.destacadoPrecio.text = "$${listaDestacados.get(position).precio}"
        holder.destacadoNombre.text = listaDestacados.get(position).nombre
        Glide.with(context!!).load(listaDestacados.get(position).img_url).into(holder.destacadoImage)

        holder.destacadoImage
                .setOnClickListener {
                    val intent = Intent(context, DetallesActivity::class.java)
                    intent.putExtra("detalle", listaDestacados.get(position))
                    context.startActivity(intent)
                }
    }
}
