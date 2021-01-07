package modelo

import java.time.LocalDate
import java.util.*

class Genero (
    var idGenero: Int,
    var nombreGenero: String,
    var fechaOrigen: LocalDate,
    var numSubgeneros: Int,
    var gananciasGenero: Double,
    var activo: Boolean
) {
    constructor(): this(0,"", LocalDate.parse("1999-01-01") , 0,0.0, false)

    override fun toString(): String {
        return "${idGenero}|${nombreGenero}|${fechaOrigen}|${numSubgeneros}|${gananciasGenero}|${activo}\n"
    }
}