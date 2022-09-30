package com.example.hundirlaflotaf

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("tablero-view.fxml"))
        val tableroController: TableroController = TableroController(stage);
        fxmlLoader.setController(tableroController)

        val scene = Scene(fxmlLoader.load(), 680.0, 600.0)

        stage.title = "Hundir la flota!"
        stage.scene = scene
        stage.show()
    }
}



fun main() {
    Application.launch(HelloApplication::class.java)
}
