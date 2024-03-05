package com.example.examenib.fireStore


import com.example.examenib.entidades.Comic
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ComicFireStore {
    companion object{
        fun crearComic(comic: Comic, idProductora: String){
            val db = Firebase.firestore
            val comics = db.collection("comics")

            val datosComics = hashMapOf(
                "titulo" to comic.tituloComic,
                "precio" to comic.precioComic,
                "recomendado" to comic.recomendado,
                "fechaPublicacion" to comic.fechaPublicacion,
                "idProducto" to idProductora
            )
            comics.document(comic.id).set(datosComics)
        }

        fun consultarComics(listener: (ArrayList<Comic>) -> Unit)
        {
            val db = Firebase.firestore
            val arregloComics = arrayListOf<Comic>()
            val comicsRefUnico = db.collection("comics")

            comicsRefUnico
                .orderBy("titulo", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener {querySnapshot ->
                    for (comic in querySnapshot){
                        comic.id
                        arregloComics.add(anadirComic(comic))
                    }
                    listener(arregloComics)
                }
                .addOnFailureListener{
                    // Errores
                }
        }


        fun anadirComic(
            comic: QueryDocumentSnapshot
        ) : Comic {
            val nuevaComic =  Comic(
                comic.id as String,
                comic.data.get("titulo") as String,
                comic.data.get("precio") as Long,
                comic.data.get("recomendado") as Boolean,
                (comic.data.get("fechaPublicacion") as com.google.firebase.Timestamp).toDate()
            )
            return nuevaComic
        }

        fun consultarComic(
            id: String,
            onSuccess: (Comic) -> Unit
        ) {
            val db = Firebase.firestore
            val comicsRefUnica = db.collection("comics")
            comicsRefUnica
                .document(id)
                .get() // obtener 1 DOCUMENTO
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        if (document != null && document.exists()) {
                            val comic = Comic(
                                document.reference.id,
                                document.data?.get("titulo") as String,
                                document.data?.get("precio") as Long,
                                document.data?.get("recomendado") as Boolean,
                                (document.data?.get("fechaPublicacion") as com.google.firebase.Timestamp).toDate()
                            )
                            onSuccess(comic)
                        } else {
                            //salio mal
                        }
                    } else {
                        //salio mal
                    }
                }
        }

        fun eliminarComic(
            id: String
        ){
            val db = Firebase.firestore
            val comicsRefUnica = db
                .collection("comics")

            comicsRefUnica
                .document(id)
                .delete()
                .addOnCompleteListener{ /* si todo sale bien */}
                .addOnFailureListener{/* Si algo salio mal*/}
        }

        fun actualizarComics(
            comic: Comic
        ){
            val db = Firebase.firestore
            val comicsRefUnica = db
                .collection("comics")

            val datosActualizados = hashMapOf(
                "titulo" to comic.tituloComic,
                "precio" to comic.precioComic,
                "recomendado" to comic.recomendado,
                "fechaPublicacion" to comic.fechaPublicacion
            )

            comicsRefUnica
                .document(comic.id)
                .update(datosActualizados as Map<String, Any>)
                .addOnSuccessListener {
                    // Operación de actualización exitosa
                }
                .addOnFailureListener { e ->
                    // Manejar el error en caso de falla
                }
        }

        fun consultarComicsProductora(
            id: String,
            listener: (ArrayList<Comic>) -> Unit
        ){
            var arregloComics = arrayListOf<Comic>()
            val db = Firebase.firestore
            val comicsRefUnica = db.collection("comics")
            comicsRefUnica
                .whereEqualTo("idProductora", id)
                .orderBy("precio", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    for (comic in querySnapshot){
                        comic.id
                        arregloComics.add(anadirComic(comic))
                    }
                    listener(arregloComics)
                }
                .addOnFailureListener{
                    // Errores
                }
        }
    }
}