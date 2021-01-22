package com.example.firebase

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.IdRes
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    val CODIGO_INGRESO = 102
    val mensajeNoLogueado = "Dale clic al boton ingresar"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonIngresar = findViewById<Button>(R.id.btn_ingresar)
        botonIngresar
            .setOnClickListener{
                pedidoIngresar()
            }
        val botonSalir = findViewById<Button>(R.id.btn_salir)
        botonSalir
            .setOnClickListener{
                pedidoSalir()
            }

        val texto = findViewById<TextView>(R.id.textView)
        val instanciaAuth = FirebaseAuth.getInstance()
        if(instanciaAuth.currentUser != null){
            texto.text = "Bienvenido ${instanciaAuth.currentUser?.email}"
            seterUsuarioFirebase()
        } else {
            texto.text = "Dale clic al boton ingresar"
        }
    }

    fun pedidoIngresar(){
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.jp)
                .setTosAndPrivacyPolicyUrls(
                    "https://example.com/terms.html",
                    "https://example.com/privacy.html"
                )
                .build(),
            CODIGO_INGRESO)
    }

    fun pedidoSalir(){
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                Log.i("firebase-firestore", "Salio")
                val texto = findViewById<TextView>(R.id.textView)
                texto.text = mensajeNoLogueado
            }
            .addOnCompleteListener {
                Log.i("firebase-firestore", "Hubo problemas al salir")
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            CODIGO_INGRESO -> {
                if(resultCode == Activity.RESULT_OK){
                    val usuario = IdpResponse.fromResultIntent(data)
                    if(usuario?.isNewUser == true){
                        //logica
                    }
                    val texto = findViewById<TextView>(R.id.textView)
                    texto.text = "Bienvenido ${usuario?.email}"
                    seterUsuarioFirebase()
                }else{
                    Log.i("firebase-login", "El usuario cancelo")
                }
            }
        }
    }

    fun seterUsuarioFirebase(){
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser

        if(usuarioLocal != null) {
            if(usuarioLocal.email != null){
                val usuarioFirebase = BUsuarioFirebase(
                        usuarioLocal.uid,
                        usuarioLocal.email!!
                )
                BAuthUsuario.usuario = usuarioFirebase
            }
        }
    }
}