package com.example.appproyectorestaurante.utils


import android.app.Application
import android.content.Context
import com.example.appproyectorestaurante.data.InitBD


class AppConfig : Application() {
    companion object{
        lateinit var CONTEXTO: Context
        lateinit var BD: InitBD
        var NOMBREBD="Proyectoo2.bd"
        var VERSION=1
    }
    override fun onCreate() {
        super.onCreate()
        //instanciar
        CONTEXTO=applicationContext
        BD=InitBD()
    }
}