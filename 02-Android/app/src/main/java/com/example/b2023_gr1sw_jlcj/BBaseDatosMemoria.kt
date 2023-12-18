package com.example.b2023_gr1sw_jlcj

class BBaseDatosMemoria {

    //EMPEZAR EL COMPANION OBJECT
    companion object{
        var arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador.add(
                BEntrenador(1, "Jorge", "jorge@ex.com")
            )
            arregloBEntrenador.add(
                BEntrenador(2, "Luis", "luis@ex.com")
            )
            arregloBEntrenador.add(
                BEntrenador(3, "Carolina", "caro@ex.com")
            )
        }
    }

}