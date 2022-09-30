package com.example.hundirlaflotaf

import javafx.application.Application
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.layout.GridPane
import javafx.scene.layout.StackPane
import javafx.stage.Stage

class RivalController( val stage: Stage) : Application() {

    @FXML
    private lateinit var tablero: GridPane

    private lateinit var arr: Array<IntArray>
    private var listaBarcos: MutableList<Barco> = mutableListOf<Barco>(Barco(6,"V"), Barco(4,"V"),Barco(4,"V"),Barco(3,"V"),Barco(3,"V"),Barco(2,"V"),Barco(2,"V"))
    private val listaOrientiacion: List<String> = listOf<String>("V", "H")
    lateinit var controladorTablero: TableroController
    private var ultimoDisparo: String = ""
    var veces: Int = 0

    fun inicializar() {
        arr = Array(11) { IntArray(11) }
        generarBarcos()
    }

    fun initialize(){
        if(veces == 0){
            inicializar()
            veces++
        }
        var cell: StackPane

        for (i in 0 until 11) {
            for (j in 0 until 11) {
                cell = StackPane()
                tablero.add(cell, i, j)
            }
        }
        anadirTocado()
        imprimirGridPane()
    }

    fun controlador(){
        if(comprobarVictoria()) {
            cambiarVistaFinal("Jugador")
        }else{
            cambiarVista()
        }
    }

    fun imprimirGridPane(){
        var cell: Node?
        for (i in 0 until 11){
            for (j in 0 until 11){
                 if (arr[i][j] == 2){
                    cell = getNodeByRowColumnIndex(i,j,tablero)
                    cell?.style="-fx-background-color: red"
                }else if (arr[i][j] == 3){
                    cell = getNodeByRowColumnIndex(i,j,tablero)
                    cell?.style="-fx-background-color: aqua"
                }else if (arr[i][j] == 1){
                    cell = getNodeByRowColumnIndex(i,j,tablero)
                    cell?.style="-fx-background-color: black"
                 }
            }
        }
    }

    fun cambiarVistaFinal(nombre: String){
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("final-view.fxml"))
        val finalController = FinalController(stage)
        fxmlLoader.setController(finalController)
        finalController.nombreGanador = nombre
        val scene = Scene(fxmlLoader.load(), 680.0, 600.0)

        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }

    fun cambiarVista(){
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("jugador-view.fxml"))
        val jugadorController = JugadorController(stage)
        fxmlLoader.setController(jugadorController)
        jugadorController.obtenerControladorRival(this)
        jugadorController.obtenerControladorTablero(controladorTablero)
        val scene = Scene(fxmlLoader.load(), 680.0, 600.0)

        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }

    fun comprobarVictoria(): Boolean{
        for(i in 0 until arr.size){
            if(arr[i].contains(1)){
                return false
            }
        }
        return true
    }


    private fun anadirTocado() {
        var cell: Node?
        for (i in 0 until 11) {
            for (j in 0 until 11) {
                cell = getNodeByRowColumnIndex(i, j, tablero)
                cell?.setOnMouseClicked {
                    run {
                        if(arr[i][j] !=2 && arr[i][j] !=3){
                            val stackP = getNodeByRowColumnIndex(i,j,tablero)
                            if(arr[i][j] != 0){
                                stackP?.style = "-fx-background-color: red"
                                ultimoDisparo = "rojo"
                                arr[i][j] = 2
                                controlador()
                            }else{
                                stackP?.style = "-fx-background-color: aqua"
                                ultimoDisparo = "azul"
                                arr[i][j] = 3
                                controlador()
                            }
                        }
                    }
                }
            }
        }
    }

    fun getNodeByRowColumnIndex(row: Int, column: Int, gridPane: GridPane): Node? {
        var result: Node? = null
        val childrens = gridPane.children
        for (node in childrens) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                result = node
                break
            }
        }
        return result
    }

    fun obtenerControladorTablero(controlador: TableroController){
        controladorTablero = controlador
    }

    private fun generarBarcos(){
        while (listaBarcos.size!=0) {
            val listaBarcosCopia = listaBarcos.toMutableList()
            for (i in 0 until listaBarcos.size) {
                listaBarcos[i].orientacion = listaOrientiacion[(0..1).random()]
                listaBarcos[i].x = (0..10).random()
                listaBarcos[i].y = (0..10).random()

                if (comprobarEspacio(listaBarcos[i])) {
                    guardarBarcoTablero(listaBarcos[i])
                    listaBarcosCopia.remove(listaBarcos[i])
                }
            }
            listaBarcos = listaBarcosCopia.toMutableList()
        }
    }

    private fun guardarBarcoTablero(barco: Barco){
        if(barco.orientacion.equals("V")){
            for(i in barco.x ..  barco.x + barco.celdas){
                arr[i][barco.y] = 1
            }
        }else if(barco.orientacion.equals("H")){
            for(i in barco.y .. barco.y + barco.celdas){
                arr[barco.x][i] = 1
            }
        }
    }


    private fun comprobarEspacio(barco: Barco) : Boolean{
        if(barco.orientacion.equals("V") ){
            if( barco.x + barco.celdas <= 10) {
                for (i in barco.x..barco.x + barco.celdas) {
                    if (arr[i][barco.y] != 0) {
                        return false
                    }
                }
            }
            else{
                return false
            }
        }else if (barco.orientacion.equals("H")){
            if(barco.y + barco.celdas <= 10) {
                for (i in barco.y..barco.y + barco.celdas) {
                    if (arr[barco.x][i] != 0) {
                        return false
                    }
                }
            }else{
                return false
            }
        }

        return  true
    }

    override fun start(primaryStage: Stage?) {
        println("No funciona")
    }
}