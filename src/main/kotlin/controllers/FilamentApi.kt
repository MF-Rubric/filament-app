package controllers
import models.Filament
import java.util.ArrayList
import utils.Utilities.formatListString
import persistence.Serializer

class FilamentApi(serializerType: Serializer) {

    private var serializer: Serializer = serializerType
 private var filaments = ArrayList<Filament>()

    private var lastId = 0
    private fun getId() = lastId++

    fun add(filament: Filament): Boolean {
        filament.filamentId = getId()
        return filaments.add(filament)
 }
    fun delete(id:Int) = filaments.removeIf{filament -> filament.filamentId ==id}

    fun update(id: Int, filament: Filament?): Boolean {

        val foundFilament = findFilament(id)

        // if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundFilament != null) && (filament != null)) {
            foundFilament.filamentBrand = filament.filamentBrand
            foundFilament.filamentType = filament.filamentType
            foundFilament.filamentColor = filament.filamentColor
            foundFilament.filamentQuantity = filament.filamentQuantity

            return true
        }


        // if the note was not found, return false, indicating that the update was not successful
        return false
    }



    fun listAllFilaments() =
        if (filaments.isEmpty()) "no filaments stored"
        else formatListString(filaments)

    fun numberOfFilaments():Int = filaments.size

    //searching
    fun findFilament(filamentId : Int) =  filaments.find{ filament -> filament.filamentId == filamentId }

    fun searchFilamentsByBrand(searchString: String) =
        formatListString(
            filaments.filter { filament -> filament.filamentBrand.contains(searchString, ignoreCase = true) }
        )

    fun searchFilamentsByType(searchString: String) =
        formatListString(
            filaments.filter { filament -> filament.filamentType.contains(searchString, ignoreCase = true) }
        )
}