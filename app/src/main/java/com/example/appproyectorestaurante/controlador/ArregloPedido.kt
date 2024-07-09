package com.example.appproyectorestaurante.controlador

import android.content.ContentValues
import com.example.appproyectorestaurante.entidad.Pedido
import com.example.appproyectorestaurante.utils.AppConfig

class ArregloPedido {
    fun findAll():ArrayList<Pedido>{
        var lista=ArrayList<Pedido>();
        var CN=AppConfig.BD.readableDatabase
        var SQL="select *from tb_pedido"
        var RS=CN.rawQuery(SQL,null)
        while(RS.moveToNext()){
            var bean=Pedido(RS.getInt(0),RS.getString(1),
                RS.getInt(2),RS.getString(3),
                RS.getString(4),RS.getDouble(5),
                RS.getString(6))
            lista.add(bean)
        }
        return lista
    }
    fun save(bean:Pedido):Int{
        var estado=-1
        var CN=AppConfig.BD.writableDatabase
        var conte= ContentValues()
        conte.put("nomcli",bean.nomcli)
        conte.put("nrmesa",bean.nrmesa)
        conte.put("producto",bean.producto)
        conte.put("detalle",bean.detalle)
        conte.put("precio",bean.precio)
        conte.put("foto",bean.foto)
        estado=CN.insert("tb_pedido","cod",conte).toInt()
        return estado
    }
    fun findById(codigo:Int):Pedido{
        lateinit var bean:Pedido
        var CN=AppConfig.BD.readableDatabase
        var SQL="select *from tb_pedido where cod=?"
        var RS=CN.rawQuery(SQL, arrayOf(codigo.toString()))
        if(RS.moveToNext()){
            bean=Pedido(RS.getInt(0),RS.getString(1),
                RS.getInt(2),RS.getString(3),
                RS.getString(4),RS.getDouble(5),
                RS.getString(6))
        }
        return bean
    }
    fun update(bean:Pedido):Int{
        var estado=-1
        var CN=AppConfig.BD.writableDatabase
        var conte=ContentValues()
        conte.put("nomcli",bean.nomcli)
        conte.put("nrmesa",bean.nrmesa)
        conte.put("producto",bean.producto)
        conte.put("detalle",bean.detalle)
        conte.put("precio",bean.precio)
        conte.put("foto",bean.foto)
        estado=CN.update("tb_pedido",conte,"cod=?", arrayOf(bean.idped.toString()))
        return estado
    }
    fun delete(codigo:Int):Int{
        var estado=-1
        var CN=AppConfig.BD.writableDatabase
        estado=CN.delete("tb_pedido","cod=?", arrayOf(codigo.toString()))
        return estado
    }
    fun findByNomCli(nomcli: String): ArrayList<Pedido> {
        var lista = ArrayList<Pedido>()
        var CN = AppConfig.BD.readableDatabase
        var SQL = "SELECT * FROM tb_pedido WHERE nomcli LIKE ?"
        var RS = CN.rawQuery(SQL, arrayOf("%$nomcli%"))
        while (RS.moveToNext()) {
            var bean = Pedido(
                RS.getInt(0), RS.getString(1),
                RS.getInt(2), RS.getString(3),
                RS.getString(4), RS.getDouble(5),
                RS.getString(6)
            )
            lista.add(bean)
        }
        RS.close()
        return lista
    }
    fun findByProducto(producto: String): ArrayList<Pedido> {
        val lista = ArrayList<Pedido>()
        val CN = AppConfig.BD.readableDatabase
        val SQL = "SELECT * FROM tb_pedido WHERE producto LIKE ?"
        val parametros = arrayOf("%$producto%")

        val cursor = CN.rawQuery(SQL, parametros)
        cursor.use {
            while (it.moveToNext()) {
                val idped = it.getInt(it.getColumnIndex("cod"))
                val nomcli = it.getString(it.getColumnIndex("nomcli"))
                val nrmesa = it.getInt(it.getColumnIndex("nrmesa"))
                val producto = it.getString(it.getColumnIndex("producto"))
                val detalle = it.getString(it.getColumnIndex("detalle"))
                val precio = it.getDouble(it.getColumnIndex("precio"))
                val foto = it.getString(it.getColumnIndex("foto"))

                val pedido = Pedido(idped, nomcli, nrmesa, producto, detalle, precio, foto)
                lista.add(pedido)
            }
        }

        return lista
    }

}