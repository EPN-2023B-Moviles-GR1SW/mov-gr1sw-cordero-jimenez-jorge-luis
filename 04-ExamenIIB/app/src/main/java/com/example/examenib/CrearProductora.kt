package com.example.examenib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import com.example.examenib.fireStore.ProductoraFireStore
import com.example.examenib.entidades.Productora
import java.util.Date

class CrearProductora : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_productora)

        val btnCrearNuevoProductora = findViewById<Button>(R.id.btn_crear_productora)
        btnCrearNuevoProductora.setOnClickListener {
            crearNuevaProductora()
            irActividad(ListViewProductora::class.java)
        }

        val btnCancelar = findViewById<Button>(R.id.btn_cancelar_productora)
        btnCancelar.setOnClickListener { irActividad(ListViewProductora::class.java) }
    }

    fun crearNuevaProductora() {
        val id = findViewById<EditText>(R.id.inp_idProductora)
        val nombre = findViewById<EditText>(R.id.inp_nombre)
        val fundador = findViewById<EditText>(R.id.inp_fundador)
        val cantidadComics = findViewById<EditText>(R.id.inp_cantidadComics)

        val nuevoProductora = Productora(
            id.text.toString(),
            nombre.text.toString(),
            fundador.text.toString(),
            cantidadComics.text.toString().toLong(),
            Date()
        )

        ProductoraFireStore.crearProductora(nuevoProductora)
    }


    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}