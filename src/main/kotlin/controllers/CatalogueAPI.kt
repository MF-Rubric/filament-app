package controllers
import models.Catalogue
import persistence.Serializer
import utils. Utility
import java.util.ArrayList

class CatalogueAPI(serializerType: Serializer) {
    private var serializer: Serializer = serializerType
    private var catalogues = ArrayList<Catalogue>()

    private var lastId = 0
    private fun getId() = lastId++

    fun addCatalogue(catalogueId: Catalogue): Boolean {
        catalogueId.catalogueId = getId()
        return catalogues.add(catalogueId)
    }
    fun deleteCatalogue(id: Int) = catalogues.removeIf { catalogue -> catalogue.catalogueId == id }

    fun updateCatalogue(id: Int, catalogue: Catalogue?): Boolean {

        val foundCatalogue = findCatalogue(id)

        if (foundCatalogue != null && catalogue != null) {
            foundCatalogue.catalogueBrand = catalogue.catalogueBrand
            foundCatalogue.catalogueComponent = catalogue.catalogueComponent
            foundCatalogue.cataloguePrintTemp = catalogue.cataloguePrintTemp
            foundCatalogue.catalogueBedTemp = catalogue.catalogueBedTemp
            return true
        }
        // if the catalogue was not found, return false, indicating that the update was not successful
        return false
    }

    fun listAllInfo() =
        if (catalogues.isEmpty()) "no info stored"
        else  Utility.formatListString(catalogues)

    fun numberOfCatalogues():Int = catalogues.size
    fun findCatalogue(catalogueId : Int) =  catalogues.find{ filament -> filament.catalogueId == catalogueId }
}