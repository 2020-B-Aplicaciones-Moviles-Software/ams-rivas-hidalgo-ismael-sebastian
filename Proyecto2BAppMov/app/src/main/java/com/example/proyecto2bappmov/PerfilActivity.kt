package com.example.proyecto2bappmov

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.proyecto2bappmov.dto.FirebaseUserDto
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask

class PerfilActivity : AppCompatActivity() {

    private var mStore: FirebaseFirestore? = null
    lateinit var usuario: FirebaseUserDto
    private var uriG: Uri? = null
    val rand = (0..100).random()
    val user= FirebaseAuth.getInstance().currentUser
    var correo = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        //Toast.makeText(this, "${user?.email}", Toast.LENGTH_SHORT).show()

        val tv_nombre_usuario_perfil = findViewById<TextView>(R.id.tv_nombre_usuario_perfil)
        val tv_nombre_perfil = findViewById<TextView>(R.id.tv_nombre_perfil)
        val tv_correo_perfil = findViewById<TextView>(R.id.tv_correo_perfil)
        val iv_cargarImagen = findViewById<ImageView>(R.id.iv_imagen_perfil)


        iv_cargarImagen
            .setOnClickListener{
                abrirDocumentos()
            }

        mStore = FirebaseFirestore.getInstance()

        mStore!!.collection("usuario")
            .whereEqualTo("email", user?.email)
                .get()
                .addOnCompleteListener( { task ->
                    if (task.isSuccessful) {

                        for (document in task.result!!) {
                            val mapa = document.data!!
                            usuario = FirebaseUserDto(mapa.get("nombre_usr").toString(), mapa.get("nombre_usuario_usr").toString(), mapa.get("email").toString(),
                                    Integer.parseInt(mapa.get("edad").toString()), mapa.get("perfil_url").toString())

                            tv_nombre_usuario_perfil.text = usuario.nombre_usuario_usr
                            tv_nombre_perfil.text = usuario.nombre_usr
                            tv_correo_perfil.text = usuario.email
                            correo = usuario.email

                            val iv_cargar_imagen = findViewById<ImageView>(R.id.iv_imagen_perfil)

                            if(usuario.perfil_url != "") {

                                Glide.with(this).load(usuario.perfil_url).into(iv_cargar_imagen)
                            }
                        }

                    } else {
                        Log.d("firestore", "Error getting documents.", task.exception)
                    }
                })




        val botonGuardarImagen = findViewById<Button>(R.id.btn_guardar_img_perfil)
        botonGuardarImagen
            .setOnClickListener {
                subirImagenDePerfil()
            }


        val botonCerrarSesion = findViewById<Button>(R.id.btn_cerrar_sesion_perfil)
        botonCerrarSesion
            .setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        val botonAceptar = findViewById<Button>(R.id.btn_aceptar_perfil)
        botonAceptar
            .setOnClickListener {
                val intent = Intent(this, InicioActivity::class.java)
                startActivity(intent)
            }

        val iv_agregar_dir = findViewById<ImageView>(R.id.iv_ingresar_direccion)
        iv_agregar_dir
                .setOnClickListener {
                    val intent = Intent(this, DireccionActivity::class.java)
                    startActivity(intent)
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            304 -> {
                if(resultCode == Activity.RESULT_OK && data != null){
                    val uri = data?.data
                    uriG = uri
                    val imageView = findViewById<ImageView>(R.id.iv_imagen_perfil)
                    imageView.setImageURI(uri)
                }else{
                    Log.i("resultado", "Usuario no seleccionó una imagen")
                }
            }
            305 -> {
                if(resultCode == Activity.RESULT_OK && data != null){
                    var graph = data.extras
                    val bitmap = graph?.getParcelable<Bitmap>("data")
                    val imageView = findViewById<ImageView>(R.id.iv_imagen_perfil)
                    imageView.setImageBitmap(bitmap)

                }else{
                    Log.i("resultado", "Usuario no ha tomado ninguna foto")
                }
            }
        }
    }

    fun abrirDocumentos(){

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, 304)

    }

    fun subirImagenDePerfil(): String {
        val storage = FirebaseStorage.getInstance()
        // Create a storage reference from our app
        val storageRef = storage.reference
        var url_resultado = ""
        val riversRef = storageRef.child("perfiles/foto${rand}.jpg")
        var uploadTask: StorageTask<*>
        uploadTask = riversRef.putFile(uriG!!)
        uploadTask.continueWithTask(Continuation <UploadTask.TaskSnapshot, Task<Uri>>{ task ->
            if(!task.isSuccessful){
                task.exception?.let {
                    throw it
                }
            }
            return@Continuation riversRef.downloadUrl
        }).addOnCompleteListener { task ->
            if(task.isSuccessful){
                val downloadUri = task.result
                url_resultado = downloadUri.toString()
                val db = FirebaseFirestore.getInstance()
                val ref = db.collection("usuario").document(correo)

                ref
                    .update("perfil_url", url_resultado)
                    .addOnSuccessListener { Log.i("update-image", "DocumentSnapshot successfully updated!") }
                    .addOnFailureListener { e -> Log.i("update-image", "Error updating document", e) }
            }
        }
        return url_resultado
    }
}