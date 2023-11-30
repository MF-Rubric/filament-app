package utils

class MenuColorManager {
    var colorChoice: String = "bright yellow" // Default color

    fun changeColor(newColor: String) {
        // Implement logic to change and store the new menu color
        colorChoice = newColor
    }

    fun getColoredMenu(menuText: String): String {
        return when (colorChoice) {
            "red" -> "\u001B[31m$menuText\u001B[0m" // Red color
            "blue" -> "\u001B[34m$menuText\u001B[0m" // Blue color
            "green" -> "\u001B[32m$menuText\u001B[0m" // Green color
            "yellow" -> "\u001B[33m$menuText\u001B[0m"  // Yellow color
            "magenta" -> "\u001B[35m$menuText\u001B[0m" // Magenta Color
            "cyan" -> "\u001B[36m$menuText\u001B[0m" // Cyan Color
            "black" -> "\u001B[30m$menuText\u001B[0m" // Black Color
            //bright colors
            "bright red" -> "\u001B[91m$menuText\u001B[0m" // Bright Red Color
            "bright blue" -> "\u001B[94m$menuText\u001B[0m" // Bright Blue Color
            "bright green" -> "\u001B[92m$menuText\u001B[0m" // Bright Green Color
            "bright yellow" -> "\u001B[93m$menuText\u001B[0m" // Bright Yellow Color
            "bright magenta" -> "\u001B[95m$menuText\u001B[0m" // Bright Magenta Color
            "bright cyan" -> "\u001B[96m$menuText\u001B[0m" // Bright Cyan Color
            "grey"-> "\u001B[90m$menuText\u001B[0m" // Grey Color
            "white" -> "\u001B[97m$menuText\u001B[0m" // White color
            else -> menuText // Default color
        }
    }
}