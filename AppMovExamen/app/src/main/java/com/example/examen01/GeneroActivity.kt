package com.example.examen01

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.example.examen01.Modelo.Genero
import java.text.SimpleDateFormat
import java.util.*

class GeneroActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    private lateinit var genero: Genero
    private lateinit var generoLiveData: LiveData<Genero>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genero)

        database = AppDatabase.getDatabase(this)

        val idGenero = intent.getIntExtra("id", 0)

        generoLiveData = database.generos().getGenero(idGenero)

        generoLiveData.observe(this, androidx.lifecycle.Observer {
            genero = it
            findViewById<TextView>(R.id.tv_nombre_generoActivity).text = genero.nombreGenero
            findViewById<TextView>(R.id.tv_fecha_genero).text = formatearFecha(genero.fechaOrigen)
            findViewById<TextView>(R.id.tv_subgeneros_genero).text = genero.numSubgeneros.toString()
            findViewById<TextView>(R.id.tv_ganancias_generoActivity).text = "$${genero.gananciasGenero}"
            findViewById<TextView>(R.id.tv_activo_generoActivity).text = generoActivo(genero.activo)
        })
    }

    fun generoActivo(activo:Boolean): String{
        return if(activo) "Activo" else "Inactivo"
    }

    fun formatearFecha(fechaOrigen: Date): String{
        val sdf1 = SimpleDateFormat("yyyy-MMM-dd")
        return sdf1.format(fechaOrigen)
    }
}