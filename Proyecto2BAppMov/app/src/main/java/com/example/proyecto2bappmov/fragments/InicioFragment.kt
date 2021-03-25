package com.example.proyecto2bappmov.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.example.proyecto2bappmov.CartActivity
import com.example.proyecto2bappmov.ListaCategoriasActivity
import com.example.proyecto2bappmov.R
import com.example.proyecto2bappmov.VariosItemsActivity
import com.example.proyecto2bappmov.adapters.CategoriaAdapter
import com.example.proyecto2bappmov.adapters.DestacadoAdapter
import com.example.proyecto2bappmov.dto.FirebaseCategoriaDto
import com.example.proyecto2bappmov.dto.FirebaseDestacadoDto
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InicioFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InicioFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var mStore: FirebaseFirestore? = null
    //Seccion Categoria
    private var listaCategorias: ArrayList<FirebaseCategoriaDto>? = null
    private lateinit var categoriaAdapter: CategoriaAdapter
    private lateinit var recyclerViewCategoria :RecyclerView
    //Seccion Destacados
    private var listaDestacados: ArrayList<FirebaseDestacadoDto>? = null
    private lateinit var destacadoAdapter: DestacadoAdapter
    private lateinit var recyclerViewDestacado :RecyclerView
    private lateinit var tv_ver_todas_cat: TextView
    private lateinit var tv_ver_todos_item: TextView


    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)
        mStore = FirebaseFirestore.getInstance()
        tv_ver_todas_cat = view.findViewById(R.id.ver_todas_cat)
        tv_ver_todos_item = view.findViewById(R.id.ver_todos_items)
        recyclerViewCategoria = view.findViewById(R.id.categoria_recycler)
        recyclerViewDestacado = view.findViewById(R.id.destacado_recycler)

        //Slider
        val imageSlider = view.findViewById<ImageSlider>(R.id.slider)

        val listaImageSlider = ArrayList<SlideModel>()
        listaImageSlider.add(SlideModel("https://regionps.com/wp-content/uploads/2018/08/hollow-knight-key-art.jpg"))
        listaImageSlider.add(SlideModel("https://gametrex.com/wp-content/uploads/2019/02/Hyper-Light-Drifter-Free-Download.jpg"))
        listaImageSlider.add(SlideModel("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/1d7bd508-5de2-45bb-9feb-954f14406ed9/d5mphkh-0bad1aa8-ba5e-48d8-8686-639579606b8a.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOiIsImlzcyI6InVybjphcHA6Iiwib2JqIjpbW3sicGF0aCI6IlwvZlwvMWQ3YmQ1MDgtNWRlMi00NWJiLTlmZWItOTU0ZjE0NDA2ZWQ5XC9kNW1waGtoLTBiYWQxYWE4LWJhNWUtNDhkOC04Njg2LTYzOTU3OTYwNmI4YS5wbmcifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6ZmlsZS5kb3dubG9hZCJdfQ.9iMO0LZfFq6fFKhlsgA3DYi3taDcm1JZnNwAtJLPeis"))
        listaImageSlider.add(SlideModel("https://www.mcvuk.com/wp-content/uploads/KATANA-ZERO-Assets-Switch-Banner-2-to-1-1.jpg"))
        imageSlider.setImageList(listaImageSlider, true)

        //Categorias
        listaCategorias = ArrayList<FirebaseCategoriaDto>()
        categoriaAdapter = CategoriaAdapter(this.context,  listaCategorias!!)
        recyclerViewCategoria.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        recyclerViewCategoria.adapter = categoriaAdapter

        //Destacados
        listaDestacados = ArrayList<FirebaseDestacadoDto>()
        destacadoAdapter = DestacadoAdapter(this.context,  listaDestacados!!)
        recyclerViewDestacado.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL, false)
        recyclerViewDestacado.adapter = destacadoAdapter

        mStore!!.collection("categoria")
                .get()
                .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            Log.d("firestore", document.id + " => " + document.data)
                            val categoria: FirebaseCategoriaDto = document.toObject(FirebaseCategoriaDto::class.java)
                            listaCategorias?.add(categoria)
                            categoriaAdapter.notifyDataSetChanged()
                        }
                    } else {
                        Log.d("firestore", "Error getting documents.", task.exception)
                    }
                })

        mStore!!.collection("destacado")
                .get()
                .addOnCompleteListener( { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            val mapa = document.getData()
                            val destacado1 = FirebaseDestacadoDto(mapa.get("descripcion").toString(), mapa.get("img_url").toString(), mapa.get("nombre").toString(),
                                                                    mapa.get("precio").toString().toDouble(), Integer.parseInt(mapa.get("review").toString()), mapa.get("desarrolladora").toString(), mapa.get("distribuidora").toString())
                            //val destacado: FirebaseDestacadoDto = document.toObject(FirebaseDestacadoDto::class.java)
                            listaDestacados?.add(destacado1)
                            destacadoAdapter.notifyDataSetChanged()
                        }
                    } else {
                        Log.d("firestore", "Error getting documents.", task.exception)
                    }
                })

        tv_ver_todas_cat
                .setOnClickListener {
                    val intent = Intent(context, ListaCategoriasActivity::class.java)
                    startActivity(intent)
                }

        tv_ver_todos_item
                .setOnClickListener {
                    val intent = Intent(context, VariosItemsActivity::class.java)
                    startActivity(intent)
                }

        val botonIrCarrito = view.findViewById<Button>(R.id.btn_ver_carrito)
        botonIrCarrito
            .setOnClickListener {
                val intent = Intent(context, CartActivity::class.java)
                startActivity(intent)
            }


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InicioFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                InicioFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}