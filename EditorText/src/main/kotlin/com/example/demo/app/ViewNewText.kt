package com.example.demo.app

import com.example.demo.app.controller.MyController
import javafx.animation.PauseTransition
import javafx.event.EventHandler
import javafx.scene.control.*
import javafx.scene.layout.BorderPane
import javafx.scene.paint.Color
import javafx.stage.DirectoryChooser
import javafx.stage.FileChooser
import javafx.util.Duration
import tornadofx.FileChooserMode
import tornadofx.View
import tornadofx.action
import tornadofx.chooseFile
import java.io.File
import java.io.FileWriter
import java.lang.Exception
import javax.swing.JFileChooser
import  javax.swing.*

class ViewNewText : View() {
    // TornadoFX delegates
    override val root: BorderPane by fxml()
    val controller: MyController by inject()

    // Observables
    private val btnBack: Button by fxid()
    private val btnSave: Button by fxid()
    private val btnSaveTo: Button by fxid()
    private val txtFileName: TextField by fxid()
    private val textareaFileContinent: TextArea by fxid()
    private val lblMessages: Label by fxid()

    init {

        //ANIMATION
        val lblClear = PauseTransition(Duration.seconds(3.0))
        lblClear.onFinished = EventHandler {
            lblMessages.text = ""
        }

        txtFileName.isEditable = false
        btnSave.isDisable = true
        textareaFileContinent.isEditable = false

        btnBack.action {
            replaceWith<ViewMain>()
            txtFileName.clear()
            textareaFileContinent.clear()
            btnSave.isDisable = true
            textareaFileContinent.isEditable = false
            lblMessages.text = ""
        }

        btnSaveTo.action {
            txtFileName.clear()
            txtFileName.text = saveTo().toString()
            btnSave.isDisable = false
            textareaFileContinent.isDisable = false
            textareaFileContinent.isEditable = true
        }

        btnSave.action {
            if (txtFileName.text == "null" || txtFileName.text == "") {
                lblMessages.textFill = Color.RED
                lblMessages.text = "ERROR: Name the file !"
                lblClear.play() //Animation
                btnSave.isDisable = true
                textareaFileContinent.isEditable = false
                textareaFileContinent.clear()
                txtFileName.clear()
            } else if (!writeToNewFile(textareaFileContinent.text, txtFileName.text)) {
                lblMessages.text = "File '" + txtFileName.text + "' was created!"
                lblMessages.textFill = Color.GREEN
                lblClear.play() //Animation
                textareaFileContinent.clear()
                txtFileName.clear()
                textareaFileContinent.isDisable = true
                btnSave.isDisable = true
            }
        }
    }

    private fun writeToNewFile(fileText: String, fileName: String): Boolean {

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

    private fun saveTo(): File? {
        val fileChooser = FileChooser()
        fileChooser.title = "Choose where to save your file"
        fileChooser.extensionFilters.addAll(FileChooser.ExtensionFilter("Text", "*.txt"))

        val fileSaved: File?
        fileSaved = fileChooser.showSaveDialog(currentWindow)

        if (fileSaved != null) {
            return fileSaved
        } else {
            txtFileName.clear()
            return null
        }
    }
}