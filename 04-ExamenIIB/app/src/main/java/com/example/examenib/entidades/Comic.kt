package com.example.examenib.entidades

import java.text.SimpleDateFormat
import java.util.Date


class Comic(
    val id: String,
    val tituloComic: String,
    val precioComic: Long,
    val recomendado: Boolean,
    val fechaPublicacion: Date,
    val idProductora: String? = null
) {
    override fun toString(): String {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

        // Formatea la fecha a una cadena en el formato especificado
        val fechaFormateada = formatoFecha.format(fechaPublicacion)

        return "Comic:\nId:$id\ntitulo:$tituloComic\nPrecio:$precioComic\nComicRecomendado:$recomendado\nFechaPublicacion:$fechaFormateada"
    }
}