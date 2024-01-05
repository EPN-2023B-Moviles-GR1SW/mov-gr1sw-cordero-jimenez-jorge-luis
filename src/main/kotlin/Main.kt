import java.io.File
import java.io.FileWriter
import java.io.IOException


fun main() {
    val productoras = mutableListOf<Productora>()
    val comics = mutableListOf<Comic>()

    cargarDesdeArchivo("productoras.txt")?.forEach {
        val partes = it.split(",")
        if (partes.size == 5) {
            val productora = Productora(partes[0].toInt(), partes[1], partes[2], partes[3], partes[4])
            productoras.add(productora)
        }
    }

    cargarDesdeArchivo("comics.txt")?.forEach {
        val partes = it.split(",")
        if (partes.size == 6) {
            val comic = Comic(partes[0].toInt(), partes[1], partes[2], partes[3], partes[4], partes[5].toInt())
            comics.add(comic)
        }
    }

    while (true) {
        println("Seleccione una opción:")
        println("1. Crear Productora")
        println("2. Crear Comic")
        println("3. Mostrar Datos")
        println("4. Actualizar Datos")
        println("5. Salir")

        val userInput = readLine()
        val option = userInput?.toIntOrNull()

        if (option != null) {
            when (option) {
                1 -> {
                    val productora = crearProductora()
                    productoras.add(productora)
                }
                2 -> {
                    val comic = crearComic(productoras)
                    comics.add(comic)
                }
                3 -> mostrarDatos(productoras, comics)
                4 -> actualizarDatos(productoras, comics)
                5 -> break
                else -> println("Opción no válida. Inténtelo de nuevo.")
            }
        } else {
            println("Entrada no válida. Por favor, ingrese un número válido.")
        }
    }

    guardarEnArchivo("productoras.txt", productoras.map { it.toString() })
    guardarEnArchivo("comics.txt", comics.map { it.toString() })
}

fun crearProductora(): Productora {
    print("Ingrese el nombre de la productora: ")
    val nombre = readLine() ?: ""

    print("Ingrese el país de origen: ")
    val pais = readLine() ?: ""

    print("Ingrese el año de fundación: ")
    val fundacion = readLine() ?: ""

    print("Ingrese el sitio web: ")
    val sitioWeb = readLine() ?: ""

    val id = generarId()
    return Productora(id, nombre, pais, fundacion, sitioWeb)
}

fun crearComic(productoras: List<Productora>): Comic {
    print("Ingrese el título del cómic: ")
    val titulo = readLine() ?: ""

    print("Ingrese el autor del cómic: ")
    val autor = readLine() ?: ""

    print("Ingrese el género del cómic: ")
    val genero = readLine() ?: ""

    print("Ingrese el año de publicación del cómic: ")
    val anioPublicacion = readLine() ?: ""

    println("Seleccione una productora:")
    productoras.forEach { println("${it.id}. ${it.nombre}") }
    val productoraId = readLine()?.toIntOrNull() ?: 0

    val id = generarId()
    return Comic(id, titulo, autor, genero, anioPublicacion, productoraId)
}

fun mostrarDatos(productoras: List<Productora>, comics: List<Comic>) {
    println("\nProductoras:")
    productoras.forEach { println(it) }

    println("\nComics:")
    comics.forEach { println(it) }
}

fun actualizarDatos(productoras: MutableList<Productora>, comics: MutableList<Comic>) {
    println("Seleccione una opción:")
    println("1. Actualizar Productora")
    println("2. Actualizar Comic")

    when (readLine()?.toIntOrNull()) {
        1 -> {
            println("Ingrese el ID de la productora que desea actualizar:")
            val idProductora = readLine()?.toIntOrNull() ?: 0
            val productora = productoras.find { it.id == idProductora }
            if (productora != null) {
                println("Ingrese los nuevos datos de la productora:")
                productora.nombre = readLine() ?: ""
                productora.pais = readLine() ?: ""
                productora.fundacion = readLine() ?: ""
                productora.sitioWeb = readLine() ?: ""
                println("Productora actualizada con éxito.")
            } else {
                println("No se encontró la productora con ID $idProductora.")
            }
        }
        2 -> {
            println("Ingrese el ID del cómic que desea actualizar:")
            val idComic = readLine()?.toIntOrNull() ?: 0
            val comic = comics.find { it.id == idComic }
            if (comic != null) {
                println("Ingrese los nuevos datos del cómic:")
                comic.titulo = readLine() ?: ""
                comic.autor = readLine() ?: ""
                comic.genero = readLine() ?: ""
                comic.anioPublicacion = readLine() ?: ""
                println("Cómic actualizado con éxito.")
            } else {
                println("No se encontró el cómic con ID $idComic.")
            }
        }
        else -> println("Opción no válida. Inténtelo de nuevo.")
    }
}

fun generarId(): Int {
    return (1..Int.MAX_VALUE).random()
}

fun guardarEnArchivo(nombreArchivo: String, datos: List<String>) {
    val archivo = File(nombreArchivo)
    try {
        FileWriter(archivo).use { writer ->
            datos.forEach { dato ->
                writer.write("$dato\n")
            }
        }
        println("Datos guardados en el archivo '$nombreArchivo'")
    } catch (e: IOException) {
        println("Error al escribir en el archivo: ${e.message}")
    }
}

fun cargarDesdeArchivo(nombreArchivo: String): List<String>? {
    val archivo = File(nombreArchivo)
    return if (archivo.exists()) {
        try {
            return archivo.readLines()
        } catch (e: IOException) {
            println("Error al leer el archivo '$nombreArchivo': ${e.message}")
            null
        }
    } else {
        println("El archivo '$nombreArchivo' no existe.")
        null
    }
}
