package com.example.examenib

import android.app.AlertDialog
import android.content.DialogInterface
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
import com.example.examenib.fireStore.ProductoraFireStore
import com.example.examenib.fireStore.ComicFireStore
import com.google.android.material.snackbar.Snackbar

class ListViewComics : AppCompatActivity() {
    var posicionItem = 0
    var idProductora = ""
    var comicProductoEscogido = ""




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_comics)
        cargarComics()
        val nombreProductora = findViewById<TextView>(R.id.tv_nombre_productora)
        idProductora = intent.getStringExtra("idProductora")!!

        if (idProductora != null) {
            ProductoraFireStore.consultarProducto(idProductora!!) {
                nombreProductora.text = it.nombreProductora
            }
        } else {

            Snackbar.make(findViewById(R.id.lv_comic),
                "Error: idProductora no encontrado", Snackbar.LENGTH_LONG).show()
            finish() // Close the activity
        }





        ProductoraFireStore.consultarProducto(idProductora!!){
            nombreProductora.text = it.nombreProductora
        }

        val botonVolver = findViewById<Button>(R.id.btn_regresar)
        botonVolver.setOnClickListener{
            irActividad(ListViewProductora :: class.java,"0")
        }

        val botonAnadirListView = findViewById<Button>(R.id.btn_crear_nuevoComic)
        botonAnadirListView.setOnClickListener{
            agregarComic()
        }

        val listView = findViewById<ListView>(R.id.lv_comic)
        registerForContextMenu(listView)

    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mn_editar_comic -> {
                mostrarSnackbar("${posicionItem}")
                irActividad(EditarComic::class.java, comicProductoEscogido)
                return true
            }
            R.id.mn_eliminar_comic -> {
                mostrarSnackbar("${posicionItem}")
                abrirDialogo()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onCreateContextMenu( menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo? ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_comic, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItem = posicion
        mostrarSnackbar("${posicion}")
        idProductora = intent.getStringExtra("idProductora")!!


        ComicFireStore.consultarComicsProductora(idProductora) { comicProductoEscogido = it[posicionItem].id }
    }



    private fun cargarComics() {
        val listView = findViewById<ListView>(R.id.lv_comic)

        idProductora = intent.getStringExtra("idProductora")!!
        ComicFireStore.consultarComicsProductora(idProductora!!) { comics ->
            if (comics != null) {
                println(comics.size)

                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    comics
                )
                listView.adapter = adapter
                adapter.notifyDataSetChanged()
                registerForContextMenu(listView)
            }
        }
    }

    /*    private fun cargarComics() {
            val listView = findViewById<ListView>(R.id.lv_comic)

            idProductora = intent.getStringExtra("idProductora")!!
            ComicFireStore.consultarComicsProductora(idProductora!!) { comics ->
                println(comics.size)

                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    comics
                )
                listView.adapter = adapter
                adapter.notifyDataSetChanged()
                registerForContextMenu(listView)
            }
        }*/

/*    private fun cargarComics(){
        val listView = findViewById<ListView>(R.id.lv_comic)

        idProductora = intent.getStringExtra("idProductora")!!
        ComicFireStore.consultarComicsProductora(idProductora!!){
            println(it.size)
            if (it != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    it!!
                )
                listView.adapter = adapter
                adapter.notifyDataSetChanged()
                registerForContextMenu(listView)
            }
        }
    }*/

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{ dialog, which ->
                eliminarComic()
            }
        )

        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }

    fun eliminarComic (){
        ComicFireStore.eliminarComic(comicProductoEscogido)
        cargarComics()
    }

    fun mostrarSnackbar(texto:String){
        val snack = Snackbar.make(findViewById(R.id.lv_comic),
            texto, Snackbar.LENGTH_LONG)
        snack.show()
    }

    fun agregarComic(){
        idProductora = intent.getStringExtra("idProducto")!!
        irActividad(CrearComic::class.java, idProductora!!)
        cargarComics()
    }

    fun irActividad (
        clase: Class <*>, id:String
    ) {
        val intent = Intent(this, clase)
        intent.putExtra("idProducto",idProductora)
        startActivity(intent)
    }
}