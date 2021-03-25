package com.example.proyecto2bappmov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto2bappmov.adapters.ItemsAdapter
import com.example.proyecto2bappmov.dto.FirebaseItemsDto
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class VariosItemsActivity : AppCompatActivity() {

    private lateinit var mStore: FirebaseFirestore
    private lateinit var variosItemsRecycler: RecyclerView
    var listaItems: ArrayList<FirebaseItemsDto>? = null
    private lateinit var itemsRecyclerAdapter: ItemsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_varios_items)

        val tipo = intent.getStringExtra("tipo")
        mStore = FirebaseFirestore.getInstance()
        listaItems = ArrayList()
        variosItemsRecycler = findViewById(R.id.rv_varios_items)
        variosItemsRecycler.layoutManager = GridLayoutManager(this, 2)
        itemsRecyclerAdapter = ItemsAdapter(this, listaItems!!)
        variosItemsRecycler.adapter = itemsRecyclerAdapter
        if(tipo == null || tipo.isEmpty()){
            mStore.collection("item")
                .get()
                .addOnSuccessListener {
                    for (doc: DocumentSnapshot in it.documents){
                        val mapa = doc.getData()!!
                        val item = FirebaseItemsDto(mapa.get("descripcion").toString(), mapa.get("img_url").toString(), mapa.get("nombre").toString(),
                            mapa.get("precio").toString().toDouble(), Integer.parseInt(mapa.get("review").toString()), mapa.get("desarrolladora").toString(), mapa.get("distribuidora").toString(), mapa.get("categoria").toString())
                        listaItems?.add(item)
                        itemsRecyclerAdapter.notifyDataSetChanged()
                    }
                }
        }
        if(tipo != null){
            mStore.collection("item")
                .whereEqualTo("categoria", tipo.toString())
                .get()
                .addOnSuccessListener {
                    for (doc: DocumentSnapshot in it.documents){
                        val mapa = doc.getData()!!
                        val item = FirebaseItemsDto(mapa.get("descripcion").toString(), mapa.get("img_url").toString(), mapa.get("nombre").toString(),
                            mapa.get("precio").toString().toDouble(), Integer.parseInt(mapa.get("review").toString()), mapa.get("desarrolladora").toString(), mapa.get("distribuidora").toString(), mapa.get("categoria").toString())
                        listaItems?.add(item)
                        itemsRecyclerAdapter.notifyDataSetChanged()
                    }
                }
        }
    }
}