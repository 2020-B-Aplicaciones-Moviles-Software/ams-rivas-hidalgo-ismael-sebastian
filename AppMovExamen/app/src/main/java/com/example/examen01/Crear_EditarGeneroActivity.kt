package com.example.examen01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.examen01.Modelo.Genero
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class Crear_EditarGeneroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_editar_genero)

        var idGenero: Int? = null

        if(intent.hasExtra("genero")){
            val genero = intent.extras?.getSerializable("genero") as Genero

            findViewById<EditText>(R.id.et_nombre_genero).setText(genero.nombreGenero)
            findViewById<EditText>(R.id.et_fecha).setText(SimpleDateFormat("dd-MM-yyyy").format(genero.fechaOrigen))
            findViewById<EditText>(R.id.et_numSubgeneros).setText(genero.numSubgeneros.toString())
            findViewById<EditText>(R.id.et_ganancias_genero).setText(genero.gananciasGenero.toString())
            findViewById<Switch>(R.id.sw_activo).isChecked = genero.activo

            idGenero = genero.idGenero
        }

        val database = AppDatabase.getDatabase(this)

        val btn_save = findViewById<Button>(R.id.btn_save_genero)
        btn_save.setOnClickListener {
            val nombreGenero = findViewById<EditText>(R.id.et_nombre_genero).text.toString()
            val fechaOrigenGenero = SimpleDateFormat("dd-MM-yyyy").parse(findViewById<EditText>(R.id.et_fecha).text.toString())
            val numeroSubgeneros= findViewById<EditText>(R.id.et_numSubgeneros).text.toString().toInt()
            val gananciasGenero= findViewById<EditText>(R.id.et_ganancias_genero).text.toString().toDouble()
            val activoGenero= findViewById<Switch>(R.id.sw_activo).isChecked

            val genero = Genero(nombreGenero, fechaOrigenGenero, numeroSubgeneros, gananciasGenero, activoGenero)

            if(idGenero != null){
                CoroutineScope(Dispatchers.IO).launch {
                    genero.idGenero = idGenero

                    database.generos().updateGenero(genero)

                    this@Crear_EditarGeneroActivity.finish()
                }
            }else{
                CoroutineScope(Dispatchers.IO).launch {
                    database.generos().insertAllGeneros(genero)

                    this@Crear_EditarGeneroActivity.finish()
                }
            }
        }
    }
}