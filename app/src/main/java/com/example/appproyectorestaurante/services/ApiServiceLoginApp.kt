package com.example.appproyectorestaurante.services


import com.example.appproyectorestaurante.entidad.LoginApp
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiServiceLoginApp {
    @Headers("Content-Type: application/json")
    @POST("LoginApp")
    fun login(@Body loginApp:LoginApp) : Call<Void>
}