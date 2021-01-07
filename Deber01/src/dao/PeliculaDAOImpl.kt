package dao

import modelo.Genero
import modelo.Pelicula
import java.io.File
import java.io.FileWriter
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class PeliculaDAOImpl: PeliculaDAO {

    val ruta = "./src/Pelicula.txt"

    override fun crearPelicula() {
        var nuevaPelicula = Pelicula()

        println("Introduce el id de la película: ")
        nuevaPelicula.idPelicula = readLine()?.toInt() as Int

        println("Introduce el nombre de la película: ")
        nuevaPelicula.nombrePelicula = readLine() as String

        println("Introduce la fecha de estreno de la película: ")
        val fechaEstString = readLine() as String
        nuevaPelicula.fechaEstreno = LocalDate.parse(fechaEstString, DateTimeFormatter.ISO_DATE)

        println("Introduce las ganancias de la película: ")
        nuevaPelicula.gananciasPelicula = readLine()?.toDouble() as Double

        println("Indica si la película está en cartelera (true/false): ")
        nuevaPelicula.enCartelera = readLine()?.toBoolean() as Boolean

        println("Introduce el id del género: ")
        nuevaPelicula.genero = readLine()?.toInt() as Int

        val arrayPelicula = cargarPeliculas()

        arrayPelicula.add(nuevaPelicula)

        escribirPeliculas(arrayPelicula)

    }

    override fun leerPeliculaPorId(id: Int) {
        val arrayPeliculas = cargarPeliculas()
        println(arrayPeliculas.find{ pelicula -> pelicula.idPelicula == id })
    }

    override fun listarPeliculas() {
        println(" Id | Nombre |  Fecha de estreno  | Ganancias | En cartelera | Género ")
        val arrayPeliculas = cargarPeliculas()

        arrayPeliculas.forEach {
            print(it)
        }
    }

    override fun actualizarPelicula(id: Int) {

        val arrayPelicula = cargarPeliculas()
        var peliculaAActualizar = arrayPelicula.find { pelicula -> pelicula.idPelicula == id }

        println("Introduce el id de la película: ")
        peliculaAActualizar?.idPelicula = readLine()?.toInt() as Int

        println("Introduce el nombre de la película: ")
        peliculaAActualizar?.nombrePelicula = readLine() as String

        println("Introduce la fecha de estreno de la película: ")
        val fechaEstString = readLine() as String
        peliculaAActualizar?.fechaEstreno = LocalDate.parse(fechaEstString, DateTimeFormatter.ISO_DATE)

        println("Introduce las ganancias de la película: ")
        peliculaAActualizar?.gananciasPelicula = readLine()?.toDouble() as Double

        println("Indica si la película está en cartelera (true/false): ")
        peliculaAActualizar?.enCartelera = readLine()?.toBoolean() as Boolean

        println("Introduce el id del género: ")
        peliculaAActualizar?.genero = readLine()?.toInt() as Int

        escribirPeliculas(arrayPelicula)
    }

    override fun eliminarPelicula(id: Int) {
        val arrayPeliculas = cargarPeliculas()
        var generoEliminar = arrayPeliculas.find{ pelicula -> pelicula.idPelicula == id }
        arrayPeliculas.remove(generoEliminar)
        escribirPeliculas(arrayPeliculas)
    }

    override fun cargarPeliculas(): ArrayList<Pelicula> {
        val arrayPelicula = ArrayList<Pelicula>()

        File(ruta).forEachLine {

            var pelicula = Pelicula()
            val linea: List<String> = it.split("|")

            pelicula.idPelicula = linea[0]?.toInt()
            pelicula.nombrePelicula = linea[1]
            pelicula.fechaEstreno = LocalDate.parse(linea[2], DateTimeFormatter.ISO_DATE)
            pelicula.gananciasPelicula = linea[3]?.toDouble()
            pelicula.enCartelera = linea[4]?.toBoolean()
            pelicula.genero = linea[5]?.toInt()

            arrayPelicula.add(pelicula)
        }

        return arrayPelicula
    }

    override fun escribirPeliculas(arrayPelicula: ArrayList<Pelicula>) {
        try {
            val fileWriter = FileWriter(ruta, false)

            arrayPelicula.forEach { fileWriter.write(it.toString()) }
            fileWriter.close()

        }catch (ex: Exception){
            println("Error al escribir los géneros: ${ex}")
        }
    }

    override fun listarPeliculasDeGenero(id: Int) {
        val arrayPeliculas = cargarPeliculas()
        arrayPeliculas
                .forEach{
                    if (it.genero == id){
                        print(it)
                }
        }
    }
}