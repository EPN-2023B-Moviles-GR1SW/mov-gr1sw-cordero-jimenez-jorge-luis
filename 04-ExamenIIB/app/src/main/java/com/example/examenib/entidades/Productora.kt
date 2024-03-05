package com.example.examenib.entidades

import java.text.SimpleDateFormat
import java.util.Date

class Productora(
    var id: String,
    var nombreProductora: String,
    var fundador: String,
    var cantidadComics: Long,
    var fechaFundacion: Date,
    var comic: MutableList<Comic>? = null
) {
    override fun toString(): String {
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

        // Formatea la fecha a una cadena en el formato especificado
        val fechaFormateada = formatoFecha.format(fechaFundacion)

        return "Productora:\nID:$id\nNombre:$nombreProductora\nFundador:$fundador\nCantidadComics:$cantidadComics\nFechaFundacion:$fechaFormateada"
    }
}