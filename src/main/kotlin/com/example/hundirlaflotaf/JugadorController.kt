package com.example.hundirlaflotaf

import javafx.application.Application
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import javafx.scene.layout.StackPane
import javafx.stage.Stage


class JugadorController(val stage: Stage) : Application()  {

    @FXML
    private lateinit var tablero: GridPane;
    @FXML
    private lateinit var lab: Label;

    lateinit var controladorTablero: TableroController;
    lateinit var controladorRival: RivalController;

    fun obtenerControladorTablero(controlador: TableroController){
        controladorTablero = controlador;
    }

    fun obtenerControladorRival(controlador: RivalController){
        controladorRival = controlador;
    }

    fun initialize(){
        disparar();
        inicializarTablero();

    }

    fun inicializarTablero(){
        var cell: StackPane;

        for (i in 0 until 11){
            for (j in 0 until 11){
                cell = StackPane();
                if(controladorTablero.arr[i][j] ==2 ) {
                    cell.style="-fx-background-color: red"
                    tablero.add(cell, i, j)
                }else if (controladorTablero.arr[i][j] == 3){
                    cell.style="-fx-background-color: aqua"
                    tablero.add(cell, i, j);
                }
            }
        }
    }

    fun anadirDisparo(x: Int, y: Int): Boolean{
        if(controladorTablero.arr[x][y] == 1){
            getNodeByRowColumnIndex(x,y,tablero)?.style.equals("-fx-background-color: red");
            controladorTablero.arr[x][y] = 2;
            return true;
        }else if(controladorTablero.arr[x][y] == 0){
            getNodeByRowColumnIndex(x,y,tablero)?.style.equals("-fx-background-color: aqua");
            controladorTablero.arr[x][y] = 3;
            return false;
        }
        return false
    }

    fun controlador(x: Int, y: Int){
        lab.text += " x: $x - y: $y  "

        if(!comprobarVictoria()) {
            if (!anadirDisparo(x, y)) {
                cambiarVista();
            } else {
                disparar();
            }
        }else{
            cambiarVistaFinal("Máquina")
        }
    }

    fun cambiarVistaFinal(nombre: String){
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("final-view.fxml"))
        val finalController: FinalController = FinalController(stage);
        fxmlLoader.setController(finalController)
        finalController.nombreGanador = nombre;
        val scene = Scene(fxmlLoader.load(), 680.0, 600.0)

        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }

    fun disparar() {
        var x: Int = -1;
        var y: Int = -1;
        var condicion = true;
        while (condicion){
            x = (0..10).random();
            y = (0..10).random();
            if(controladorTablero.arr[x][y] != 2 && controladorTablero.arr[x][y] != 3){
                condicion = false;
            }
        }
        controlador(x,y);
    }

    fun comprobarVictoria(): Boolean{
        for(i in 0 until controladorTablero.arr.size){
            if(controladorTablero.arr[i].contains(1)){
                return false
            }
        }
        return true;
    }

    fun cambiarVista(){
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("rival-view.fxml"))
        fxmlLoader.setController(controladorRival)
        val scene = Scene(fxmlLoader.load(), 680.0, 600.0)
        controladorRival.imprimirGridPane();

        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
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

    override fun start(primaryStage: Stage?) {
        println("No funciona")
    }

}