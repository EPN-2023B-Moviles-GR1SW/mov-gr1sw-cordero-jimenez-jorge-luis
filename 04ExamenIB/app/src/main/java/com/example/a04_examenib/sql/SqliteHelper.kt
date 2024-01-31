package com.example.examen1b.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.a04_examenib.entidades.Comic
import com.example.a04_examenib.entidades.Productora
import java.text.SimpleDateFormat

class SqliteHelper (
    contexto: Context?,
): SQLiteOpenHelper(
    contexto,
    "BDMundoMusical", //nombre BDD
    null,
    1
){
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(db: SQLiteDatabase?) {
        // Crear la tabla de productora
        val crearTablaProductora = """
            CREATE TABLE PRODUCTORAS(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre VARCHAR(50),
            paisOrigen VARCHAR(50),
            fundacion INTEGER,
            cantidadComics INTEGER
            )
        """.trimIndent()
        db?.execSQL(crearTablaProductora)

        // Crear la tabla de comics
        val crearTablaComics = """
            CREATE TABLE CANCIONES(
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            tituloComic VARCHAR(100),
            idComic INTEGER,
            publicacion INTEGER,
            autorComic VARCHAR(50),
            generoComic VARCHAR(50),
            FOREIGN KEY(idComic) REFERENCES PRDOCTORAS(id)
            )
        """.trimIndent()
        db?.execSQL(crearTablaComics)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    // CRUD ARTISTAS
    fun crearProductora(
        nombre: String,
        paisOrigen: String,
        fundacion: Int,
        cantidadComics: Int
    ): Int {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("paisOrigen", paisOrigen)
        valoresAGuardar.put("fundacion", fundacion)
        valoresAGuardar.put("cantidadComics", cantidadComics)
        val id = basedatosEscritura
            .insert(
                "PRODUCTORAS", // nombre tabla
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return id.toInt()
    }

    fun eliminarProductora(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "PRODUCTORAS", // nombre tabla
                "id=?", // claúsula WHERE
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarProductora(
        id: Int,
        nombre: String,
        paisOrigen: String,
        fundacion: Int,
        cantidadComics: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("paisOrigen", paisOrigen)
        valoresAActualizar.put("fundacion", fundacion)
        valoresAActualizar.put("cantidadComics", cantidadComics)
        //where id...
        val parametrosConsultaUpdate = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "PRODUCTORAS", // nombre tabla
                valoresAActualizar,
                "id=?", // claúsula WHERE
                parametrosConsultaUpdate
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    fun consultarArtistaPorID(id: Int): Productora {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM ARTISTAS WHERE id = ?
        """.trimIndent()
        val parametrosConsulta = arrayOf(id.toString())
        val cursor = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsulta
        )
        val existeProductora = cursor.moveToFirst()
        // Si no existe la productora
        if (!existeProductora) return Productora(-1, "", "", 0, 0)
        // Si existe la productora
        val nombre = cursor.getString(1)
        val paisOrigen = cursor.getString(2)
        val fundacion = cursor.getInt(3)
        val cantidadComics = cursor.getInt(4)
        cursor.close()
        baseDatosLectura.close()
        return Productora(id, nombre, paisOrigen, fundacion, cantidadComics)
    }

    fun consultarProductora(): ArrayList<Productora> {
        val scriptConsultarProductora = """
            SELECT * FROM PRODUCTORAS
        """.trimIndent()
        val baseDatosLectura = readableDatabase
        val resultadoConsulta = baseDatosLectura.rawQuery(
            scriptConsultarProductora,
            null
        )
        val existeProductora = resultadoConsulta.moveToFirst()
        val arregloProductora = arrayListOf<Productora>()
        if (existeProductora) {
            do {
                val id = resultadoConsulta.getInt(0) // Columna indice 0 -> ID
                val nombre = resultadoConsulta.getString(1) // Columna indice 1 -> NOMBRE
                val paisOrigen = resultadoConsulta.getString(2) // Columna indice 2 -> EDAD
                val fundacion = resultadoConsulta.getInt(3)  // Columna indice 3 -> VIVO
                val cantidadComics = resultadoConsulta.getInt(4) // Columna indice 4 -> PATRIMONIO
                val productora = Productora(id, nombre, paisOrigen, fundacion, cantidadComics)
                arregloProductora.add(productora)
            } while (resultadoConsulta.moveToNext())
        }
        resultadoConsulta.close()
        baseDatosLectura.close()
        return arregloProductora
    }

    // CRUD Comics
    fun crearComics(
        titulo: String,
        idProductora: Int,
        autorComic: String,
        generoComic: String,
        precioComic: Double,
        fechaLanzamiento: String
    ): Boolean {
        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("titulo", titulo)
        valoresAGuardar.put("idProductora", idProductora)
        valoresAGuardar.put("autorComic", autorComic)
        valoresAGuardar.put("generoComic", generoComic)
        valoresAGuardar.put("precioComic", precioComic)
        valoresAGuardar.put("fechaLanzamiento", fechaLanzamiento)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "COMICS", // nombre tabla
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true
    }

    fun eliminarComic(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "COMICS", // nombre tabla
                "id=?", // claúsula WHERE
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarComic(
        titulo: String,
        idProductora: Int,
        autorComic: String,
        generoComic: String,
        precioComic: Double,
        fechaLanzamiento: String,
        id: Int
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("titulo", titulo)
        valoresAActualizar.put("idProductora", idProductora)
        valoresAActualizar.put("autorComic", autorComic)
        valoresAActualizar.put("generoComic", generoComic)
        valoresAActualizar.put("precioComic", precioComic)
        valoresAActualizar.put("fechaLanzamiento", fechaLanzamiento)
        //where id...
        val parametrosConsultaUpdate = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "CANCIONES", // nombre tabla
                valoresAActualizar,
                "id=?", // claúsula WHERE
                parametrosConsultaUpdate
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    fun consultarComics(): ArrayList<Comic> {
        val scriptConsultarComics = """
            SELECT * FROM CANCIONES
        """.trimIndent()
        val baseDatosLectura = readableDatabase
        val resultadoConsulta = baseDatosLectura.rawQuery(
            scriptConsultarComics,
            null
        )
        val existeComic = resultadoConsulta.moveToFirst()
        val arregloComics = arrayListOf<Comic>()
        if (existeComic) {
            do {
                val id = resultadoConsulta.getInt(0) // Columna indice 0 -> ID
                val titulo = resultadoConsulta.getString(1) // Columna indice 1 -> NOMBRE
                val autorComic = resultadoConsulta.getString(2) // Columna indice 2 -> ID ARTISTA
                val generoComic = resultadoConsulta.getString(3) // Columna indice 3 -> DURACION
                val precioComic = resultadoConsulta.getDouble(4) // Columna indice 4 -> ALBUM
                val publicacion = resultadoConsulta.getInt(5) // Columna indice 5 -> GENERO

                val comic = Comic(id, titulo, autorComic, generoComic, precioComic, publicacion)
                arregloComics.add(comic)
            } while (resultadoConsulta.moveToNext())
        }
        resultadoConsulta.close()
        baseDatosLectura.close()
        return arregloComics
    }

    fun consultarComicPorID(id: Int): Comic {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM CANCIONES WHERE id = ?
        """.trimIndent()
        val parametrosConsulta = arrayOf(id.toString())
        val cursor = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsulta
        )
        val existeComic = cursor.moveToFirst()
        // Si no existe el artista
        if (!existeComic) return Comic(-1, "", "", "", 0.0, 0)
        // Si existe el artista
        val titulo = cursor.getString(1)
        val autorComic = cursor.getString(2)
        val generoComic = cursor.getString(4)
        val precioComic = cursor.getDouble(4)
        val publicacion = cursor.getInt(4)

        cursor.close()
        baseDatosLectura.close()
        return Comic(id, titulo, autorComic, generoComic, precioComic, publicacion)
    }

    fun consultarComicsPorProductoras(idProductora: Int): ArrayList<Comic> {
        val scriptConsultarComic = """
            SELECT * FROM COMICS WHERE idComic = ?
        """.trimIndent()
        val baseDatosLectura = readableDatabase
        val parametrosConsulta = arrayOf(idProductora.toString())
        val resultadoConsulta = baseDatosLectura.rawQuery(
            scriptConsultarComic,
            parametrosConsulta
        )
        val existeComic = resultadoConsulta.moveToFirst()
        val arregloComic = arrayListOf<Comic>()
        if (existeComic) {
            do {
                val id = resultadoConsulta.getInt(0) // Columna indice 0 -> ID
                val titulo = resultadoConsulta.getString(1) // Columna indice 1 -> NOMBRE
                val autorComic = resultadoConsulta.getString(2) // Columna indice 2 -> ID ARTISTA
                val generoComic = resultadoConsulta.getString(3) // Columna indice 3 -> DURACION
                val precioComic = resultadoConsulta.getDouble(4) // Columna indice 4 -> ALBUM
                val fundacion = resultadoConsulta.getInt(5) // Columna indice 5 -> GENERO

                val comic = Comic(id, titulo, autorComic, generoComic, precioComic, fundacion)
                arregloComic.add(comic)
            } while (resultadoConsulta.moveToNext())
        }
        resultadoConsulta.close()
        baseDatosLectura.close()
        return arregloComic
    }

    fun asignarComicsAProductoras(idArtista: Int?, idCancion: Int): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("idProductora", idArtista)
        //where id...
        val parametrosConsultaUpdate = arrayOf(idCancion.toString())
        val resultadoActualizacion = conexionEscritura
            .update(
                "COMICS", // nombre tabla
                valoresAActualizar,
                "id=?", // claúsula WHERE
                parametrosConsultaUpdate
            )
        conexionEscritura.close()
        return if (resultadoActualizacion == -1) false else true
    }

    fun numeroComic(idProductora: Int): Int {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT COUNT(*) FROM COMICS WHERE idArtista = ?
        """.trimIndent()
        val parametrosConsulta = arrayOf(idProductora.toString())
        val cursor = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            parametrosConsulta
        )
        val existeComic = cursor.moveToFirst()
        // Si no existe el artista
        if (!existeComic) return 0
        // Si existe el artista
        val numeroComics = cursor.getInt(0)
        cursor.close()
        baseDatosLectura.close()
        return numeroComics
    }
}