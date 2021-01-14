package com.example.examen01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.examen01.Modelo.Pelicula
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class Crear_EditarPeliculaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear__editar_pelicula)

        var idPelicula: Int? = null
        var idGeneroAct: Int = 0

        if(intent.hasExtra("pelicula")){
            val pelicula = intent.extras?.getSerializable("pelicula") as Pelicula
            findViewById<EditText>(R.id.et_nombre_pelicula).setText(pelicula.nombrePelicula)
            findViewById<EditText>(R.id.et_fecha_pelicula).setText(SimpleDateFormat("dd-MM-yyyy").format(pelicula.fechaEstreno))
            findViewById<EditText>(R.id.et_ganancias_pelicula).setText(pelicula.gananciasPelicula.toString())
            findViewById<Switch>(R.id.sw_enCartelera).isChecked = pelicula.enCartelera

            idGeneroAct = pelicula.idGenero
            idPelicula = pelicula.idPelicula
        }

        val database = AppDatabase.getDatabase(this)

        val savePelicula = findViewById<Button>(R.id.btn_save_pelicula)
        savePelicula
            .setOnClickListener {

                val nombrePelicula = findViewById<EditText>(R.id.et_nombre_pelicula).text.toString()
                val fechaEstrenoPelicula = SimpleDateFormat("dd-MM-yyyy").parse(findViewById<EditText>(R.id.et_fecha_pelicula).text.toString())!!
                val gananciasPelicula = findViewById<EditText>(R.id.et_ganancias_pelicula).text.toString().toDouble()
                val enCarteleraPelicula = findViewById<Switch>(R.id.sw_enCartelera).isChecked



                if(idPelicula != null){
                    CoroutineScope(Dispatchers.IO).launch {
                        val peliculaAct = Pelicula(nombrePelicula, fechaEstrenoPelicula, gananciasPelicula, enCarteleraPelicula, idGeneroAct)
                        peliculaAct.idPelicula = idPelicula

                        database.peliculas().updatePelicula(peliculaAct)
                        this@Crear_EditarPeliculaActivity.finish()
                    }
                } else {
                    CoroutineScope(Dispatchers.IO).launch {
                        val idGenero = intent.getIntExtra("id", 0)
                        val peliculaCrear = Pelicula(nombrePelicula, fechaEstrenoPelicula, gananciasPelicula, enCarteleraPelicula, idGenero)

                        database.peliculas().insertAllPeliculas(peliculaCrear)
                        this@Crear_EditarPeliculaActivity.finish()
                    }
                }
            }
    }
}