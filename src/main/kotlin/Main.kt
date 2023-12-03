// Importing necessary classes and packages
import controllers.MenuAPI
import controllers.FilamentApi
import controllers.CatalogueAPI
import models.Catalogue
import models.Filament
import models.Menu
import persistence.JSONSerializer
import utils.MenuColorManager
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ScannerInput
import java.io.File
import kotlin.system.exitProcess

// Creating instances of the API classes and other utilities
private val menuAPI = MenuAPI()
//private val filamentApi = FilamentApi(XMLSerializer(File("filament.xml")))
private val filamentApi = FilamentApi(JSONSerializer(File("filaments.json")))
private val catalogueAPI = CatalogueAPI(JSONSerializer(File("catalogues.json")))

val menuColorManager = MenuColorManager()
// Main function to start the application
fun main() = runMenu()
// Function to run the main menu in a loop
fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addFilament()
            2 -> listFilaments()
            3 -> updateFilament()
            4 -> deleteFilament()
            5 -> searchFilamentBrand()
            6 -> searchFilamentType()
            7 -> addCatalogue()
            8 -> listInfo()
            9 -> updateCatalogue()
            10 -> deleteCatalogue()
            //11 -> sortCatalogueInfoBy()
            12 -> changeMenu()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}


// Function to display the main menu and get the user's choice
fun mainMenu(): Int { return readNextInt(menuColorManager.getColoredMenu(
    """ 
> ---------------------------------------------------------------------------------
| / __ \     _____ _ _                            _   _   _       _        / __ \ |
|/ /  \ \   |  ___(_) | __ _ _ __ ___   ___ _ __ | |_| | | |_   _| |__    / /  \ \|
|\ \__/ /   | |_  | | |/ _` | '_ ` _ \ / _ \ '_ \| __| |_| | | | | '_ \   \ \__/ /|
| \____/    |  _| | | | (_| | | | | | |  __/ | | | |_|  _  | |_| | |_) |   \____/ |
| /    \    |_|   |_|_|\__,_|_| |_| |_|\___|_| |_|\__|_| |_|\__,_|_.__/    /    \ |
|---------------------------------------------------------------------------------| 
|                               FILAMENT CATALOG APP                              |
|---------------------------------------------------------------------------------|  
| FILAMENT MENU                                                                   |
|   1) Add a Filament                                                             |
|   2) List Filaments                                                             |
|   3) Update Filament                                                            |
|   4) Delete Filament                                                            |
|   5) Search Filament by Brand                                                   |
|   6) Search Filament by Type                                                    |
|---------------------------------------------------------------------------------|
| CATALOG MENU                                                                    |
|   7) Add Catalogue Info                                                         |
|   8) List Info                                                                  |           |
|   9) Update Catalogue Info                                                      | 
|  10) Delete Catalogue Info                                                      |
|  11) Sort Catalogue Info by                                                     |
----------------------------------------------------------------------------------|
| SETTINGS                                                                        |
|   12) Change Menu Color                                                         |
|    0) Exit App                                                                  |
-----------------------------------------------------------------------------------
> ==>> 
 """.trimMargin(">")
))
}
// Function to add a filament
fun addFilament() {
    // Getting details from the user
    val filamentBrand = readNextLine("Enter the brand of filament: ")
    val filamentType = readNextLine("Enter the type of filament (PLA,TPU,PETG,ABS, etc): ")
    val filamentColor = readNextLine("Enter the color of filament: ")
    val filamentQuantity = readNextInt("Enter the quantity of filament: ")
    // Adding the filament using FilamentApi
    val isAdded = filamentApi.add(Filament(filamentBrand = filamentBrand, filamentType = filamentType, filamentColor = filamentColor, filamentQuantity = filamentQuantity))
    // Displaying success or failure message
    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}
// Function to list all filaments
fun listFilaments() {
    println(filamentApi.listAllFilaments())
}
// Function to update a filament
fun updateFilament(){
    // Displaying the list of filaments
    listFilaments()

    // Checking if filaments exist
    if (filamentApi.numberOfFilaments() > 0) {

        // Asking the user to choose the filament to update
        val id = readNextInt("Enter the id of the filament to update: ")

// Checking if the chosen filament exists
        if (filamentApi.findFilament(id) != null) {
            // Getting new details from the user
            val filamentBrand = readNextLine("Enter the brand of filament: ")
            val filamentType = readNextLine("Enter the type of filament: ")
            val filamentColor = readNextLine("Enter the color of filament: ")
            val filamentQuantity = readNextInt("Enter the quantity of filament: ")

            // Updating the filament using FilamentApi
            if (filamentApi.update(id, Filament(0, filamentBrand, filamentType, filamentColor, filamentQuantity))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("Filament not found with ID: $id")
        }
    }
}

// Function to delete a filament
fun deleteFilament() {
    // Displaying the list of filaments
    listFilaments()
    // Checking if filaments exist
    if (filamentApi.numberOfFilaments() > 0) {
        // Asking the user to choose the filament to delete
        val id = readNextInt("Enter the id of the note to delete: ")

        // Displaying success or failure message
        val filamentToDelete = filamentApi.delete(id)
        if (filamentToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}
// Function to search filaments by brand
    fun searchFilamentBrand() {
    // Getting the brand to search for
        val searchBrand = readNextLine("Enter the description to search by: ")
    // Searching for filaments by brand using FilamentApi
        val searchResults = filamentApi.searchFilamentsByBrand(searchBrand)
    // Displaying search results
        if (searchResults.isEmpty()) {
            println("No filaments found")
        } else {
            println(searchResults)
        }
    }

// Function to search filaments by type
    fun searchFilamentType() {
    // Getting the type to search for
        val searchType = readNextLine("Enter the description to search by: ")
    // Searching for filaments by type using FilamentApi
        val searchResults = filamentApi.searchFilamentsByType(searchType)
    // Displaying search results
        if (searchResults.isEmpty()) {
            println("No filaments found")
        } else {
            println(searchResults)
        }
    }
// Function to add a catalogue
fun addCatalogue() {
    // Getting details from the user
    var catalogueBrand: String = readNextLine("Enter the brand of filament: ")
    val catalogueComponent = readNextLine("Enter the Type of material used to make the filament): ")
    val cataloguePrintTemp = readNextLine("The optimal printing temperature for filament: ")
    val catalogueBedTemp = readNextLine("The optimal bed printing temperature for filament: ")
    // Adding the catalogue using CatalogueAPI
    val isAdded = catalogueAPI.addCatalogue(Catalogue(catalogueBrand = catalogueBrand, catalogueComponent = catalogueComponent, cataloguePrintTemp = cataloguePrintTemp, catalogueBedTemp = catalogueBedTemp))

// Displaying success or failure message
    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

// Function to list all catalogues
fun listInfo() {
    println(catalogueAPI.listAllInfo())
}
// Function to delete a catalogue
fun deleteCatalogue(){
    // Displaying the list of catalogues
    listInfo()
    // Checking if catalogues exist
    if (catalogueAPI.numberOfCatalogues() > 0) {
        // Asking the user to choose the catalogue to delete
        val id = readNextInt("Enter the id of the catalogue to delete: ")
        // Deleting the catalogue using CatalogueAPI
        val filamentToDelete = catalogueAPI.deleteCatalogue(id)
        // Displaying success or failure message
        if (filamentToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}
// Function to update a catalogue
fun updateCatalogue(){
    // Displaying the list of catalogues
    listInfo()
    // Checking if catalogues exist
    if (catalogueAPI.numberOfCatalogues() > 0) {
// Asking the user to choose the catalogue to update
        val id = readNextInt("Enter the id of the filament to update: ")
        // Checking if the chosen catalogue exists
        if (catalogueAPI.findCatalogue(id) != null) {
            // Getting new details from the user
            val catalogueBrand = readNextLine("Enter the brand of filament: ")
            val catalogueComponent = readNextLine("Enter the Type of material used to make the filament:")
            val cataloguePrintTemp = readNextLine("Enter the the optimal printing temperature for filament: ")
            val catalogueBedTemp = readNextLine("Enter the optimal bed printing temperature for filament: ")

            // Updating the catalogue using CatalogueAPI
            if (catalogueAPI.updateCatalogue(id, Catalogue(0, catalogueBrand, catalogueComponent, cataloguePrintTemp, catalogueBedTemp))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("Filament not found with ID: $id")
        }
    }
}
// Function to change the menu color
fun changeMenu() {
        //logger.info { "changeColour() function invoked" }
        val colorChoice = readNextLine("Enter the name of a Color: ")
        menuColorManager.changeColor(colorChoice)
        val isChanged = menuAPI.change(Menu(colorChoice))

        if (isChanged) {
            println("Change Successfully")
        } else {
            println("Change Failed")
        }
    }

    fun exitApp() {
        println("Exiting...bye for now")
        exitProcess(0)
    }
