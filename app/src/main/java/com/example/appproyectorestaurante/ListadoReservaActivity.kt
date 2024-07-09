package com.example.appproyectorestaurante


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class ListadoReservaActivity : AppCompatActivity() {
    private lateinit var btnVolver: Button

    private lateinit var btnAgregar: FloatingActionButton
    private lateinit var rvListado: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.listado_producto_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnVolver = findViewById(R.id.btnVolverR)

        btnAgregar = findViewById(R.id.btnAgregarR)
        rvListado = findViewById(R.id.rvListadoR)
        btnVolver.setOnClickListener { volver() }

    }

    fun volver() {
        var intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}