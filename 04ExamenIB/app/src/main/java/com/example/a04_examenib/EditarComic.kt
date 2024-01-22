package com.example.a04_examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import com.example.a04_examenib.LogicaBaseDatos.ComicBD

class EditarComic : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_comic)

        // Recupera el ID
        val intent = intent
        val id = intent.getIntExtra("id", 1)
        // Buscar Pelicula
        val comic = ComicBD().getById(id)!!

        // Setear el texto en componentes visuales
        val titulo = findViewById<EditText>(R.id.input_titulo)
        val director = findViewById<EditText>(R.id.input_autor)
        val ano = findViewById<EditText>(R.id.input_ano)
        val precioEntrada = findViewById<EditText>(R.id.input_precio_entrada)


        titulo.setText(comic.tituloComic)
        director.setText(comic.autorComic)
        ano.setText(comic.publicacion.toString())
        precioEntrada.setText(comic.precioComic.toString())


        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_pelicula)
        botonActualizar.setOnClickListener {
            comic.tituloComic = titulo.text.toString()
            comic.autorComic = director.text.toString()
            comic.publicacion = ano.text.toString().toInt()
            comic.precioComic = precioEntrada.text.toString().toDouble()


            ComicBD().update(comic)

        }
        val botonVolver = findViewById<Button>(R.id.btn_volver_3)
        botonVolver.setOnClickListener {
            finish()
        }
    }


}