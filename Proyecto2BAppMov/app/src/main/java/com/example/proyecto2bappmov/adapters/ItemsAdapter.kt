package com.example.proyecto2bappmov.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto2bappmov.DetallesActivity
import com.example.proyecto2bappmov.R
import com.example.proyecto2bappmov.dto.FirebaseItemsDto

class ItemsAdapter(val context: Context?, val listaItems: ArrayList<FirebaseItemsDto>):
    RecyclerView.Adapter<ItemsAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemsImageView = itemView.findViewById<ImageView>(R.id.iv_single_item_img)
        val itemsNombre = itemView.findViewById<TextView>(R.id.tv_single_item_nombre)
        val itemsPrecio = itemView.findViewById<TextView>(R.id.tv_single_item_precio)
        val itemsReviewBar = itemView.findViewById<RatingBar>(R.id.rb_review_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.single_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listaItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemsPrecio.text = "$${listaItems.get(position).precio}"
        holder.itemsNombre.text = listaItems.get(position).nombre
        Glide.with(context!!).load(listaItems.get(position).img_url).into(holder.itemsImageView)
        holder.itemsReviewBar.rating = listaItems.get(position).review.toFloat()

        holder.itemsImageView
                .setOnClickListener {
                    val intent = Intent(context, DetallesActivity::class.java)
                    intent.putExtra("detalle", listaItems.get(position))
                    context.startActivity(intent)
                }
    }
}