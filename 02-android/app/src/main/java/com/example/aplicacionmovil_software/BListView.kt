package com.example.aplicacionmovil_software

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class BListView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_list_view)

        val listView = findViewById<ListView>(R.id.lv_list_view)
        val arreglo = arrayListOf(1, 2, 3, 5)



        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,    //cómo se va a ver
            BBaseDeDatos.arregloEntrenadores //Arreglo
        )

        listView.adapter = adaptador

        val botonListView = findViewById<Button>(R.id.btn_list_view)
        botonListView
            .setOnClickListener{
                anadirItem(BBaseDeDatos.arregloEntrenadores, adaptador)
            }

        listView.adapter = adaptador

        listView
                .setOnItemLongClickListener{ parent, view, position, id ->

                    val builder = AlertDialog.Builder(this)

                    builder.setTitle("Titulo")
                    builder.setMessage("Descripcion")

                    val seleccionPrevia = booleanArrayOf(
                        true,
                        false,
                        false
                    )

                    val opciones = resources.getStringArray(
                            R.array.string_array_opciones_dialogo
                    )

                    builder.setMultiChoiceItems(
                            opciones,
                            seleccionPrevia,
                            {
                                dialog,
                                which,
                                isChecked ->
                                Log.i("intent-explicito", "Dio clic en el item")
                            }
                    )

                    builder.setPositiveButton(
                            "Aceptar",
                            DialogInterface.OnClickListener{ dialog, which ->
                                Log.i("intent-explicito", "Hola =)")
                            }
                    )

                    builder.setNegativeButton(
                            "Cancelar",
                            null
                    )

                    val dialogo = builder.create()
                    dialogo.show()

                    return@setOnItemLongClickListener true

                }
    }

    fun anadirItem(
        arreglo: ArrayList<BEntrenador>,
        adaptador: ArrayAdapter<*>
    ) {
        BBaseDeDatos.anadirItemAlArreglo(BEntrenador("Ash", "Pueblo Paleta"))
        adaptador.notifyDataSetChanged()
    }
}