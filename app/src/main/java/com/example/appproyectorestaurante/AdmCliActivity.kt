package com.example.appproyectorestaurante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AdmCliActivity : AppCompatActivity() {
    private lateinit var btnAdm:Button
    private lateinit var btnCli:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.adm_clie_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnAdm=findViewById(R.id.btnAdm)
        btnCli=findViewById(R.id.btnCli)
        btnAdm.setOnClickListener { ingresarAdmLog() }
        btnCli.setOnClickListener { ingresarCliLog() }

    }
    fun ingresarAdmLog(){
        var intent=Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }
    fun ingresarCliLog(){
        val intent=Intent(this,LoginYRegistroCliActivity::class.java)
        startActivity(intent)
    }
}