package com.example.appproyectorestaurante.entidad

class Producto(var id_producto:Int, var nombre:String,
               var precio:Double, var descripcion:String,
               var foto:String, var id_categoria:Int) {
    override fun toString(): String {
        return nombre
    }
}