package com.example.appproyectorestaurante.data

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.appproyectorestaurante.utils.AppConfig

class InitBD : SQLiteOpenHelper(
    AppConfig.CONTEXTO,
    AppConfig.NOMBREBD,
    null,
    AppConfig.VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE tb_categoria (" +
                    "cod INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nom VARCHAR(20)," +
                    "pai VARCHAR(20)," +
                    "des VARCHAR(100)," +
                    "foto VARCHAR(30))"
        )
        db.execSQL("create table tb_pedido(" +
                "cod integer primary key autoincrement,"+
                "nomcli varchar(30),"+
                "nrmesa int,"+
                "producto varchar(30),"+
                "detalle varchar(30),"+
                "precio double,"+
                "foto varchar(100))")
        db.execSQL("insert into tb_pedido values(null,'Joshua',1,'Hamburguesa','Carne con Papas fritas',10.00,'https://i.imgur.com/YLBU47w.jpeg')")
        db.execSQL("insert into tb_pedido values(null,'Nicoll',1,'Aji de Gallina','Ricas y deliciosas',11.00,'https://imgur.com/10o83QB.jpeg')")

        // Insertar un ejemplo de registro en la tabla tb_categoria
        val values = ContentValues().apply {
            put("nom", "Marinos")
            put("pai", "Perú")
            put("des", "A base de productos marinos")
            put("foto", "Sopas")
        }
        db.insert("tb_categoria", null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Manejar actualizaciones de la base de datos (no implementado en este ejemplo)
        // Aquí puedes añadir lógica para manejar cambios en la estructura de la base de datos
    }
}
