package com.example.aplicacionmovil_software

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result

class HHttpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h_http)
        //metodoGet()
        metodoPost()
    }

    fun metodoGet() {
        "https://jsonplaceholder.typicode.com/posts/1"  //URL del sitio al que deseo acceder
            .httpGet()
            .responseString{ req, res, result ->        //Request - Response
                when(result) {
                    is Result.Failure -> {              //Cuando falla
                        val error = result.getException()
                        Log.i("http-klaxon", "Error: ${error}")
                    }
                    is Result.Success -> {              //Cuando no falla
                        val postString = result.get()
                        Log.i("http-klaxon", "${postString}")
                    }
                }
            }
    }

    fun metodoPost() {

        val parametros: List<Pair<String, *>> = listOf(
            "title" to "Titulo moviles",
            "body" to "descripcion moviles",
            "userId" to 1
        )

        "https://jsonplaceholder.typicode.com/posts"  //URL del sitio al que deseo acceder
            .httpPost(parametros)
            .responseString{ req, res, result ->        //Request - Response
                when(result) {
                    is Result.Failure -> {              //Cuando falla
                        val error = result.getException()
                        Log.i("http-klaxon", "Error: ${error}")
                    }
                    is Result.Success -> {              //Cuando no falla
                        val postString = result.get()
                        Log.i("http-klaxon", "${postString}")
                    }
                }
            }
    }

}