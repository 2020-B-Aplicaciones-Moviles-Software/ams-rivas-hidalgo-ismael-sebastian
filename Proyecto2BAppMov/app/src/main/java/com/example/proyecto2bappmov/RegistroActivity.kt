package com.example.proyecto2bappmov

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.proyecto2bappmov.dto.FirebaseUserDto
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_cart.*

//import com.google.firebase.storage.ktx.storage

class RegistroActivity : AppCompatActivity() {

    private var uriG: Uri? = null
    private lateinit var db: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        val editNombreRegistro = findViewById<EditText>(R.id.et_nombre_registro)
        val editNombreUsuario = findViewById<EditText>(R.id.et_nom_usuario_registro)
        val editEmailRegistro = findViewById<EditText>(R.id.et_email_registro)
        val editPasswordRegistro = findViewById<EditText>(R.id.et_password_registro)
        val editEdadRegistro = findViewById<EditText>(R.id.et_edad_registro)

        val autorizacion = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()


        val botonRegistrar = findViewById<Button>(R.id.btn_aceptar_login)
        botonRegistrar
            .setOnClickListener {
                val nombre = editNombreRegistro.text.toString()
                val nombreUsuario = editNombreUsuario.text.toString()
                val email = editEmailRegistro.text.toString()
                val password = editPasswordRegistro.text.toString()
                val edad = editEdadRegistro.text.toString()

                if(nombre.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
                    autorizacion.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener {
                            if(it.isSuccessful){
                                Toast.makeText(this, "Cuenta creada satisfactoriamente", Toast.LENGTH_LONG).show()
                                val intent = Intent(this, InicioActivity::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(this, "${it.exception}", Toast.LENGTH_LONG).show()
                            }
                        }
                }else{
                    Toast.makeText(this, "Llena todos los campos por favor", Toast.LENGTH_LONG).show()
                }

                val imagenUrl = ""
                val usuario = FirebaseUserDto(nombre, nombreUsuario, email, Integer.parseInt(edad), imagenUrl)
                    db.collection("usuario")
                            .document(email)
                            .set(usuario)
                            .addOnCompleteListener {
                                Log.i("registro", "Se cargó: ${imagenUrl}")
                            }
            }
    }

    fun irALoginDesdeRegistro(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}