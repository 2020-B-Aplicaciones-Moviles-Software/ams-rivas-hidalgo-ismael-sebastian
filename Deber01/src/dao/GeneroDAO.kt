package dao

import modelo.Genero

interface GeneroDAO {
    fun crearGenero()
    fun leerGeneroPorId(id: Int)
    fun listarGeneros()
    fun cargarGeneros(): ArrayList<Genero>
    fun escribirGeneros(arrayGenero: ArrayList<Genero>)
    fun actualizarGenero(id: Int)
    fun eliminarGenero(id: Int)
}