package com.example.demo.app.controller

import tornadofx.Controller

class MyController : Controller() {
    //    val values: ObservableList<String> = FXCollections.observableArrayList("Alpha","Beta","Gamma","Delta")
    fun print(msg: String) {
        println(msg)
    }
}