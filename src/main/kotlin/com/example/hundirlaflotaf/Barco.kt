package com.example.hundirlaflotaf

class Barco(celdas: Int, orientacion: String,  x: Int = 0,  y: Int = 0) {
    var celdas = celdas
        get() = field;

    var orientacion = orientacion
        get() = field;

    var x = x
        set(value) {
            if(value>0){
                field = value;
            }
        }

    var y = y
        set(value) {
            if(value>0){
                field = value;
            }
        }
}