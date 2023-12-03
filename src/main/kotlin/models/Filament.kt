package models
import utils.Utilities

data class Filament(
    var filamentId: Int= 0, // Unique identifier for the filament
    var filamentBrand: String, // Brand of the filament
    var filamentType: String, // Type of filament (e.g., PLA, ABS)
    var filamentColor: String, // The pigment of the filament
    var filamentQuantity: Int, // Quantity available in stock


)
