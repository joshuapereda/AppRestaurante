package com.example.appproyectorestaurante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistroPedidoActivity : AppCompatActivity() {
  private  lateinit var btnCerrar:Button
  private  lateinit var  btnRegresarPrincipal:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registra_pedido_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnCerrar=findViewById(R.id.btnCerrar)
        btnRegresarPrincipal=findViewById(R.id.btnRegresarPrincipal)

        btnCerrar.setOnClickListener { cerrar() }
        btnRegresarPrincipal.setOnClickListener { regresarPrincipal() }

    }
    fun cerrar(){
        var intent=Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
    fun regresarPrincipal(){
        var intent=Intent(this,PanelActivity::class.java)
        startActivity(intent)
    }
}