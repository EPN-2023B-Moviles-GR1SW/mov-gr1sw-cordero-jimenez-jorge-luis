package com.example.a04_examenib.entidades

class Comic(
    var id: Int?,
    var tituloComic: String,
    var autorComic: String,
    var generoComic: String,
    var precioComic: Double,
    var publicacion: Int,
    var productora: Productora = Productora()

) {
    constructor(): this(null,"","","",0.0,0)

    override fun toString(): String {
        return "$tituloComic -$autorComic"
    }


}