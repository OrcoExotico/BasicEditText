package com.example.demo.app

import com.example.demo.app.controller.MyController
import javafx.animation.PauseTransition
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.stage.FileChooser
import javafx.util.Duration
import tornadofx.View
import tornadofx.action
import java.awt.SystemColor
import java.io.FileReader
import java.io.FileWriter
import java.lang.Exception
import java.awt.SystemColor.text
import java.awt.SystemColor.text
import java.io.File


class ViewEditText : View() {

    // TornadoFX delegates
    override val root: BorderPane by fxml()
    val controller: MyController by inject()

    // Observables
    private val btnBack: Button by fxid()
    private val btnSearchFile: Button by fxid()
    private val btnSaveChanges: Button by fxid()
    private val btnCancel: Button by fxid()
    private val txtFileName: TextField by fxid()
    private val textareaFileContinent: TextArea by fxid()
    private val lblMessages: Label by fxid()

    init {

        txtFileName.isEditable = false
        btnSaveChanges.isDisable = true
        textareaFileContinent.isEditable = false

        //ANIMATION
        val lblClear = PauseTransition(Duration.seconds(3.0))
        lblClear.onFinished = EventHandler {
            lblMessages.text = ""
        }

        btnBack.action {
            replaceWith<ViewMain>()
            txtFileName.clear()
            textareaFileContinent.clear()
        }

        btnCancel.action {
            txtFileName.clear()
            textareaFileContinent.isEditable = false
            textareaFileContinent.clear()
            lblMessages.text = ""
        }

        btnSearchFile.action {
            txtFileName.text = openFile().toString()
            btnSaveChanges.isDisable = false
            textareaFileContinent.isEditable = true

        }

        btnSaveChanges.action {
            if(txtFileName.text == "null" || txtFileName.text == ""){
                lblMessages.textFill = Color.RED
                lblMessages.text = "ERROR: Name the file !"
                lblClear.play() //Animation
                btnSaveChanges.isDisable = true
                textareaFileContinent.isEditable = false
                textareaFileContinent.clear()
                txtFileName.clear()
            }
            else if (!saveEditedText(textareaFileContinent.text, txtFileName.text)) {
                lblMessages.text = "File '" + txtFileName.text + "' saved!"
                lblMessages.textFill = Color.GREEN
                lblClear.play() //Animation
                textareaFileContinent.isEditable = false
                btnSaveChanges.isDisable = true
                textareaFileContinent.clear()
                txtFileName.clear()
            }
        }
    }

    private fun readFile(fileName: String): Boolean {

        var texto: String = ""

        try {
            val fileIn = FileReader(fileName)
            var i: Int

            do {
                i = fileIn.read()
                texto += i.toChar()
            } while (i != -1)

            textareaFileContinent.text = texto

        } catch (ex: Exception) {
            print(ex.message)
            return true
        }
        return false
    }

    private fun openFile(): File? {

        val fileChooser = FileChooser()

        fileChooser.title = "Choose File"
        fileChooser.initialDirectory = File("/users/Arnau")

        fileChooser.extensionFilters.addAll(FileChooser.ExtensionFilter("Text", "*.*"))
        val selectedFile: File? = fileChooser.showOpenDialog(currentWindow)

        readFile(selectedFile.toString())

        return selectedFile
    }

    private fun saveEditedText(fileText: String, fileName: String): Boolean {

        try {
            val fileOut = FileWriter(fileName)
            fileOut.write(fileText)
            fileOut.close()
        } catch (ex: Exception) {
            print(ex.message)
            return true
        }
        return false

    }


}



