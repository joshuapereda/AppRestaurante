package com.example.appproyectorestaurante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.appproyectorestaurante.controlador.ArregloCategoria
import com.example.appproyectorestaurante.entidad.Categoria
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText

class CategoriaActualizarActivity : AppCompatActivity() {

    private lateinit var spnNombreCategoria: MaterialAutoCompleteTextView
    private lateinit var txtDescripcionCategoriaActualizar: TextInputEditText
    private lateinit var txtPaisCategoriaActualizar: TextInputEditText
    private lateinit var btnActualizar: Button
    private lateinit var btnRegresar: Button

    private var categoriaId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registro_editar_categoria_main)

        // Inicialización de vistas
        spnNombreCategoria = findViewById(R.id.spnNombreCategoriaActualizar)
        txtDescripcionCategoriaActualizar = findViewById(R.id.txtDescripcionCategoriaActualizar)
        txtPaisCategoriaActualizar = findViewById(R.id.txtPaisCategoriaActualizar)
        btnActualizar = findViewById(R.id.btnGrabarCActualizar)
        btnRegresar = findViewById(R.id.btnregresarCategActualizar)

        // Configuración de listeners
        btnActualizar.setOnClickListener { actualizarCategoria() }
        btnRegresar.setOnClickListener { regresar() }

        // Cargar datos si están disponibles
        cargarDatos()
    }

    private fun actualizarCategoria() {
        val nombre = spnNombreCategoria.text.toString()
        val descripcion = txtDescripcionCategoriaActualizar.text.toString()
        val pais = txtPaisCategoriaActualizar.text.toString()

        // Crear objeto Categoria con los datos actualizados
        val categoria = Categoria(categoriaId, nombre, pais, descripcion, "")

        // Actualizar en la base de datos
        val salida = ArregloCategoria().update(categoria)

        // Mostrar mensaje dependiendo del resultado
        if (salida > 0) {
            showAlert("Categoría actualizada correctamente")
        } else {
            showAlert("Error en la actualización de la categoría")
        }
    }

    private fun regresar() {
        val intent = Intent(this, ListadoCategoriaActivity::class.java)
        startActivity(intent)
        finish() // Finalizar esta actividad para evitar volver a ella desde el botón de regreso
    }

    private fun showAlert(mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Mensaje")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun cargarDatos() {
        val info = intent.extras
        if (info != null) {
            categoriaId = info.getInt("codigo", -1)
            if (categoriaId != -1) {
                val categoria = ArregloCategoria().findById(categoriaId)
                if (categoria != null) {
                    spnNombreCategoria.setText(categoria.nombre)
                    txtDescripcionCategoriaActualizar.setText(categoria.descripcion)
                    txtPaisCategoriaActualizar.setText(categoria.pais)
                } else {
                    showAlert("Categoría no encontrada")
                }
            } else {
                showAlert("Código de categoría inválido")
            }
        } else {
            showAlert("No se recibieron datos de la categoría")
        }
    }
}
