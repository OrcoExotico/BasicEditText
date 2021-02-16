package com.example.demo.app

import com.example.demo.app.controller.MyController
import javafx.scene.Parent
import javafx.scene.control.Button
import javafx.scene.layout.BorderPane
import tornadofx.View
import tornadofx.action


class ViewMain : View() {
    // TornadoFX delegates
    override val root: BorderPane by fxml()
    val controller: MyController by inject()

    ////Observables
    private val btnNewText: Button by fxid()
    private val btnEditText: Button by fxid()
    private val btnExit: Button by fxid()

    init {

        btnNewText.action {
            replaceWith<ViewNewText>()
        }

        btnEditText.action {
            replaceWith<ViewEditText>()
        }

        btnExit.action {
            close()
        }
    }
}
