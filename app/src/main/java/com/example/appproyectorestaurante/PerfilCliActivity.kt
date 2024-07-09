package com.example.appproyectorestaurante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

enum class ProviderType{
    BASIC
}

class PerfilCliActivity : AppCompatActivity() {
    private lateinit var btnRegistrarPedidoCli:Button
    private lateinit var btnCerrarSesionCli:Button
    private lateinit var tvEmailCli:TextView
    private lateinit var tvProvider:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.perfil_clie_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnRegistrarPedidoCli=findViewById(R.id.btnRegistrarPedidoCli)
        btnCerrarSesionCli=findViewById(R.id.btnCerrarSesionCli)
        tvEmailCli=findViewById(R.id.tvEmailCli)
        tvProvider=findViewById(R.id.tvProvider)

        btnRegistrarPedidoCli.setOnClickListener { registrarPedido() }
        btnCerrarSesionCli.setOnClickListener { cerrarSesion() }

        val bundle= intent.extras
        val email = bundle?.getString("email")
        val provider= bundle?.getString("provider")

        datosSesion(email ?:"",provider ?:"")

    }
    fun registrarPedido(){
        var intent=Intent(this,ListadoProductoCliActivity::class.java)
        startActivity(intent)
    }

    fun datosSesion(email:String, provider:String){
        tvEmailCli.text = email
        tvProvider.text = provider
    }

    fun cerrarSesion(){
        FirebaseAuth.getInstance().signOut()
        onBackPressedDispatcher.onBackPressed()
    }
}