package com.example.examen01

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.examen01.Modelo.Pelicula
import kotlinx.android.synthetic.main.item_pelicula.view.*
import java.text.SimpleDateFormat

class PeliculaAdapter (private val mContext: Context, private val listaPeliculas: List<Pelicula>):ArrayAdapter<Pelicula>(mContext, 0, listaPeliculas){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutPelicula = LayoutInflater.from(mContext).inflate(R.layout.item_pelicula, parent, false)

        val pelicula = listaPeliculas[position]
        layoutPelicula.tv_nombre_pelicula.text = pelicula.nombrePelicula
        layoutPelicula.tv_fecha_pelicula.text = SimpleDateFormat("dd-MM-yyyy").format(pelicula.fechaEstreno)
        layoutPelicula.tv_en_cartelera_pelicula.text = peliculaEnEstreno(pelicula.enCartelera)

        return layoutPelicula
    }

    fun peliculaEnEstreno(enCartelera:Boolean): String{
        return if(enCartelera) "En Cartelera" else "No está en cartelera"
    }
}