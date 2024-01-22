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
import androidx.appcompat.app.AlertDialog
import com.example.a04_examenib.LogicaBaseDatos.ProductoraBD
import com.example.a04_examenib.entidades.Productora

import com.google.android.material.snackbar.Snackbar

class MostrarProductora : AppCompatActivity() {
    val arregloProductora = ProductoraBD().getAll()
    var posicionItemSeleccionado = 0
    var idProductoraSeleccionada = 0
    lateinit var listView: ListView
    lateinit var adaptador: ArrayAdapter<Productora>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_productora)
        listView = findViewById<ListView>(R.id.lv_mostrar_productora)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arregloProductora
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonCrearProductora = findViewById<Button>(R.id.btn_crear_productora)
        botonCrearProductora.setOnClickListener {
            crearProductora()
        }

        registerForContextMenu(listView)
    }

    fun crearProductora() {
        val productora = Productora(
            null,
            "Nueva Productora",
            "Nuevo Pais",
            2024,
            82
        )
        ProductoraBD().create(productora)
        adaptador.notifyDataSetChanged()
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
        posicionItemSeleccionado = posicion

        val productoraSeleccionada = arregloProductora.get(posicion)
        idProductoraSeleccionada = productoraSeleccionada.id!!
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_editar_productora -> {
                irActividadConId(EditarProductora::class.java, idProductoraSeleccionada)
                return true
            }

            R.id.mi_eliminar_productora -> {
                abrirDialogo()
                return true
            }

            R.id.mi_ver_comics -> {
                irActividadConId(MostrarComics::class.java, idProductoraSeleccionada)
                return true
            }

            else -> super.onContextItemSelected(item)
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
        builder.setPositiveButton("Aceptar") { _, _ ->
            ProductoraBD().deleteById(idProductoraSeleccionada)
            adaptador.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }

    override fun onResume() {
        super.onResume()
        adaptador.notifyDataSetChanged()
    }
}