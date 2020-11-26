import java.util.*
import kotlin.collections.ArrayList

fun main(){
    println("Hola mundo")
    
    // Java Int edad = 12;
    var edadProfesor = 31
    
    var sueldoProfesor: Double = 12.34

    //Mutables      /Inmutables
    //Mutables
    var edadCachorro: Int = 0
    edadCachorro = 1
    edadCachorro = 1
    edadCachorro = 3

    //Inmutables
    val numeroCedula = 18181818
    //numeroCedula = 12

    //tipos de variables
    val nombreProfesor: String = " Adrian Eguez"
    val sueldo: Double = 12.2
    val estadoCivil: Char = 'S'

    val fechaNacimiento: Date = Date()

    if(true){
        //Verdadera
    }else{
        //false
    }

    when(sueldo){
        12.2 -> {
            println("sueldo normal")
        }
        -12.2 -> println("sueldo negativo")
        else -> println ("Sueldo no reconocido")
    }

    val sueldoMayorAEstablecido:Int = if(sueldo > 12.2) 500 else 0
    //condicion ? bloque-true : bloque-false

    imprimirNombre("Ismael")

    calcularSueldo(1000.00)

    calcularSueldo(1000.00, 14.00)

    calcularSueldo(1000.00, 12.00, 3)

    //Named parameters
    calcularSueldo(
            calculoEspecial = 3,
            tasa = 12.00,
            sueldo = 1000.00
    )

    //Arreglos estáticos
    val arregloEstatico: Array<Int> = arrayOf(1, 2, 3)
    //no se pueden modificar (add, push, etc)

    //Arreglos dinámicos
    val arregloDinamico: ArrayList<Int> = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)


    //OPERADORES: sirven para los arreglos estáticos y dinámicos
    arregloEstatico.forEach {  }
    arregloDinamico.forEach {  }

    val respuestaForEach:Unit = arregloEstatico
            .forEach {
                valorActualIteracion ->
                println("Valor ${valorActualIteracion}")
            }
    println(respuestaForEach)

    arregloDinamico
            .forEachIndexed() { indice, valorActualIteracion ->
                println("Valor ${valorActualIteracion} Indice: ${indice}")
            }
    println(respuestaForEach)


    //MAP -> Muta el arreglo (Cambia los elementos del arreglo y nos devuelve un nuevo arreglo)
    val respuestaMap: List<Int> = arregloDinamico
            .map {  valorActualIteracion ->
                return@map valorActualIteracion * 10
            }
    println(respuestaMap)

    val respuestaMapDos: List<Int> = arregloDinamico
            .map {  it + 15 }
    println(respuestaMapDos)

    //Filter -> Filtrar el arreglo
    // 1. Devuelve una expresion  t o f
    // 2. Nuevo arreglo filtrado

    val respuestaFilter: List<Int> = arregloDinamico
            .filter {
                valorActualIteracion ->
                val mayoresACinco: Boolean = valorActualIteracion > 5
                return@filter mayoresACinco
            }

    println(respuestaFilter)


    //Any All -> Condition -> Boolean
    // OR <-> AND
    // OR = ANY
    // OR (Falso si todos son falsos)
    // OR (True si alguno es verdadero)
    // AND (Falso si uno es falso)
    // AND (True si todos con true)

    val respuestaAny: Boolean = arregloDinamico
            .any{
                valorActualIteracion ->
                return@any (valorActualIteracion > 5)
            }
    println(respuestaAny)

    val respuestaAll: Boolean = arregloDinamico
            .all {
                valorActualIteracion ->
                return@all valorActualIteracion > 5
            }
    println(respuestaAll)

    //REDUCE
    // 1 Devuelve un acumulado
    // 2 En qué valor inicia

    val  respuestaFilter2:Int = arregloDinamico
            .reduce { //valor inicial es .0
                acumulado, valorActualIteracion ->
                return@reduce acumulado + valorActualIteracion
            }
    println(respuestaFilter2)   //78


    val respuestaReduceFold = arregloDinamico
            .fold(
                    100,
                    {
                        acumulado, valorActualIteracion ->
                        return@fold acumulado - valorActualIteracion
                    }
            )
    println(respuestaReduceFold)   //22

    // arregloMutable.foldRight (empieza desde el final)
    // arregloMutable.reduceRight (empieza desde el final)


    //Operadores
    // forEach -> Unit
    // map -> Arreglo
    // filter -> Arreglo
    // all, any -> Boolean
    // reduce, fold -> Valor

    val vidaActual: Double = arregloDinamico
            .map { it * 2.3 }
            .filter { it > 20 }
            .fold ( 100.00, {acc, i -> acc - i})
            .also{ println(it)}

    println("Valor vida actual ${vidaActual}")

    val ejemploUno = Suma(1,2)
    val ejemploDos = Suma(null,2)
    val ejemploTres = Suma(1,null)
    val ejemploCuatro = Suma(null,null)

    println(ejemploUno.sumar())
    println(ejemploDos.sumar())
    println(ejemploTres.sumar())
    println(ejemploCuatro.sumar())

    println(Suma.historialSumas)


} //fin bloque main

fun  imprimirNombre(nombre: String): Int{
    println("Nombre ${nombre}") // template strings

    return 1
}

fun calcularSueldo(
        sueldo: Double,
        tasa: Double = 12.00,
        calculoEspecial: Int? = null
): Double {
    //String -> String?   / puede ser String o nulo
    if(calculoEspecial == null){
        return sueldo * (100 / tasa)
    }else{
        return sueldo * (100 / tasa) * calculoEspecial
    }
}



abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor( //constructor primario
            uno:Int,
            dos:Int
    ){  //bloque de código del constructor primario
        //this
        numeroUno = uno
        //this
        numeroDos = dos
        println("Hola")
    }
}

abstract class Numeros(
        protected var numeroUno: Int, //cuando escribo código aquí, esto ya se convierte en propiedades de la clase
        protected var numeroDos: Int
) {
    init{
        println("HOLA")
    }
}


class Suma(
        uno: Int,   //parámetros
        dos: Int    //parámetros
): Numeros(uno, dos){
    init {
        //this.uno -> No existe
        //this.dos -> No existe
    }

    constructor( //segundo constructor
            uno: Int?,
            dos: Int
    ) : this(
            if(uno == null) 0 else uno,
            dos
    ){

    }

    constructor( //tercer constructor
            uno: Int,
            dos: Int?
    ) : this(
            uno,
            if(dos == null) 0 else dos
    ){

    }
    constructor( //cuarto constructor
            uno: Int?,
            dos: Int?
    ) : this(
            if(dos == null) 0 else dos,
            if(uno == null) 0 else uno
    ){

    }

    public fun sumar(): Int{
        val total: Int = numeroUno + numeroDos
        Suma.agregarHistorial(total)
        return total
    }

    //Singleton
    companion object{ //métodos y propiedades
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(nuevaSuma: Int){
            this.historialSumas.add(nuevaSuma)
        }
    }
}

class BaseDeDatos(){
    companion object{
        val datos = arrayListOf<Int>()
    }
}