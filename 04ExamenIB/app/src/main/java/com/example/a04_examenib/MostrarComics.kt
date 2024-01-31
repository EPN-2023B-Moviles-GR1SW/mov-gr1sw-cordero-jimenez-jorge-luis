package com.example.a04_examenib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.a04_examenib.LogicaBaseDatos.ComicBD
import com.example.a04_examenib.LogicaBaseDatos.ProductoraBD
import com.example.a04_examenib.entidades.Comic
import com.example.a04_examenib.entidades.Productora
import com.google.android.material.snackbar.Snackbar

class MostrarComics : AppCompatActivity() {
    var arregloComics = arrayListOf<Comic>()
    var productora: Productora = Productora()
    var posicionItemSeleccionado = 0
    var idComicSeleccionada = 0
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Productora>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_comics)
        // Recupera el ID
        val intent = intent
        val id = intent.getIntExtra("id", 1)
        // Buscar Comics
        productora = ProductoraBD().getById(id)!!
        arregloComics = productora.listaComics

        val nombreProductora = findViewById<TextView>(R.id.tv_nombre_cine)
        nombreProductora.text = "${productora.nombre}"

        listView = findViewById<ListView>(R.id.lv_mostrar_comics)
        /*
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloComics,
        )

         */
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonCrearComic = findViewById<Button>(R.id.btn_crear_comic)
        botonCrearComic.setOnClickListener {
            crearComic()
        }

        registerForContextMenu(listView)
    }
    fun crearComic() {
        val comic = Comic(
            null,
            "Nuevo Comic",
            "Nuevo Actor",
            "Ciencia Ficcion",
            10.0,
            2024,
            productora
        )
        ComicBD().create(comic)
        adaptador.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        // Llenamos las opciones del menú
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_comic, menu)
        // Obtener el ID del ArrayListSeleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItemSeleccionado = posicion
        // Acceder al objeto Comic en la posición seleccionada
        val comicSeleccionado = arregloComics[posicion]
        // Obtener el ID de la Comic seleccionada
        idComicSeleccionada = comicSeleccionado.id!!
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_comic -> {
                irActividadConId(EditarComic::class.java, idComicSeleccionada)
                return true
            }

            R.id.mi_eliminar_comic -> {
                abrirDialogo()
                return true
            }

            else -> super.onContextItemSelected(item)
        }
        val botonVolver = findViewById<Button>(R.id.btn_volver_comic)
        botonVolver.setOnClickListener {
            val intent = Intent(this, MostrarProductora::class.java)
            startActivity(intent)
        }
    }


    fun irActividadConId(
        clase: Class<*>,
        id: Int
    ) {
        val intent = Intent(this, clase)
        intent.putExtra("id", id)
        startActivity(intent)
    }


    fun abrirDialogo() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea eliminar?")
        builder.setPositiveButton(
            "Aceptar"
        ) { dialog, which ->
            ComicBD().deleteById(idComicSeleccionada)
            adaptador.notifyDataSetChanged()
        }
        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }

    override fun onResume() {
        super.onResume()
        adaptador.notifyDataSetChanged()
    }
}