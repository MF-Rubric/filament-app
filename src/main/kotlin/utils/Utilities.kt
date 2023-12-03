package utils
import models.Catalogue
import models.Filament



object Utilities {

    @JvmStatic
    fun formatListString(filamentsToFormat: List<Filament>): String =
        filamentsToFormat
            .joinToString(separator = "\n") { filament ->  "$filament" }

    @JvmStatic
    fun formatListString(cataloguesToFormat: List<Catalogue>): String =
        cataloguesToFormat
            .joinToString(separator = "\n") { catalogue ->  "$catalogue" }
}
