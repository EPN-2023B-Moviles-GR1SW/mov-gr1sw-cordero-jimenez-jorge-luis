package com.example.a04_examenib.baseDatos

import com.example.a04_examenib.entidades.Comic
import com.example.a04_examenib.entidades.Productora
import java.util.ArrayList

class BBaseDatosMemoria {
    companion object{
        var listaDeProductoras: ArrayList<Productora> = arrayListOf()
        var listaDeComics: ArrayList<Comic> = arrayListOf()

        init {

            //Comic 0
            listaDeComics.add(
                Comic(
                    0,
                    "Spider-man:Entre los muertos",
                    "Frank Cho",
                    "Ciencia Ficcion",
                    50.0,
                    2021
                )
            )
            //Comic 1
            listaDeComics.add(
                Comic(
                    1,
                    "Lobezno: Origen",
                    "Len Wein",
                    "Ciencia Ficcion",
                    80.0,
                    1980
                )
            )

            //Comic 2
            listaDeComics.add(
                Comic(
                    2,
                    "El Hombre sin Miedo",
                    "Andy Kubert",
                    "Ciencia Ficcion",
                    90.0,
                    1990
                )
            )

            //Productora 0
            listaDeProductoras.add(
                Productora(
                    0,
                    "Marvel Studio",
                    "Estados Unidos",
                    1980,
                    800
                )
            )

            //Productora 1
            listaDeProductoras.add(
                Productora(
                    1,
                    "DC Comics",
                    "Estados Unidos",
                    1950,
                    750
                )
            )

            //Productora 2
            listaDeProductoras.add(
                Productora(
                    2,
                    "Toe Animation",
                    "Japon",
                    2000,
                    250
                )
            )

            //Obtener Comics
            listaDeProductoras.get(0).listaComics.add(listaDeComics.get(0))
            listaDeProductoras.get(0).listaComics.add(listaDeComics.get(1))
            listaDeProductoras.get(1).listaComics.add(listaDeComics.get(2))

            //Obtener Productora
            listaDeComics.get(0).productora = listaDeProductoras.get(0)
            listaDeComics.get(1).productora = listaDeProductoras.get(0)
            listaDeComics.get(2).productora = listaDeProductoras.get(1)


        }
    }
}