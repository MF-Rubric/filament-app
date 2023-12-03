package utils
import models.Catalogue
import models.Filament
import java.util.Scanner



object Utilities {

    @JvmStatic
    fun formatListString(filamentsToFormat: List<Filament>): String =
        filamentsToFormat
            .joinToString(separator = "\n") { filament ->  "$filament" }


}
object Utility {
    @JvmStatic
    fun formatListString(cataloguesToFormat: List<Catalogue>): String =
        cataloguesToFormat
            .joinToString(separator = "\n") { catalogue ->  "$catalogue" }
}