package com.example.b2023_gr1sw_jlcj

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.net.Uri
import android.provider.ContactsContract
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    //Logica de negocio
                    val data = result.data
                    mostrarSnackbar(
                        "${data?.getStringExtra("NombreModificado")}"
                    )
                }
            }
        }

    fun mostrarSnackbar(texto: String){
        Snackbar.make(
            findViewById(R.id.id_layout_main),
            texto,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    val callbackIntentPickUri =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result ->
            if(result.resultCode === Activity.RESULT_OK){
                if(result.data != null){
                    if(result.data!!.data != null){
                        val uri: Uri = result.data!!.data!!
                        val cursor = contentResolver.query(
                            uri, null, null, null, null, null)
                        cursor?.moveToFirst()
                        val indiceTelefonico = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono = cursor?.getString(indiceTelefonico!!)
                        cursor?.close()
                        mostrarSnackbar("Telefono ${telefono}")
                    }
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        //Base de datos sqlite
        EBaseDeDatos.tablaEntrenador = ESqliteHelperEntrenador(this)

         setContentView(R.layout.activity_main)
         val botonCiclovida = findViewById<Button>(R.id.btn_ciclo_vida)
         botonCiclovida
             .setOnClickListener{
                 irActividad(ACicloVida:: class.java)
             }
         val botonListView = findViewById<Button>(R.id.btn_ir_list_view)
         botonListView.setOnClickListener{
             irActividad(BListView::class.java)
         }

        val botonIntentImplicito = findViewById<Button>(
            R.id.btn_ir_intent_implicito)
        botonIntentImplicito.setOnClickListener {
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            callbackIntentPickUri.launch(intentConRespuesta)
        }

        val botonIntentExplicito = findViewById<Button>(
            R.id.btn_ir_intent_explicito)
        botonIntentExplicito.setOnClickListener {
            abrirActividadConParametros(
                CIntentExplicitoParametros::class.java)

        }

        val botonSqlite = findViewById<Button>(R.id.btn_sqlite)
        botonSqlite
            .setOnClickListener {
                irActividad(ECrudEntrenador::class.java)
            }

        val botonRView = findViewById<Button>(R.id.btn_recycler_view)
        botonRView
            .setOnClickListener {
                irActividad(FRecyclerView::class.java)
            }

        val botonGoogleMaps = findViewById<Button>(R.id.btn_google_maps)
        botonGoogleMaps.setOnClickListener { irActividad(GGoogleMapsActivity::class.java) }

        val botonHFirebaseUI = findViewById<Button>(R.id.btn_intent_firebase_ui)
        botonHFirebaseUI.setOnClickListener { irActividad((HFirebaseUIAuth::class.java)) }



    }

    fun abrirActividadConParametros(
        clase: Class<*>
    ){
        val intentExplicito = Intent(this,clase)
        //Enviar parametros (solamente variables primitivas)
        intentExplicito.putExtra("nombre", "Jorge")
        intentExplicito.putExtra("apellido", "Cordero")
        intentExplicito.putExtra("edad", 34)
        callbackContenidoIntentExplicito.launch(intentExplicito)
    }
    fun irActividad(clase: Class<*>){
        val intent = Intent(this,clase)
        startActivity(intent)
    }
}

