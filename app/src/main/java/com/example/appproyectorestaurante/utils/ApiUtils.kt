package com.example.appproyectorestaurante.utils

import com.example.appproyectorestaurante.services.ApiServiceLoginApp
import com.example.appproyectorestaurante.services.ApiServiceProducto

class ApiUtils {
    companion object{
        val BASE_URL="https://www.appdamiapi.somee.com/api/"

        fun getAPIProducto(): ApiServiceProducto{
            return RetrofitClient.getClient(BASE_URL).create(ApiServiceProducto::class.java)
        }
        fun getAPILoginApp(): ApiServiceLoginApp {
            return RetrofitClient.getClient(BASE_URL).create(ApiServiceLoginApp::class.java)
        }
    }
}