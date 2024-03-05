package com.example.examenib

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.examenib.fireStore.ProductoraFireStore
import com.google.android.material.snackbar.Snackbar

class ListViewProductora : AppCompatActivity() {

    var posicionItem = 0
    var idProductora = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view_productoras)
        cargarProductoras()
        val btnAnadirListView = findViewById<Button>( R.id.btn_crear_lvproductora)
        btnAnadirListView.setOnClickListener{
            agregarProductora()
        }
        val listView = findViewById<ListView>(R.id.lv_productora)

        registerForContextMenu(listView)

    }

    private fun cargarProductoras(){
        val listView = findViewById<ListView>(R.id.lv_productora)
        ProductoraFireStore.consultarProductoras {
            if (it!= null) {
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
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_productora, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionItem = posicion
        mostrarSnackbar("${posicion}")
        ProductoraFireStore.consultarProductoras { idProductora = it[posicion].id }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mn_editar_prod ->{
                mostrarSnackbar("${posicionItem}")
                irActividad(EditarProductora::class.java)
                return true
            }
            R.id.mn_eliminar_prod -> {
                mostrarSnackbar("${posicionItem}")
                abrirDialogo()
                return true
            }
            R.id.mn_ver_comics -> {
                mostrarSnackbar("${posicionItem}")
                irActividad(ListViewComics::class.java)
                return true
            }
            else -> super.onContextItemSelected(item)

        }
    }

    fun agregarProductora() {
        irActividad(CrearProductora:: class.java)
        cargarProductoras()
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eliminar")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{ dialog, which ->
                mostrarSnackbar("Acepto ${which}")
                eliminarProductora()

            }
        )

        builder.setNegativeButton(
            "Cancelar",
            null
        )

        val dialogo = builder.create()
        dialogo.show()
    }

    fun eliminarProductora (){
        mostrarSnackbar(idProductora)

        ProductoraFireStore.eliminarProductora(idProductora)
        cargarProductoras()
    }
    fun mostrarSnackbar(texto:String){
        val snack = Snackbar.make(findViewById(R.id.lv_productora),
            texto, Snackbar.LENGTH_LONG)
        snack.show()
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        intent.putExtra("idProducto",idProductora)
        startActivity(intent)
    }
}
