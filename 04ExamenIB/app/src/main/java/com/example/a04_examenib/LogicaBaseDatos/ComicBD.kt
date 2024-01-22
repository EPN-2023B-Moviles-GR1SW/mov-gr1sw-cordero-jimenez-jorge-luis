package com.example.a04_examenib.LogicaBaseDatos

import com.example.a04_examenib.baseDatos.BBaseDatosMemoria
import com.example.a04_examenib.entidades.Comic

class ComicBD {
    fun getById(id: Int): Comic? {
        var comicEncontrado: Comic? = null
        getAll().forEach { comic: Comic ->
            if (comic.id == id) comicEncontrado = comic
        }
        return comicEncontrado
    }

    fun getAll(): ArrayList<Comic> {
        return BBaseDatosMemoria.listaDeComics
    }

    fun create(comic: Comic) {
        val listaComics = getAll()
        if (listaComics.isEmpty()) {
            comic.id = 0
        } else {
            comic.id = listaComics.last().id?.plus(1)!!
        }
        listaComics.add(comic)
        comic.productora.listaComics.add(comic)
    }

    fun update(comicActualizado: Comic) {
        val listaComics = getAll()
        listaComics.forEachIndexed { index, comic ->
            if (comic.id == comicActualizado.id) {
                listaComics[index] = comicActualizado
                return
            }
        }
    }

    fun deleteById(id: Int) {
        val comic = getById(id)
        if (comic != null) {
            comic.productora.listaComics.remove(comic)
            getAll().remove(comic)
        }
    }
}