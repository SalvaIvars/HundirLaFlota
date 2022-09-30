package com.example.hundirlaflotaf

import javafx.application.Application
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.RadioButton
import javafx.scene.layout.GridPane
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class TableroController(val stage: Stage) : Application() {
    @FXML
     lateinit var tablero: GridPane
    @FXML
    private lateinit var radioBarco4: RadioButton
    @FXML
    private lateinit var radioBarco6: RadioButton
    @FXML
    private lateinit var radioBarco3: RadioButton
    @FXML
    private lateinit var radioBarco2: RadioButton
    @FXML
    private lateinit var radioGirar: RadioButton

    lateinit var arr: Array<IntArray>
    private var nBarcos4: Int = 5 // 3
    private var nBarcos6: Int = 5 // 1
    private var nBarcos2: Int = 2 // 2
    private var nBarcos3: Int = 5 // 2

    fun initialize(){
        arr = Array(11) { IntArray(11) }
        var cell: StackPane
        for (i in 0 until 11){
            for (j in 0 until 11){
                cell = StackPane()
                tablero.add(cell, i, j)
            }
        }
        escucharCeldasAzules()
        dibujarCeldasBlancas()
        escucharPosicionBarco()
    }

    private  fun restarNumeroBarcos(nCeldas: Int){
        when (nCeldas){
            6 -> nBarcos6--
            4 -> nBarcos4--
            3 -> nBarcos3--
            2 -> nBarcos2--
        }
        comprobarNumeroBarcos()
    }

    private fun comprobarNumeroBarcos(){
       if(nBarcos2 <= 0){
            radioBarco2.isSelected = false
            radioBarco2.isDisable = true
        }
        if(nBarcos3 <= 0){
            radioBarco3.isSelected = false
            radioBarco3.isDisable = true
        }
        if(nBarcos4 <= 0){
            radioBarco4.isSelected = false
            radioBarco4.isDisable = true
        }
        if(nBarcos6 <= 0){
            radioBarco6.isSelected = false
            radioBarco6.isDisable = true
        }
    }

    fun orientacionSeleccionada() : String{
        if(radioGirar.isSelected){
            return "V"
        }
        return "H"
    }

    private fun barcoSeleccionado() : Int{
        if(radioBarco6.isSelected){
            return 6
        }else if(radioBarco4.isSelected){
            return 4
        }else if (radioBarco3.isSelected){
            return 3
        }else if (radioBarco2.isSelected){
            return 2
        }
        return 0
    }

    private fun comprobarEspacio(i: Int, j: Int, tablero: GridPane, nCeldas: Int, orientacion: String) : Boolean{
        var ii: Int = 1
        var jj: Int = 1

        if(orientacion.equals("V")){
            ii = nCeldas
        }else{
            jj = nCeldas
        }

        var cell: Node? = null
        for(a in i until i+ii){
            for(b in j until j+jj){
                cell = obtenerNodeFilaColumna(a,b,tablero)
                if(cell != null){
                    if(orientacion.equals("V")){
                        if(cell.style.equals("-fx-background-color: green") || 11-(i+nCeldas) < 0 || (i+nCeldas) > 11 ){
                            return false
                        }
                    }else {
                        if (cell.style.equals("-fx-background-color: green") || 11 - (j + nCeldas) < 0 || (j + nCeldas) > 11) {
                            return false
                        }
                    }
                }
            }
        }
        return true
    }

    fun cambiarVista(){
         if(nBarcos2 == 0 && nBarcos4 == 0 && nBarcos3 == 0 && nBarcos6 == 0) {
             val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("rival-view.fxml"))
             val rivalController: RivalController = RivalController(stage)
             fxmlLoader.setController(rivalController)
             rivalController.obtenerControladorTablero(this)
             val scene = Scene(fxmlLoader.load(), 680.0, 600.0)

             stage.title = "Atacar!"
             stage.scene = scene
             stage.show()
         }
    }

    private fun guardarBarco(i: Int, j: Int, tablero: GridPane,barco: Barco){
        var ii: Int = 1
        var jj: Int = 1
        if(barco.orientacion.equals("V")){
            ii = barco.celdas
        }else{
            jj = barco.celdas
        }
        var cell: Node? = null
        for(a in i until i+ii){
            for(b in j until j+jj){
                val stackP: Node? = obtenerNodeFilaColumna(a, b, tablero)
                if (stackP != null) {
                    stackP.style = "-fx-background-color: green"
                    arr[b][a] = 1
                }
            }
        }
    }

    private fun escucharPosicionBarco() {
        var cell: Node?
        for (i in 0 until 11) {
            for (j in 0 until 11) {
                cell = obtenerNodeFilaColumna(i, j, tablero)
                cell?.setOnMouseClicked {
                    run {
                        val nCeldas = barcoSeleccionado()
                        if (nCeldas != 0) {
                            if(comprobarEspacio(i,j,tablero,nCeldas, orientacionSeleccionada())){
                                val barco: Barco = Barco(nCeldas,orientacionSeleccionada(),i,j)
                                restarNumeroBarcos(nCeldas)
                                guardarBarco(i,j,tablero,barco)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun dibujarCeldasBlancas(){
        var cell: Node?
        for(i in 0 until 11){
            for (j in 0 until 11){
                cell = obtenerNodeFilaColumna(i,j,tablero)
                cell?.setOnMouseExited {
                    run{
                        val nCeldas = barcoSeleccionado()
                        if (nCeldas != 0) {
                            for(a in i.. i+nCeldas){
                                for(b in j  .. j+nCeldas){
                                    val stackP: Node? = obtenerNodeFilaColumna(a,b,tablero)
                                    if (stackP != null) {
                                        if(stackP.style.equals("-fx-background-color: blue")){
                                            stackP.style = "-fx-background-color: transparent"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun dibujarCeldasAzules(i: Int, j: Int, nCeldas: Int, orientacion: String){
        var ii: Int = 1
        var jj: Int = 1

        if(orientacion.equals("V")){
            ii = nCeldas
        }else{
            jj = nCeldas
        }

        var cell: Node? = null
        for(a in i until i+ii){
            for(b in j until j+jj){
                val stackP: Node? = obtenerNodeFilaColumna(a, b, tablero)
                if (stackP != null) {
                    if(orientacion.equals("V") && i <= 11 - (nCeldas-1)) {
                        stackP.style = "-fx-background-color: blue"
                    }else if (orientacion.equals("H") && j <= 11 - (nCeldas-1)){
                        stackP.style = "-fx-background-color: blue"
                    }
                }
            }
        }
    }

    private fun escucharCeldasAzules(){
        var cell: Node?
        for(i in 0 until 11){
            for (j in 0 until 11){
                cell = obtenerNodeFilaColumna(i,j,tablero)
                cell?.setOnMouseEntered {
                    run {
                        val nCeldas = barcoSeleccionado()
                        var stackP: Node?

                        if(nCeldas != 0 && comprobarEspacio(i,j,tablero,nCeldas,orientacionSeleccionada())){
                            dibujarCeldasAzules(i,j,nCeldas,orientacionSeleccionada())
                        }
                    }
                }
            }
        }
    }

    fun obtenerNodeFilaColumna(fila: Int, columna: Int, gridPane: GridPane): Node? {
        var resultado: Node? = null
        val hijos = gridPane.children
        for (node in hijos) {
            if (GridPane.getRowIndex(node) == fila && GridPane.getColumnIndex(node) == columna) {
                resultado = node
                break
            }
        }
        return resultado
    }

    override fun start(primaryStage: Stage?) {
        println("no funciona")
    }
}