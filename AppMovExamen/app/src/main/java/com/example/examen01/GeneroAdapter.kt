package com.example.examen01

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.examen01.Modelo.Genero
import kotlinx.android.synthetic.main.item_genero.view.*

class GeneroAdapter(private val mContext: Context, private val listaGeneros: List<Genero> ): ArrayAdapter<Genero>(mContext, 0, listaGeneros){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_genero, parent, false)
        
        val genero = listaGeneros[position]

        layout.tv_nombre_genero.text = genero.nombreGenero
        layout.tv_ganancias_genero.text = "$${genero.gananciasGenero}"
        layout.tv_activo_genero.text = generoActivo(genero.activo)
        return layout
    }

    fun generoActivo(activo:Boolean): String{
        return if(activo) "Activo" else "Inactivo"
    }
}