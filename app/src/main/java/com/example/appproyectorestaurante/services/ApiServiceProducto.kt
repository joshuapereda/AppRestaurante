package com.example.appproyectorestaurante.services

import com.example.appproyectorestaurante.entidad.Producto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiServiceProducto {
    @GET("Producto")
    fun findAll():Call<List<Producto>>
    @POST("Producto")
    fun save(@Body prod:Producto):Call<Producto>
    @GET("Producto/{id}")
    fun findById(@Path("id") id:Int):Call<Producto>
    @PUT("Producto")
    fun update(@Body prod:Producto):Call<Producto>
    @DELETE("Producto/{id}")
    fun deleteById(@Path("id")id: Int):Call<Void>
}