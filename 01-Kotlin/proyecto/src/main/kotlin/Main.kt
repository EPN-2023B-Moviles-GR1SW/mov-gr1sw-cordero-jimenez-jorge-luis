fun main (){
    println("Hello World")
    add(3,2)
    println(calcularsueldo(200.0,17.0))


    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1, null)

}

fun add(sum1: Int, sum2: Int):Unit{
    println("${sum1} + ${sum2} = ${sum1 + sum2}")
}

fun imprimirNombre(nombre: String): Unit {
// template strings
// "Bienvenido: " + nombre + " "
    println("Nombre : ${nombre}")
}

fun calcularsueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional (defecto)
    bonoEspecial: Double? =null, //Opcion null --> nullable
): Double{
    if(bonoEspecial == null){
        return sueldo*(100/tasa)
    }else{
        bonoEspecial.dec()
        return sueldo *(100/tasa) + bonoEspecial
    }
}

abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int,
        dos: Int
    ) { // Bloque de codigo del constructor
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

/*abstract class Numeros( // Constructor PRIMARIO
// Ejemplo:
// uno: Int, // (Parametro (sin modificador de acceso))
// private var uno: Int, // Propiedad Publica Clase numeros.uno
// var uno: Int, // Propiedad de la clase (por defecto es PUBLIC)
// public var uno: Int,
// Propiedad de La clase protected numeros.numeroUno
    protected val numeroUno: Int,
// Propiedad de la clase protected numeros.numeroDos
    protected val numeroDos: Int,
) {
    // var cedula: string = "" (public es por defecto)
// private valorCalculado: Int = 0 (private)
    init {
        this.numeroUno; this.numeroDos; // this es opcional
        numeroUno; numeroDos; // sin el "this", es lo mismo
        println("Inicializando")
    }
}
*/

abstract class Numeros( // Constructor PRIMARIO
    protected val numeroUno: Int,
    protected val numeroDos: Int,
    //uno: Int
){
    init {
        this.numeroUno; this.numeroDos; // this es opcional
        numeroUno; numeroDos;
        println("Inicializando")
    }
}

class Suma (
    uno: Int,
    dos: Int
) : Numeros (uno, dos) {
    init {
        this.numeroDos; numeroUno
        this.numeroDos; numeroUno
    }

    constructor(
        uno: Int?,
        dos: Int
    ) : this (
        if (uno==null) 0 else uno,
        dos
    ){
        numeroUno
    }

    constructor(
        uno: Int,
        dos: Int?
    ) : this (
        uno,
        if (dos==null) 0 else dos,
    ){
        numeroDos
    }
}

