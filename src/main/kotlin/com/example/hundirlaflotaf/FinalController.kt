package com.example.hundirlaflotaf

import javafx.application.Application
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.stage.Stage

class FinalController( val stage: Stage) : Application() {
    @FXML
    private lateinit var lab: Label;

    var nombreGanador: String = "";

    override fun start(primaryStage: Stage?) {
        println("")
    }

    fun initialize(){
        lab.text+= " $nombreGanador!!!"
    }
}