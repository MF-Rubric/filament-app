
import controllers.MenuAPI
import controllers.FilamentApi
import models.Filament
import models.Menu
import persistence.JSONSerializer
import persistence.XMLSerializer
import utils.MenuColorManager
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.ScannerInput
import java.io.File
import kotlin.system.exitProcess


private val menuAPI = MenuAPI()
//private val filamentApi = FilamentApi(XMLSerializer(File("filament.xml")))
private val filamentApi = FilamentApi(JSONSerializer(File("filaments.json")))
val menuColorManager = MenuColorManager()
fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addFilament()
            2 -> listFilaments()
            3 -> updateFilament()
            4 -> deleteFilament()
            5 -> searchFilamentBrand()
            6 -> searchFilamentType()
           // 7 -> AddPrintProperty()
           // 8 -> updateProperty()
           // 9 ->deleteProperty()
            //10 -> sortCatalogueByBrand()
            11 -> changeMenu()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}



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
|   7) Add a Print Property                                                       |
|   8) Update a Print Property                                                    | 
|   9) Delete a Print Property                                                    |
|  10) Sort Catalogue by Brand                                                    |
----------------------------------------------------------------------------------|
| SETTINGS                                                                        |
|   11) Change Menu Color                                                         |
|    0) Exit App                                                                  |
-----------------------------------------------------------------------------------
> ==>> 
 """.trimMargin(">")
))
}
fun addFilament() {
    val filamentBrand = readNextLine("Enter the brand of filament: ")
    val filamentType = readNextLine("Enter the type of filament (PLA,TPU,PETG,ABS, etc): ")
    val filamentColor = readNextLine("Enter the color of filament: ")
    val filamentQuantity = readNextInt("Enter the quantity of filament: ")
    val isAdded = filamentApi.add(Filament(filamentBrand = filamentBrand, filamentType = filamentType, filamentColor = filamentColor, filamentQuantity = filamentQuantity))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}


fun listFilaments() {
    println(filamentApi.listAllFilaments())
}
fun updateFilament(){
    listFilaments()
    if (filamentApi.numberOfFilaments() > 0) {

        val id = readNextInt("Enter the id of the filament to update: ")
        if (filamentApi.findFilament(id) != null) {
            val filamentBrand = readNextLine("Enter the brand of filament: ")
            val filamentType = readNextLine("Enter the type of filament: ")
            val filamentColor = readNextLine("Enter the color of filament: ")
            val filamentQuantity = readNextInt("Enter the quantity of filament: ")

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


fun deleteFilament() {
    listFilaments()
    if (filamentApi.numberOfFilaments() > 0) {
        // only ask the user to choose the filament to delete if filaments exist
        val id = readNextInt("Enter the id of the note to delete: ")
        // pass the index of the note to NoteAPI for deleting and check for success.
        val filamentToDelete = filamentApi.delete(id)
        if (filamentToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}
    fun searchFilamentBrand() {
        val searchBrand = readNextLine("Enter the description to search by: ")
        val searchResults = filamentApi.searchFilamentsByBrand(searchBrand)
        if (searchResults.isEmpty()) {
            println("No filaments found")
        } else {
            println(searchResults)
        }
    }

    fun searchFilamentType() {
        val searchType = readNextLine("Enter the description to search by: ")
        val searchResults = filamentApi.searchFilamentsByType(searchType)
        if (searchResults.isEmpty()) {
            println("No filaments found")
        } else {
            println(searchResults)
        }
    }

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
