package com.example.deber_03

import java.time.LocalDate

data class Habitos (
    val tituloHabito : String,
    val fechaCreacion : LocalDate,
    val descripcion: String,
    val estadoHabito: String
)