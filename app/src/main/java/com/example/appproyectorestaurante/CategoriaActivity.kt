package com.example.appproyectorestaurante

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appproyectorestaurante.controlador.ArregloCategoria
import com.example.appproyectorestaurante.entidad.Categoria
import com.google.android.material.textfield.TextInputEditText

class CategoriaActivity : AppCompatActivity() {

    private lateinit var spnNombreCategoria: AutoCompleteTextView
    private lateinit var txtDescripcionCategoria: TextInputEditText
    private lateinit var txtPaisCategoria: TextInputEditText
    private lateinit var btnGrabarCateg: Button
    private lateinit var btnregresarCateg: Button

    private val operadores = arrayOf(
        "Marina", "Criolla", "Pizzas", "Hamburguesas",
        "Entradas", "Brazas", "Chifas", "Alitas", "Sopas"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registro_categoria_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        spnNombreCategoria = findViewById(R.id.spnNombreCategoria)
        txtDescripcionCategoria = findViewById(R.id.txtDescripcionCategoria)
        txtPaisCategoria = findViewById(R.id.txtPaisCategoria)
        btnGrabarCateg = findViewById(R.id.btnGrabarC)
        btnregresarCateg = findViewById(R.id.btnregresarCateg)

        // Configurar el ArrayAdapter para AutoCompleteTextView
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, operadores)
        spnNombreCategoria.setAdapter(adapter)

        btnGrabarCateg.setOnClickListener { grabar() }
        btnregresarCateg.setOnClickListener { cerrar() }
    }

    private fun grabar() {
        val nom = spnNombreCategoria.text.toString()
        val pai = txtPaisCategoria.text.toString()
        val des = txtDescripcionCategoria.text.toString()

        if (nom.isBlank() || pai.isBlank() || des.isBlank()) {
            val campos = mutableListOf<String>()
            if (nom.isBlank()) campos.add("Nombre")
            if (pai.isBlank()) campos.add("País")
            if (des.isBlank()) campos.add("Descripción")

            mostrarAlerta("Por favor registrar los campos:\n${campos.joinToString("\n")}")
            return
        }

        val foto = getImagenIdByOperador(nom)
        val bean = Categoria(0, nom, pai, des, "")
        val salida = ArregloCategoria().save(bean)

        if (salida != -1L) {
            showAlert("Categoría registrada") {
                Handler(mainLooper).postDelayed({
                    val intent = Intent(this, ListadoCategoriaActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 1500)
            }
        } else {
            showAlert("Error en el registro")
        }
    }

    private fun getImagenIdByOperador(operador: String): Int {
        return when (operador) {
            "Marina" -> R.drawable.marina
            "Criolla" -> R.drawable.criolla
            "Pizzas" -> R.drawable.pizza
            "Hamburguesas" -> R.drawable.hamburguesa
            "Entradas" -> R.drawable.entradas
            "Brazas" -> R.drawable.brazass
            "Chifas" -> R.drawable.chifa
            "Alitas" -> R.drawable.alitas
            "Sopas" -> R.drawable.sopas
            else -> 0
        }
    }

    private fun showAlert(mensaje: String, onPositiveClick: () -> Unit = {}) {
        AlertDialog.Builder(this)
            .setTitle("MENSAJE")
            .setMessage(mensaje)
            .setPositiveButton("Aceptar") { dialogInterface, _ ->
                dialogInterface.dismiss()
                onPositiveClick.invoke()
            }
            .setCancelable(false)
            .show()
    }

    private fun mostrarAlerta(mensaje: String) {
        AlertDialog.Builder(this)
            .setTitle("SISTEMA")
            .setMessage(mensaje)
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun cerrar() {
        val intent = Intent(this, ListadoCategoriaActivity::class.java)
        startActivity(intent)
    }
}
