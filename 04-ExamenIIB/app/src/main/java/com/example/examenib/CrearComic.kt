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

class CrearComic : AppCompatActivity() {

    var radioT:RadioButton?=null
    var radioF:RadioButton?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_comic)
        var idProductora = intent.getStringExtra("idProductora")

        radioT = findViewById(R.id.rdb_recom_true_editar)
        radioF = findViewById(R.id.rdb_recom_false_editar)
        val btnCrearNuevoComic = findViewById<Button>(R.id.btn_crear_comics)
        btnCrearNuevoComic.setOnClickListener{
            crearNuevoComic()
            irActividad(ListViewComics::class.java, idProductora!!)
        }

        val btnCancelar = findViewById<Button>(R.id.btn_cancelar_comics)
        btnCancelar.setOnClickListener{irActividad(ListViewProductora:: class.java,idProductora!!)}
    }

    fun crearNuevoComic(){
        val id = findViewById<EditText>(R.id.inp_idComic)
        val titulo = findViewById<EditText>(R.id.inp_titulo)
        val precio = findViewById<EditText>(R.id.inp_precio)
        val recomendado: Boolean = radioT?.isChecked == true
        var idProductora = intent.getStringExtra("idProductora")

        val nuevaComic = Comic(id.text.toString(),titulo.text.toString(),precio.text.toString().toLong(), recomendado, Date())
        ComicFireStore.crearComic(nuevaComic, idProductora!!)
    }


    fun irActividad (
        clase: Class <*>, idProductora: String
    ) {
        val intent = Intent(this, clase)
        intent.putExtra("idProductora", idProductora)
        startActivity(intent)
    }

}