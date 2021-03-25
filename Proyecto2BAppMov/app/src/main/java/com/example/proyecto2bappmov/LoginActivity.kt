package com.example.proyecto2bappmov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editEmailLogin = findViewById<EditText>(R.id.et_email_login)
        val editPaswordLogin = findViewById<EditText>(R.id.et_password_login)

        val autorizacion = FirebaseAuth.getInstance()

        val botonLogin = findViewById<Button>(R.id.btn_aceptar_login)
        botonLogin
                .setOnClickListener {
                    val email = editEmailLogin.text.toString()
                    val password = editPaswordLogin.text.toString()

                    if(email.isNotEmpty() && password.isNotEmpty()){
                        autorizacion.signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Inicio de sesión satisfactorio", Toast.LENGTH_LONG).show()
                                    val intent = Intent(this, InicioActivity::class.java)
                                    startActivity(intent)
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "${it}", Toast.LENGTH_LONG).show()
                                }
                    }else{
                        Toast.makeText(this, "Llena todos los campos por favor", Toast.LENGTH_LONG).show()
                    }

                }

    }

    fun irARegistroDesdeLogin(view: View) {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
    }
}