package com.example.a04_examenib.LogicaBaseDatos

import com.example.a04_examenib.baseDatos.BBaseDatosMemoria
import com.example.a04_examenib.entidades.Comic
import com.example.a04_examenib.entidades.Productora

class ProductoraBD {
    fun getById(id: Int): Productora? {
        var productoraEncontrada: Productora? = null
        getAll().forEach { productora: Productora ->
            if ( productora.id == id) productoraEncontrada = productora
        }
        return productoraEncontrada
    }

    fun getAll(): ArrayList<Productora> {
        return BBaseDatosMemoria.listaDeProductoras
    }

    fun create(productora: Productora) {
        val listaProductoras = getAll()
        if (listaProductoras.isEmpty()) {
            productora.id = 0
        } else {
            productora.id = listaProductoras.last().id?.plus(1)!!
        }
        listaProductoras.add(productora)
    }

    fun update(productoraActualizado: Productora) {
        val listaProductoras = getAll()
        listaProductoras.forEachIndexed { index, productora ->
            if (productora.id == productoraActualizado.id) {
                listaProductoras[index] = productoraActualizado
                return
            }
        }
    }

    fun deleteById(id: Int): Boolean {
        val comicBD = ComicBD()
        getAll().forEach { productora: Productora ->
            if (productora.id == id) {
                productora.listaComics.forEach { comic: Comic ->
                    comicBD.deleteById(comic.id!!)
                }
            }
        }
        return getAll().removeIf { productora -> (productora.id == id) }
    }
}