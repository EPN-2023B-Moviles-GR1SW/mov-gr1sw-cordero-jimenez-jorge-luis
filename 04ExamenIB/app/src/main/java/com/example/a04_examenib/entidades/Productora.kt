package com.example.a04_examenib.entidades

class Productora(
    var id: Int?,
    var nombre: String,
    var paisOrigen: String,
    var fundacion: Int,
    var cantidadComics: Int,
    var listaComics: ArrayList<Comic> = arrayListOf()
){
    constructor() : this(null,"","",0,0,)

    override fun toString(): String {
        return "$nombre"
    }
}