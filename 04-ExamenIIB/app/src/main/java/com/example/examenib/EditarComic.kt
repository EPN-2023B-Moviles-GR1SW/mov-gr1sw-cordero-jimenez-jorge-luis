package com.example.examenib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import com.example.examenib.fireStore.ComicFireStore
import com.example.examenib.entidades.Comic
import java.util.Date

class EditarComic : AppCompatActivity() {

    var radioTrue: RadioButton? = null
    var radioFalse: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_comic)

        val id = findViewById<EditText>(R.id.inp_idEditarComic)
        val titulo = findViewById<EditText>(R.id.inp_editarTitulo)
        val precioComic = findViewById<EditText>(R.id.inp_editarPrecio)

        val radioTrue = findViewById<RadioButton>(R.id.rdb_recom_true_editar)
        val radioFalse = findViewById<RadioButton>(R.id.rdb_recom_false_editar)
        var recomendado: Boolean = false

        radioTrue.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                recomendado = true
            }
        }

        radioFalse.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                recomendado = false
            }
        }

        val idComic = intent.getStringExtra("id")
        ComicFireStore.consultarComic(idComic!!){
            id.setText(it.id)
            titulo.setText(it.tituloComic)
            precioComic.setText(it.precioComic.toString())
            recomendado = it.recomendado
        }

        val btnEditarComic = findViewById<Button>(R.id.btn_editarComic)
        btnEditarComic.setOnClickListener {
            editarComic()
            irActividad(ListViewComics::class.java)
        }
    }

    private fun editarComic() {
        val id = findViewById<EditText>(R.id.inp_idEditarComic)
        val titulo = findViewById<EditText>(R.id.inp_editarTitulo)
        val precioComic = findViewById<EditText>(R.id.inp_editarPrecio)
        val recomendado: Boolean = radioTrue?.isChecked == true

        val comicEditada = Comic(
            id.text.toString(),
            titulo.text.toString(),
            precioComic.text.toString().toLong(),
            recomendado,
            Date()
        )

        ComicFireStore.actualizarComics(comicEditada)

    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}