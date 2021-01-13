package com.example.examen01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.examen01.Modelo.Genero
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var idItemSeleccionado = 0
    private lateinit var generoSelected: Genero
    var listaVacia = emptyList<Genero>()
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var listaGeneros = emptyList<Genero>()

        database = AppDatabase.getDatabase(this)

        database.generos().getAllGeneros().observe(this, Observer {
            listaGeneros = it
            val adapter = GeneroAdapter(this, listaGeneros)

            val lista = findViewById<ListView>(R.id.lv_lista_generos)
            lista.adapter = adapter

            listaVacia = listaGeneros
        })

        val lista = findViewById<ListView>(R.id.lv_lista_generos)
        lista
            .setOnItemClickListener { parent, view, position, id ->
                val intent = Intent(this, GeneroActivity::class.java)
                intent.putExtra("id", listaGeneros[position].idGenero)

                startActivity(intent)
        }

        val botonCrearGenero = findViewById<FloatingActionButton>(R.id.fbtn_crear_genero)
        botonCrearGenero
            .setOnClickListener {
                val intent = Intent(this, Crear_EditarGeneroActivity::class.java)
                startActivity(intent)
            }

        registerForContextMenu(lista)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        menuInflater.inflate(R.menu.menu_genero, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
        generoSelected = listaVacia[id]
        Toast.makeText(this, "Item seleccionado: ${id}", Toast.LENGTH_SHORT).show()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.op_ver_peliculas -> {
                Toast.makeText(this, "Opción ver películas seleccionada", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainPelicula::class.java)
                intent.putExtra("genero", generoSelected)
                startActivity(intent)
            }
            R.id.op_actualizar_genero -> {
                Toast.makeText(this, "Opción actualizar seleccionada", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Crear_EditarGeneroActivity::class.java)
                intent.putExtra("genero", generoSelected)
                startActivity(intent)
            }
            R.id.op_eliminar_genero-> {
                Toast.makeText(this, "Opción eliminar seleccionada", Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.IO).launch {
                    database.generos().delete(generoSelected)
                }
            }
        }
        return super.onContextItemSelected(item)
    }
}