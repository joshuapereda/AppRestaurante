package com.example.appproyectorestaurante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PanelActivity : AppCompatActivity() {
    private lateinit var cardMesa1:CardView
    private lateinit var cardMesa2:CardView
    private lateinit var cardMesa3:CardView
    private lateinit var cardMesa4:CardView
    private lateinit var cardMesa5:CardView
    private lateinit var cardMesa6:CardView
    private  lateinit var btnCerrar:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.panel_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        cardMesa1=findViewById(R.id.cardMesa1)
        cardMesa2=findViewById(R.id.cardMesa2)
        cardMesa3=findViewById(R.id.cardMesa3)
        cardMesa4=findViewById(R.id.cardMesa4)
        cardMesa5=findViewById(R.id.cardMesa5)
        cardMesa6=findViewById(R.id.cardMesa6)
        btnCerrar=findViewById(R.id.btnCerrar)

        cardMesa1.setOnClickListener { card1() }
        cardMesa2.setOnClickListener { card2() }
        cardMesa3.setOnClickListener { card3() }
        cardMesa4.setOnClickListener { card4() }
        cardMesa5.setOnClickListener { card5() }
        cardMesa6.setOnClickListener { card6() }
        btnCerrar.setOnClickListener { cerrar() }
    }
    fun card1(){
        var intent=Intent(this,RegistroPedidoActivity::class.java)
        startActivity(intent)
    }
    fun card2(){
        var intent=Intent(this,RegistroPedidoActivity::class.java)
        startActivity(intent)
    }
    fun card3(){
        var intent=Intent(this,RegistroPedidoActivity::class.java)
        startActivity(intent)
    }
    fun card4(){
        var intent=Intent(this,RegistroPedidoActivity::class.java)
        startActivity(intent)
    }
    fun card5(){
        var intent=Intent(this,RegistroPedidoActivity::class.java)
        startActivity(intent)
    }
    fun card6(){
        var intent=Intent(this,RegistroPedidoActivity::class.java)
        startActivity(intent)
    }
    fun cerrar(){
        var intent=Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

}