package com.example.appproyectorestaurante.controlador

import android.content.ContentValues
import com.example.appproyectorestaurante.data.InitBD
import com.example.appproyectorestaurante.entidad.Categoria
import com.example.appproyectorestaurante.utils.AppConfig

class ArregloCategoria {

    fun findAll(): ArrayList<Categoria> {
        val lista = ArrayList<Categoria>()
        val CN = AppConfig.BD.readableDatabase
        val sql = "SELECT * FROM tb_categoria"
        val RS = CN.rawQuery(sql, null)

        while (RS.moveToNext()) {
            val categoria = Categoria(
                RS.getInt(RS.getColumnIndexOrThrow("cod")),
                RS.getString(RS.getColumnIndexOrThrow("nom")) ?: "",
                RS.getString(RS.getColumnIndexOrThrow("des")) ?: "",
                RS.getString(RS.getColumnIndexOrThrow("pai")) ?: "",
                RS.getString(RS.getColumnIndexOrThrow("foto")) ?: ""
            )
            lista.add(categoria)
        }

        RS.close()
        return lista
    }

    fun save(categoria: Categoria): Long {
        val CN = AppConfig.BD.writableDatabase
        val content = ContentValues().apply {
            put("nom", categoria.nombre)
            put("des", categoria.descripcion)
            put("pai", categoria.pais)
            put("foto", categoria.foto)
        }
        return CN.insert("tb_categoria", null, content)
    }

    fun findById(codigo: Int): Categoria? {
        var categoria: Categoria? = null
        val CN = AppConfig.BD.readableDatabase
        val sql = "SELECT * FROM tb_categoria WHERE cod=?"
        val cursor = CN.rawQuery(sql, arrayOf(codigo.toString()))

        if (cursor.moveToFirst()) {
            categoria = Categoria(
                cursor.getInt(cursor.getColumnIndexOrThrow("cod")),
                cursor.getString(cursor.getColumnIndexOrThrow("nom")) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow("des")) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow("pai")) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow("foto")) ?: ""
            )
        }

        cursor.close()
        return categoria
    }

    fun findByNombre(nombre: String): List<Categoria> {
        val lista = mutableListOf<Categoria>()
        val CN = AppConfig.BD.readableDatabase
        val query = "SELECT * FROM tb_categoria WHERE nom LIKE ?"
        val cursor = CN.rawQuery(query, arrayOf("$nombre%"))

        while (cursor.moveToNext()) {
            val categoria = Categoria(
                cursor.getInt(cursor.getColumnIndexOrThrow("cod")),
                cursor.getString(cursor.getColumnIndexOrThrow("nom")) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow("des")) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow("pai")) ?: "",
                cursor.getString(cursor.getColumnIndexOrThrow("foto")) ?: ""
            )
            lista.add(categoria)
        }

        cursor.close()
        return lista
    }

    fun update(categoria: Categoria): Int {
        val CN = AppConfig.BD.writableDatabase
        val content = ContentValues().apply {
            put("nom", categoria.nombre)
            put("des", categoria.descripcion)
            put("pai", categoria.pais)
            put("foto", categoria.foto)
        }
        return CN.update("tb_categoria", content, "cod=?", arrayOf(categoria.codigo.toString()))
    }

    fun deleteById(codigo: Int): Int {
        val db = InitBD().writableDatabase
        return db.delete("tb_categoria", "cod=?", arrayOf(codigo.toString()))
    }

    fun getAllNombresCategorias(): List<String> {
        val categorias = findAll() // Obtener todas las categorías
        return categorias.map { it.nombre } // Extraer solo los nombres de las categorías
    }

    fun updateCategoria(id: Int, nombre: String, descripcion: String, pais: String): Int {
        val categoriaExistente = findById(id)

        // Verificar si la categoría existe
        if (categoriaExistente != null) {
            val categoriaActualizada =
                Categoria(id, nombre, descripcion, pais, categoriaExistente.foto)
            return update(categoriaActualizada) // Actualizar la categoría en la base de datos
        } else {
            return -1 // Devolver -1 si la categoría no se encontró
        }
    }
}
