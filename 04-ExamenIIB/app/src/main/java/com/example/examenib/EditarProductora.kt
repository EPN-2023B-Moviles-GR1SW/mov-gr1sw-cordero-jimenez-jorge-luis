package com.example.examenib

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.examenib.fireStore.ProductoraFireStore
import com.example.examenib.entidades.Productora
import java.util.Date

class EditarProductora : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_productora)

        val id = findViewById<EditText>(R.id.inp_idEditarProductora)
        val nombre = findViewById<EditText>(R.id.inp_editarNombreProd)
        val fundador = findViewById<EditText>(R.id.inp_editarFundador)
        val cantidadComics = findViewById<EditText>(R.id.inp_editarCantComics)

        var idProductoraSeleccionado = intent.getStringExtra("idProductora");

        if (idProductoraSeleccionado != null) {
            ProductoraFireStore.consultarProducto(idProductoraSeleccionado) { it ->
                it?.let {
                    id.setText(it.id)
                    nombre.setText(it.nombreProductora)
                    fundador.setText(it.fundador)
                    cantidadComics.setText(it.cantidadComics.toString())
                }
            }
        }
/*        ProductoraFireStore.consultarProducto(idProductoraSeleccionado!!){
            id.setText(it.id)
            nombre.setText(it.nombreProductora)
            fundador.setText(it.fundador)
            cantidadComics.setText(it.cantidadComics.toString())
        }*/


        val btnEditarProductora = findViewById<Button>(R.id.btn_editar_productora)
        btnEditarProductora.setOnClickListener{
            editarProductora()
            irActividad(ListViewProductora::class.java)
        }

        val btnCancelar = findViewById<Button>(R.id.btn_cancelar_editarProduct)
        btnCancelar.setOnClickListener {
            irActividad(ListViewProductora::class.java)
        }


    }

    fun editarProductora() {
        val id = findViewById<EditText>(R.id.inp_idEditarProductora)
        val nombre = findViewById<EditText>(R.id.inp_editarNombreProd)
        val fundador = findViewById<EditText>(R.id.inp_editarFundador)
        val cantidadComics = findViewById<EditText>(R.id.inp_editarCantComics)

        val productoraActualizado = Productora(id.text.toString(),
            nombre.text.toString(),
            fundador.text.toString(),
            cantidadComics.text.toString().toLong(),
            Date())
        ProductoraFireStore.actualizarProductoras(productoraActualizado)

    }

    fun irActividad(
        clase: Class<*>
    ) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}