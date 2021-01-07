package dao

import modelo.Genero
import java.io.File
import java.io.FileWriter
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class GeneroDAOImpl: GeneroDAO {

    val ruta = "./src/Genero.txt"

    override fun crearGenero() {

        var nuevoGenero = Genero()
        val arrayGenero = cargarGeneros()

        println("Introduce el id del género: ")
        nuevoGenero.idGenero = readLine()!!.toInt()

        println("Introduce el nombre del género: ")
        nuevoGenero.nombreGenero = readLine()!!.toString()

        println("Introduce la fecha de origen del género: ")
        val fechaGenString = readLine()!!.toString()
        nuevoGenero.fechaOrigen = LocalDate.parse(fechaGenString, DateTimeFormatter.ISO_DATE)

        println("Introduce el número de subgéneros del género: ")
        nuevoGenero.numSubgeneros = readLine()!!.toInt()

        println("Introduce las ganancias del género: ")
        nuevoGenero.gananciasGenero = readLine()!!.toDouble()

        println("Indica si el género está activo en la industria (true/false): ")
        nuevoGenero.activo = readLine()!!.toBoolean()

        arrayGenero.add(nuevoGenero)

        escribirGeneros(arrayGenero)

    }

    override fun leerGeneroPorId(id: Int) {
        val arrayGenero = cargarGeneros()
        println(arrayGenero.find{ genero -> genero.idGenero == id })
    }

    override fun listarGeneros() {
        println(" Id | Nombre |  Fecha  | Subgéneros | Ganancias | Activo ")
        val arrayGenero = cargarGeneros()

        arrayGenero.forEach {
            print(it)
        }
    }

    override fun cargarGeneros(): ArrayList<Genero> {
        var arrayGenero = ArrayList<Genero>()

        File(ruta).forEachLine {

            var genero = Genero()
            val linea: List<String> = it.split("|")

            genero.idGenero = linea[0]?.toInt()
            genero.nombreGenero = linea[1]
            genero.fechaOrigen = LocalDate.parse(linea[2], DateTimeFormatter.ISO_DATE)
            genero.numSubgeneros = linea[3]?.toInt()
            genero.gananciasGenero = linea[4]?.toDouble()
            genero.activo = linea[5]?.toBoolean()

            arrayGenero.add(genero)
        }
        return arrayGenero
    }

    override fun escribirGeneros(arrayGenero: ArrayList<Genero>) {

        try {
            val fileWriter = FileWriter(ruta, false)

            arrayGenero.forEach { fileWriter.write(it.toString()) }
            fileWriter.close()

        }catch (ex: Exception){
            println("Error al escribir los géneros: ${ex}")
        }
    }

    override fun actualizarGenero(id: Int) {
        val arrayGenero = cargarGeneros()
        var generoActualizar = arrayGenero.find{ genero -> genero.idGenero == id }

        println("El género que vas a actualizar es el siguiente: ")
        println(generoActualizar)

        println("Introduce el id del género: ")
        generoActualizar?.idGenero = readLine()?.toInt() as Int

        println("Introduce el nombre del género: ")
        generoActualizar?.nombreGenero = readLine() as String

        println("Introduce la fecha de origen del género: ")
        val fechaGenString = readLine() as String
        generoActualizar?.fechaOrigen = LocalDate.parse(fechaGenString, DateTimeFormatter.ISO_DATE)

        println("Introduce el número de subgéneros del género: ")
        generoActualizar?.numSubgeneros = readLine()?.toInt() as Int

        println("Introduce las ganancias del género: ")
        generoActualizar?.gananciasGenero = readLine()?.toDouble() as Double

        println("Indica si el género está activo en la industria (true/false): ")
        generoActualizar?.activo = readLine()?.toBoolean() as Boolean

        escribirGeneros(arrayGenero)
    }

    override fun eliminarGenero(id: Int) {
        val arrayGenero = cargarGeneros()
        var generoEliminar = arrayGenero.find{ genero -> genero.idGenero == id }
        arrayGenero.remove(generoEliminar)
        escribirGeneros(arrayGenero)
    }
}