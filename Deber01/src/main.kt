import dao.GeneroDAO
import dao.GeneroDAOImpl
import dao.PeliculaDAO
import dao.PeliculaDAOImpl
import kotlin.system.exitProcess


fun main() {

    val generoDao: GeneroDAO = GeneroDAOImpl()
    val peliculaDAO: PeliculaDAO = PeliculaDAOImpl()

    while (true){
        menuOpciones()
        println("Seleccione una opción: ")
        var op = readLine()?.toInt() as Int
        when (op) {
            1 -> generoDao.listarGeneros()
            2 -> {
                println("Introduce el identificador del género: ")
                var idBuscar = readLine()?.toInt() as Int
                generoDao.leerGeneroPorId(idBuscar)
            }
            3 -> generoDao.crearGenero()
            4 -> {
                println("Introduce el identificador del género a actualizar: ")
                var idActualizar = readLine()?.toInt() as Int
                generoDao.actualizarGenero(idActualizar)
            }
            5 -> {
                println("Introduce el identificador del género a eliminar: ")
                var idEliminar = readLine()?.toInt() as Int
                generoDao.eliminarGenero(idEliminar)
            }
            6 -> peliculaDAO.listarPeliculas()
            7 -> {
                println("Introduce el identificador de la película: ")
                var idBuscarP = readLine()?.toInt() as Int
                peliculaDAO.leerPeliculaPorId(idBuscarP)
            }
            8 -> peliculaDAO.crearPelicula()
            9 -> {
                println("Introduce el identificador de la película a actualizar: ")
                var idActualizarP = readLine()?.toInt() as Int
                peliculaDAO.actualizarPelicula(idActualizarP)
            }
            10->{
                println("Introduce el identificador de la película a eliminar: ")
                var idEliminarP = readLine()?.toInt() as Int
                peliculaDAO.eliminarPelicula(idEliminarP)
            }
            11->{
                println("Introduce el identificador del género del que deseas listar las películas: ")
                var idGeneroPeliculas = readLine()?.toInt() as Int
                peliculaDAO.listarPeliculasDeGenero(idGeneroPeliculas)
            }
            12->{
                exitProcess(-1)
            }
            else -> { // Note the block
                print("Opción no válida")
            }
        }
    }
}

fun menuOpciones(){
    println("\n------  CRUD en archivos con KOTLIN  ------")
    println("-----------  Género - Película ------------")

    println("Genero")
    println("1: Leer registros\n2: Buscar género por identificador\n3: Insertar género\n4: Actualizar género\n5: Eliminar género")
    println("------------------------------------------------")
    println("Película")
    println("6: Leer registros\n7: Buscar película por identificador\n8: Insertar película\n9: Actualizar película\n10:Eliminar película\n" +
            "11:Listar películas de un género")
    println("------------------------------------------------")
    println("12: Salir")
}
