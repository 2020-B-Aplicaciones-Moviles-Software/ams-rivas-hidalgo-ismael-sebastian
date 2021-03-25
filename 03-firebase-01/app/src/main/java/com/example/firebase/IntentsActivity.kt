package com.example.firebase

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.ByteArrayOutputStream

class IntentsActivity : AppCompatActivity() {

    private var uriG: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intents)

        val botonSeleccionar = findViewById<Button>(R.id.btn_seleccionar_img)
        botonSeleccionar
                .setOnClickListener {
                    abrirDocumentos()
                }

        val botonCapturar = findViewById<Button>(R.id.btn_capturar_img)
        botonCapturar
                .setOnClickListener {
                    abrirCamara()
                }

        val botonSubir = findViewById<Button>(R.id.btn_subir_bitmap_img)
        botonSubir
                .setOnClickListener {
                    subirImagenBitmap()
                }

        val botonSubirUri = findViewById<Button>(R.id.btn_subir_uri_img)
        botonSubirUri
                .setOnClickListener {
                    subirImagenUri()
                }

        val botonDescargar = findViewById<Button>(R.id.btn_descargar_img)
        botonDescargar
                .setOnClickListener {
                    descargarImagen()
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            304 -> {
                if(resultCode == Activity.RESULT_OK && data != null){
                    val uri = data?.data
                    uriG = uri
                    val imageView = findViewById<ImageView>(R.id.iv_imagen)
                    imageView.setImageURI(uri)

                    val storage = Firebase.storage
                    // Create a storage reference from our app
                    val storageRef = storage.reference



                }else{
                    Log.i("resultado", "Usuario no seleccionó una imagen")
                }
            }
            305 -> {
                if(resultCode == Activity.RESULT_OK && data != null){
                    var graph = data.extras
                    val bitmap = graph?.getParcelable<Bitmap>("data")
                    val imageView = findViewById<ImageView>(R.id.iv_imagen)
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

    fun abrirCamara(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 305)
    }

    fun subirImagenUri(){

        val storage = Firebase.storage
        // Create a storage reference from our app
        val storageRef = storage.reference

        val riversRef = storageRef.child("images/foto1111.jpg")
        val uploadTask = riversRef.putFile(uriG!!)

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
            Log.i("resultado", "Fallo")
        }.addOnSuccessListener { taskSnapshot ->
            Log.i("resultado", "Cargado")
        }
    }

    fun subirImagenBitmap(){

        val storage = Firebase.storage
        // Create a storage reference from our app
        val storageRef = storage.reference
        // Create a reference to "mountains.jpg"
        val mountainsRef = storageRef.child("foto.jpg")

        val imageView = findViewById<ImageView>(R.id.iv_imagen)

        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            Log.i("resultado", "Fallo")
        }.addOnSuccessListener { taskSnapshot ->
            Log.i("resultado", "Cargado")
        }
    }

    fun descargarImagen(){


        val storage = Firebase.storage
        // Create a storage reference from our app
        val storageRef = storage.reference
        val pathEditText = findViewById<EditText>(R.id.et_path)

        var islandRef = storageRef.child(pathEditText.text.toString())

        val ONE_MEGABYTE: Long = 1024 * 1024
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener {

            val imageView = findViewById<ImageView>(R.id.iv_imagen)

            val bmp = BitmapFactory.decodeByteArray(it, 0, it.size)

            imageView.setImageBitmap(Bitmap.createScaledBitmap(bmp, imageView.width, imageView.height, false))

        }.addOnFailureListener {
            // Handle any errors
        }
    }

}