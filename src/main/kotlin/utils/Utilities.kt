package utils

import models.Filament



object Utilities {

    // NOTE: JvmStatic annotation means that the methods are static i.e. we can call them over the class
    //      name; we don't have to create an object of Utilities to use them.


    @JvmStatic
    fun formatListString(filamentsToFormat: List<Filament>): String =
        filamentsToFormat
            .joinToString(separator = "\n") { filament ->  "$filament" }


}
