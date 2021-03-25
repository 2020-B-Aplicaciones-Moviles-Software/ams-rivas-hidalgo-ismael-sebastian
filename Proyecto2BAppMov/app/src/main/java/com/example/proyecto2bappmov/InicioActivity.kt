package com.example.proyecto2bappmov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proyecto2bappmov.adapters.ItemsAdapter
import com.example.proyecto2bappmov.dto.FirebaseItemsDto
import com.example.proyecto2bappmov.fragments.InicioFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_inicio.*
import java.util.*
import kotlin.collections.ArrayList

class InicioActivity : AppCompatActivity() {

    lateinit var inicioFragment: Fragment
    lateinit var bd: FirebaseFirestore
    var listaBuscarItems: ArrayList<FirebaseItemsDto>? = null
    private var itemsRecyclerView: RecyclerView? = null
    private var itemsRecyclerAdapter: ItemsAdapter? = null
    val user= FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        inicioFragment = InicioFragment()
        cargarFragment(inicioFragment)

        bd = FirebaseFirestore.getInstance()
        cargarFotoPerfil()

        listaBuscarItems = ArrayList()
        itemsRecyclerView = findViewById(R.id.buscar_recycler)
        itemsRecyclerView?.layoutManager = GridLayoutManager(this, 2)
        itemsRecyclerAdapter = ItemsAdapter(this, listaBuscarItems!!)
        itemsRecyclerView?.adapter = itemsRecyclerAdapter

        val buscarString = findViewById<EditText>(R.id.et_buscar_inicio)
        buscarString.doAfterTextChanged {
            if(it.toString().isEmpty()){
                listaBuscarItems?.clear()
                itemsRecyclerAdapter?.notifyDataSetChanged()
            }else{
                buscarItem(it.toString())
            }
        }

        val imagen_perfil = findViewById<ImageView>(R.id.iv_profile_inicio)
        imagen_perfil
            .setOnClickListener {
                val intent = Intent(this, PerfilActivity::class.java)
                startActivity(intent)
            }

    }

    private fun buscarItem(texto: String) {
        bd.collection("item")
                //.whereGreaterThanOrEqualTo("nombre", texto)
                .get()
                .addOnSuccessListener {
                    if(it != null){
                        listaBuscarItems?.clear()
                        for (doc: DocumentSnapshot in it.documents){
                            Log.i("busqueda", "funca ${doc.data}")
                            val mapa = doc.data!!
                            val item = FirebaseItemsDto(mapa.get("descripcion").toString(), mapa.get("img_url").toString(), mapa.get("nombre").toString(),
                                    mapa.get("precio").toString().toDouble(), Integer.parseInt(mapa.get("review").toString()), mapa.get("desarrolladora").toString(), mapa.get("distribuidora").toString(), mapa.get("categoria").toString())
                            if(item.nombre.startsWith(texto)){
                                listaBuscarItems?.add(item)
                                itemsRecyclerAdapter?.notifyDataSetChanged()
                            }
                        }
                    }
                }
    }

    private fun cargarFragment(inicioFragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.inicio_container, inicioFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun cargarFotoPerfil(){

        val mStore = FirebaseFirestore.getInstance()

        mStore!!.collection("usuario")
            .whereEqualTo("email", user?.email)
                .get()
                .addOnCompleteListener( { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            val mapa = document.data!!
                            val perfil_url = mapa.get("perfil_url")

                            if(perfil_url != "") {
                               Glide.with(this).load(perfil_url).into(iv_profile_inicio)
                            }
                        }

                    } else {
                        Log.d("firestore", "Error getting documents.", task.exception)
                    }
                })
    }
}