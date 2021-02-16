package com.example.demo.app

import tornadofx.App
import tornadofx.launch

class MyApp: App(ViewMain::class, Styles::class)

fun main(args: Array<String>) {
    launch<MyApp>(args)
}