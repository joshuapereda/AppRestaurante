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
    private lateinit var btnCerraMenu:Button
    private lateinit var btnCerrarSesion:Button
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
        btnCerraMenu=findViewById(R.id.btnCerraMenu)
        btnCerrarSesion=findViewById(R.id.btnCerrarSesion)

        btnIngresarProducto.setOnClickListener { ingrProducto() }
        btnIngresarCategoria.setOnClickListener { ingrCategoria() }
        btnCerraMenu.setOnClickListener { cerraSession() }
        btnCerrarSesion.setOnClickListener { cerrarSesion() }


    }
    fun ingrProducto(){
        var intent=Intent(this,ListadoProductoActivity::class.java)
        startActivity(intent)
    }
    fun ingrCategoria(){
        var intent=Intent(this,ListadoCategoriaActivity::class.java)
        startActivity(intent)
    }
    fun cerraSession(){
        var intent=Intent(this,OpcionesAdmiActivity::class.java)
        startActivity(intent)
    }
    fun cerrarSesion(){
        var intent=Intent(this, AdmCliActivity::class.java)
        startActivity(intent)
    }

}