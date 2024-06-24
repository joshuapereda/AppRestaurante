package com.example.appproyectorestaurante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuActivity : AppCompatActivity() {
    private lateinit var btnIngresarProducto:Button
    private lateinit var btnIngresarCategoria:Button
    private lateinit var btnIngresarMesa:Button
    private lateinit var btnCerraMenu:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.menu_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnIngresarProducto=findViewById(R.id.btnIngresarProducto)
        btnIngresarCategoria=findViewById(R.id.btnIngresarCategoria)
        btnIngresarMesa=findViewById(R.id.btnIngresarMesa)
        btnCerraMenu=findViewById(R.id.btnCerraMenu)

        btnCerraMenu.setOnClickListener { cerraSession() }

    }
    fun cerraSession(){
        var intent=Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
}