import java.util.*
import kotlin.collections.ArrayList

fun main(){
    println("Hola mundo - Maldigo mi existencia")
    
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

