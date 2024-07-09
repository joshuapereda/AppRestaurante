package com.example.appproyectorestaurante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OpcionesAdmiActivity : AppCompatActivity() {
    private lateinit var btnAdm:Button
    private lateinit var btnMozo:Button
    private lateinit var btnCocina:Button
    private lateinit var btnCerrarSesion:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.opciones_admi_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnAdm=findViewById(R.id.btnAdm)
        btnMozo=findViewById(R.id.btnMozo)
        btnCocina=findViewById(R.id.btnCocina)
        btnCerrarSesion=findViewById(R.id.btnCerrarSesion)

        btnAdm.setOnClickListener { irAdmi() }
        btnMozo.setOnClickListener { irMozo() }
        btnCocina.setOnClickListener { irCocina() }
        btnCerrarSesion.setOnClickListener { cerrarSesion() }

    }
    fun irAdmi(){
        var intent=Intent(this,MenuActivity::class.java)
        startActivity(intent)
    }
    fun irMozo(){
        var intent=Intent(this,ListadoPedidoActivity::class.java)
        startActivity(intent)
    }
    fun irCocina(){
        var intent=Intent(this, ListadoCocina::class.java)
        startActivity(intent)
    }
    fun cerrarSesion(){
        var intent=Intent(this, AdmCliActivity::class.java)
        startActivity(intent)
    }
}