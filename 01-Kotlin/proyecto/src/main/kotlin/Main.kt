import java.util.*

fun main (){
    //Tipo de variables
    //Inmutables (No se reasignan "=")

    val inmutable: String = "Jorge";

    //Mutables (Se puede reasignar)
    var mutable: String = "Vicente";
    mutable = "Jorge";


    //Variables primitivas

    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    //Clase de java para las fechas
    val fechaNacimiento: Date = Date()

    //Dentro del lenguaje de Kotlin no hay Switch
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") ->{
            println("Casado")
        }
        "S" ->{
            println("Soltero")
        }
        else ->{
            println("No sabemos")
        }
    }

    //If- Else en una sola linea

    val coqueto = if(estadoCivilWhen=="S")"Si" else "No"


    //Utilizar funciones

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00)
    calcularSueldo(10.00, 12.00, 20.00)
    calcularSueldo(sueldo =10.00, tasa = 12.00, bonoEspecial = 20.00) // Parametros nombrados
    calcularSueldo(10.00, bonoEspecial = 20.00) // Named Parameters
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00) //Parametros nombrados

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null,null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    //Arreglos

    //Tipos de arrglos
    //Arreglo Estatico
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)


    //Arreglos Dinamicos

    val arregloDinamicos: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )

    println(arregloDinamicos)
    arregloDinamicos.add(11)
    arregloDinamicos.add(12)
    println(arregloDinamicos)

    //FOR EACH --> Unit
    //Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamicos.forEach{
        valorActual: Int ->
        println("Valor actual: ${valorActual}")
    }

    //it (en ingles eso) significa el elemento iterado

    arregloDinamicos.forEach { println(it) }

    arregloEstatico.forEachIndexed{ indice: Int, valorActual: Int ->
        println("Valor ${valorActual} Indice ${indice}")
    }

    println(respuestaForEach)

    //MAP -> Muta el arreglo (Cambia el arreglo)
    //1) Enviamos el nuevo valor de la iteracion
    //2) Nos devuelve  un nuevo **arreglo** con los valores modificados

    val respuestaMap: List<Double> = arregloDinamicos.map {
        valorActual: Int ->
        return@map valorActual.toDouble() +100.00
    }

    println(respuestaMap)
    val respuestaMapDos = arregloDinamicos.map{it + 15}

    //Filter --> Filtrar el arreglo
    //1) Devolver una expresion(True o False)
    //2) Nuevo arreglo filtrado

    val respuestaFilter: List <Int> = arregloDinamicos.filter {
        valorActual: Int ->
        //Expresion de Condicion
        val mayoresACinco: Boolean = valorActual > 5
        return@filter mayoresACinco
    }

    val respuestaFilterDos = arregloDinamicos.filter { it <=5}
    println(respuestaFilter)
    println(respuestaFilterDos)


    /**
     * OR AND
     * OR -> ANY (Alguno cumple?)
     * AND -> ALL (Todos cumplen?)
     */

    val respuestaAny: Boolean = arregloDinamicos.any{ valorActual: Int ->
        return@any (valorActual > 5)
    }

    println(respuestaAny) //true

    val respuestaAll: Boolean =arregloDinamicos.all{valorActual: Int ->
        return@all (valorActual > 5)
    }

    println(respuestaAll) //false

    /*REDUCE -> Valor acumulado
    Valor acumulado = 0 (Siempre 0 en el lenguaje Kotlin)
    [1,2,3,4,5] -> Sumeme todos los valores del arreglo
    valorEmpieza = 0
    ValorIteracion1 = valorEmpienza +1 = 0 +1 = 1 --> Iteracion 1
    ValorIteracion2 = valorIteracion1 +2 = 1 +2 = 3 --> Iteracion 2
    ValorIteracion3 = valorIteracion2 + 3 = 3 +3 = 6 --> Iteracion 3
    ValorIteracion4 = valorIteracion4 + 4 = 6 +4 = 10 --> Iteracion 4
    ValorIteracion5 = valorIteracion5 +5 = 10 +5 = 15 --> Iteracion 5

     */

    val respuestaReduce: Int = arregloDinamicos.reduce{ //acumulado = 0
        acumulado: Int, valorActual: Int ->
        return@reduce(acumulado + valorActual)
    }

    println(respuestaReduce) //78






}


//Funciones


//void se reemplaza por Unit ---> No se necesita colocar el Unit
fun imprimirNombre(nombre:String):Unit{
    //template string --> Se puede usar variables dentro de un String
    println("Nombre: ${nombre}")
}

fun calcularSueldo(
    //Parametros

    sueldo: Double, //Requerido
    tasa: Double = 12.00, //Opcional (defecto)
    bonoEspecial: Double? = null,
    /*
    Opcion null --> nullable
    (Sirve para reducir nos null pointer exception que tiene JAVA)
    */
):Double{
    //Int --> Int?(nullable)
    //String --> String?(nullable)
    //Date -->Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) + bonoEspecial
    }
}

abstract class NumerosJAVA {
    protected val numeroUno: Int
    protected val numeroDos: Int

    constructor(numeroUno: Int, numeroDos: Int) {
        this.numeroUno = numeroUno
        this.numeroDos = numeroDos

        println("Inicializando")
    }
}


abstract class Numeros( // Constructor PRIMARIO
    /*
    * Ejemplo:
    * uno: Int, // (Parametro (sin modificador de acceso)
    * private var uno: Int, // Propiedad Publica clase numero.uno
    * var uno: Int, //Propiedad de la clase (por defecto es PUBLIC)
    * public var uno: Int,
    * */
    //Propiedad de la clase protected numeros.numeroUno
    protected val numeroUno: Int,
    //Propiedad de la clase protected numeros.numeroDos
    protected val numeroDos: Int,

){
    /*
    * var cedula: String = "" (public es por defecto)
    * private valorCalculado: Int = 0 (private)
    * */
    init {
        this.numeroUno; this.numeroDos; // this es opcional
        numeroUno; numeroDos; //Sin el this
        println("Inicializando")
    }
}



//Clase
class Suma ( //Constructor Primario Suma
    uno: Int, //Parametro
    dos: Int //Parametro
) : Numeros (uno, dos) { //<-- Constructor del padre
    init { //
        this.numeroDos; numeroUno
        this.numeroDos; numeroUno
    }

    constructor( //Segundo Constructor
        uno: Int?, //parametros
        dos: Int //parametros
    ) : this( // llamada constructor primario
        if (uno == null) 0 else uno,
        dos
    ) { // si necesitamos bloque de codigo lo usamos
        numeroUno
    }

    constructor( //tercer constructor
        uno: Int, //parametros
        dos: Int? //parametros
    ) : this( // llamada constructor primario
        uno,
        if (dos == null) 0 else dos,
    ) {
        numeroDos
    }

    constructor( // cuarto constructor
        uno: Int?,
        dos: Int?
    ): this ( // llamada constructor primario
        if (uno ==null) 0 else uno,
        if (dos == null) 0 else uno
    )

    //public por defecto, o usar private o proteted

    public fun sumar(): Int{
        val total = numeroUno + numeroDos

        //Suma. agregarHistorial(total)
        agregarHistorial(total)
        return total
    }

    //Un companion  object por clase
    companion object{
        //Atributo y Metodos "Compartidos"
        //entre instancias
        val pi = 3.14

        fun elevarAlCuadrado(num:Int): Int{
            return num * num
        }

        val historialSumas = arrayListOf<Int>()

        fun  agregarHistorial(valorNuevaSuma: Int){
            historialSumas.add(valorNuevaSuma)
        }
    }
}






