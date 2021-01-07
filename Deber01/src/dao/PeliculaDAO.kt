package dao

import modelo.Pelicula

interface PeliculaDAO {
    fun crearPelicula()
    fun leerPeliculaPorId(id: Int)
    fun listarPeliculas()
    fun actualizarPelicula(id: Int)
    fun eliminarPelicula(id: Int)
    fun cargarPeliculas(): ArrayList<Pelicula>
    fun escribirPeliculas(arrayPelicula: ArrayList<Pelicula>)
    fun listarPeliculasDeGenero(id: Int)
}