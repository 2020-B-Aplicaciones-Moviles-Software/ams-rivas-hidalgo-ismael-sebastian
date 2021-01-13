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
import com.example.examen01.Modelo.Pelicula
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainPelicula : AppCompatActivity() {

    var idItemSeleccionado = 0
    private lateinit var database: AppDatabase
    private lateinit var peliculaSelected: Pelicula
    var listaVacia = emptyList<Pelicula>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_pelicula)

        var listaPeliculas = emptyList<Pelicula>()

        database = AppDatabase.getDatabase(this)
        val genero = intent.extras?.getSerializable("genero") as Genero

        this.setTitle(genero.nombreGenero)

        Toast.makeText(this, "Nombre del género seleccionado: ${genero.nombreGenero}", Toast.LENGTH_SHORT).show()

        val peliculasDelGenero = database.peliculas().getAllPeliculasByGenero(genero.idGenero)

        peliculasDelGenero.observe(this, Observer {
            listaPeliculas = it
            val adapter = PeliculaAdapter(this, listaPeliculas)
            val lvListaPeliculas = findViewById<ListView>(R.id.lv_lista_peliculas)
            lvListaPeliculas.adapter = adapter

            listaVacia = listaPeliculas
        })

        val lvListaPeliculas = findViewById<ListView>(R.id.lv_lista_peliculas)
        lvListaPeliculas
            .setOnItemClickListener { parent, view, position, id ->
                val intent = Intent(this, PeliculaActivity::class.java)
                intent.putExtra("id_pelicula", listaPeliculas[position].idPelicula)
                startActivity(intent)
        }

        val botonCrearPelicula = findViewById<FloatingActionButton>(R.id.ft_nueva_pelicula)
        botonCrearPelicula
            .setOnClickListener {
                val intent = Intent(this, Crear_EditarPeliculaActivity::class.java)
                intent.putExtra("id", genero.idGenero)
                startActivity(intent)
            }

        registerForContextMenu(lvListaPeliculas)
    }

    override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        menuInflater.inflate(R.menu.menu_pelicula, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
        peliculaSelected = listaVacia[id]
        Toast.makeText(this, "Item seleccionado: ${id}", Toast.LENGTH_SHORT).show()
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {

        when(item.itemId){

            R.id.op_actualizar_pelicula -> {
                Toast.makeText(this, "Opción actualizar seleccionada", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, Crear_EditarPeliculaActivity::class.java)
                intent.putExtra("pelicula", peliculaSelected)
                startActivity(intent)
            }
            R.id.op_eliminar_pelicula-> {
                Toast.makeText(this, "Opción eliminar seleccionada", Toast.LENGTH_SHORT).show()
                CoroutineScope(Dispatchers.IO).launch {
                    database.peliculas().delete(peliculaSelected)
                }
            }
        }
        return super.onContextItemSelected(item)
    }
}