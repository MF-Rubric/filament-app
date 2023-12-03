package models
import utils.Utilities

data class Catalogue(
    var catalogueId: Int= 0, // Unique identifier for the catalogue
    var catalogueBrand: String, // Brand of the filament
    var catalogueComponent: String, // Type of material used to make the filament
    var cataloguePrintTemp: String, // The optimal printing temperature for filament
    var catalogueBedTemp: String, // The optimal bed printing temperature for filament

)