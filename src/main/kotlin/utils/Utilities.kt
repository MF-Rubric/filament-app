package utils

import models.Filament



object Utilities {

    @JvmStatic
    fun formatListString(filamentsToFormat: List<Filament>): String =
        filamentsToFormat
            .joinToString(separator = "\n") { filament ->  "$filament" }


}
