package controllers

import models.Menu

private var menus = ArrayList<Menu>()
class MenuAPI {

    fun change(menu: Menu): Boolean {
        return menus.add(menu)
    }
}