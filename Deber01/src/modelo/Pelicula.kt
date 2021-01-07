package modelo

import java.time.LocalDate

class Pelicula (
    var idPelicula: Int?,
    var nombrePelicula: String?,
    var fechaEstreno: LocalDate?,
    var gananciasPelicula: Double?,
    var enCartelera: Boolean?,
    var genero: Int?
) {
    constructor(): this(0, "", LocalDate.parse("1999-01-01") , 0.0,false, 0)

    override fun toString(): String {
        return "${idPelicula}|${nombrePelicula}|${fechaEstreno}|${gananciasPelicula}|${enCartelera}|${genero}\n"
    }
}