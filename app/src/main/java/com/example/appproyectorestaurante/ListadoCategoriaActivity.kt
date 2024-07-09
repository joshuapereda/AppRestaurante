package com.example.appproyectorestaurante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectorestaurante.adaptador.CategoriaAdaptador
import com.example.appproyectorestaurante.controlador.ArregloCategoria
import com.example.appproyectorestaurante.entidad.Categoria
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class ListadoCategoriaActivity : AppCompatActivity() {
    private lateinit var btnVolver: Button
    private lateinit var edtBuscar: TextInputEditText
    private lateinit var btnBuscar: Button
    private lateinit var btnCerrarSesion:Button
    private lateinit var btnAgregar: FloatingActionButton
    private lateinit var rvListado: RecyclerView

    private var adaptador: CategoriaAdaptador? = null
    private var listaCategorias: ArrayList<Categoria> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listado_categoria_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnVolver = findViewById(R.id.btnVolverC)
        edtBuscar = findViewById(R.id.edtBuscarC) // Inicializar edtBuscar
        btnBuscar = findViewById(R.id.btnBuscarC) // Inicializar btnBuscar
        btnAgregar = findViewById(R.id.btnAgregarC)
        rvListado = findViewById(R.id.rvListadoC)
        btnCerrarSesion=findViewById(R.id.btnCerrarSesion)

        btnVolver.setOnClickListener { volver() }
        btnCerrarSesion.setOnClickListener { cerrarSesion() }
        btnAgregar.setOnClickListener { nuevo() }
        btnBuscar.setOnClickListener { buscar() }

        // Inicializar adaptador con todas las categorías al inicio
        adaptador = CategoriaAdaptador(ArregloCategoria().findAll()) {
            cargarCategorias() // Esta función se llamará cuando se elimine una categoría exitosamente
        }

        rvListado.layoutManager = LinearLayoutManager(this)
        rvListado.adapter = adaptador

        cargarCategorias() // Cargar todas las categorías al inicio
    }

    private fun volver() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
    fun cerrarSesion(){
        var intent=Intent(this, AdmCliActivity::class.java)
        startActivity(intent)
    }

    private fun nuevo() {
        val intent = Intent(this, CategoriaActivity::class.java)
        startActivity(intent)
    }

    private fun buscar() {
        val nombre = edtBuscar.text.toString().trim()
        if (nombre.isEmpty()) {
            cargarCategorias()
        } else {
            val categoriasEncontradas = ArregloCategoria().findByNombre(nombre)
            listaCategorias.clear()
            if (categoriasEncontradas != null) {
                listaCategorias.addAll(categoriasEncontradas)
                adaptador?.notifyDataSetChanged()
            } else {
                showAlert("No se encontraron categorías con el nombre $nombre")
            }
        }
    }

    private fun cargarCategorias() {
        listaCategorias.clear()
        listaCategorias.addAll(ArregloCategoria().findAll())
        adaptador?.notifyDataSetChanged()
    }

    private fun showAlert(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Mensaje")
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .show()
    }
}
