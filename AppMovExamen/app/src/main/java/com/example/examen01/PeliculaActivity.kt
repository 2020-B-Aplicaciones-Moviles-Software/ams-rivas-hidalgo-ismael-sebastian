package com.example.examen01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.examen01.Modelo.Pelicula
import java.text.SimpleDateFormat
import java.util.*

class PeliculaActivity : AppCompatActivity() {
    private lateinit var database: AppDatabase
    private lateinit var pelicula: Pelicula
    private lateinit var peliculaLiveData: LiveData<Pelicula>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pelicula)

        database = AppDatabase.getDatabase(this)

        val idPelicula = intent.getIntExtra("id_pelicula", 0)
        peliculaLiveData = database.peliculas().getPelicula(idPelicula)

        peliculaLiveData.observe(this, Observer {
            pelicula = it

            findViewById<TextView>(R.id.tv_nombre_peliculaActivity).text = pelicula.nombrePelicula
            findViewById<TextView>(R.id.tv_fechaEstreno_peliculaActivity).text = formatearFecha(pelicula.fechaEstreno)
            findViewById<TextView>(R.id.tv_ganancias_peliculaActivity).text = "$${pelicula.gananciasPelicula}"
            findViewById<TextView>(R.id.tv_enCartelera_peliculaActivity).text = peliculaEnCartelera(pelicula.enCartelera)
        })
    }
    fun peliculaEnCartelera(activo:Boolean): String{
        return if(activo) "En cartelera" else "No está en cartelera"
    }

    fun formatearFecha(fechaOrigen: Date): String{
        val sdf1 = SimpleDateFormat("dd-MMM-yyyy")
        return sdf1.format(fechaOrigen)
    }
}