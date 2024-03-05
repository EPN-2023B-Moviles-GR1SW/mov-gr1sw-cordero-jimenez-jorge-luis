package com.example.examenib.fireStore

import com.example.examenib.entidades.Productora
import com.example.examenib.entidades.Comic
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductoraFireStore {

    companion object{
        fun crearProductora(productora: Productora){
            val db = Firebase.firestore
            val productoras = db.collection("productora")

            val datosProductoras = hashMapOf(
                "nombre" to productora.nombreProductora,
                "fundador" to productora.fundador,
                "cantidadComics" to productora.cantidadComics,
                "fechaFundacion" to productora.fechaFundacion
            )
            productoras.document(productora.id).set(datosProductoras)
        }

        fun consultarProductoras(listener: (ArrayList<Productora>) -> Unit)
        {
            val db = Firebase.firestore
            val arregloProductoras = arrayListOf<Productora>()
            val productorasRefUnico = db.collection("productora")

            productorasRefUnico
                .orderBy("nombre", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener {querySnapshot ->
                    // it == eso (lo que llegue)
                    for (productoras in querySnapshot){
                        productoras.id
                        arregloProductoras.add(anadirProductora(productoras))
                    }
                    listener(arregloProductoras)
                }
                .addOnFailureListener{
                    // Errores
                }
        }


        fun anadirProductora(
            productora: QueryDocumentSnapshot
        ) : Productora {
            val nuevoPlatosComic =  Productora(

                productora.id as String,
                productora.data.get("nombre") as String,
                productora.data.get("fundador") as String,
                productora.data.get("cantidadComics") as Long,
                (productora.data.get("fechaFundacion") as com.google.firebase.Timestamp).toDate(),
                productora.data.get("comics") as ArrayList<Comic>?,
            )
            return nuevoPlatosComic
        }

        fun consultarProducto(
            id: String,
            listener: (Productora) -> Unit,
        ) {
            val db = Firebase.firestore
            val productorasRefUnica = db.collection("productos")
            productorasRefUnica
                .document(id)
                .get() // obtener 1 DOCUMENTO
                .addOnSuccessListener { querySnapshot ->
                    val document = querySnapshot
                    val productora = Productora(
                        document.reference.id,
                        document.data?.get("nombre") as String,
                        document.data?.get("fundador") as String,
                        document.data?.get("cantidadComics") as Long,
                        (document.data?.get("fechaFundacion") as com.google.firebase.Timestamp).toDate(),)
                    listener(productora)
                }
                .addOnFailureListener{
                    // Errores
                }
        }

        fun eliminarProductora(
            id: String
        ){
            val db = Firebase.firestore
            val productorasRefUnica = db
                .collection("productora")

            productorasRefUnica
                .document(id)
                .delete()
                .addOnCompleteListener{ /* si todo sale bien */}
                .addOnFailureListener{/* Si algo salio mal*/}
        }

        fun actualizarProductoras(
            productora: Productora
        ){
            val db = Firebase.firestore
            val productorasRefUnica = db
                .collection("productora")


            val datosActualizados = hashMapOf(
                "nombre" to productora.nombreProductora,
                "fundador" to productora.fundador,
                "cantidadEmpleados" to productora.cantidadComics,
                "fechaFundacion" to productora.fechaFundacion,
                "comics" to listOf("comic1", "comic2"),
            )

            productorasRefUnica
                .document(productora.id.toString())
                .update(datosActualizados)
                .addOnSuccessListener {
                    // Operación de actualización exitosa
                }
                .addOnFailureListener { e ->
                    // Manejar el error en caso de falla
                }
        }
    }
}