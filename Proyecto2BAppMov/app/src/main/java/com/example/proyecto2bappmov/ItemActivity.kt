package com.example.proyecto2bappmov

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class ItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        val tipo = intent.getStringExtra("tipo")
        Toast.makeText(this, ""+tipo, Toast.LENGTH_SHORT).show()

    }
}