package com.example.appproyectorestaurante.adaptador

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectorestaurante.CategoriaActualizarActivity
import com.example.appproyectorestaurante.R
import com.example.appproyectorestaurante.entidad.Categoria
import com.example.appproyectorestaurante.controlador.ArregloCategoria

class CategoriaAdaptador(
    private var lista: ArrayList<Categoria>,
    private val onDeleteSuccess: () -> Unit
) : RecyclerView.Adapter<ViewCategoria>() {

    private lateinit var contexto: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewCategoria {
        contexto = parent.context
        val inflador = LayoutInflater.from(contexto).inflate(R.layout.listado_item_categoria_main, parent, false)
        return ViewCategoria(inflador)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewCategoria, position: Int) {
        val categoria = lista[position]

        holder.tvNombre.text = categoria.nombre

        // Cargar la imagen según el nombre de la categoría
        val imagenId = getImagenIdByOperador(categoria.nombre)
        holder.imgFotoM.setImageResource(imagenId)

        holder.itemView.setOnClickListener {
            mostrarDetalleCategoria(categoria)
        }

        holder.btnEliminar.setOnClickListener {
            val builder = AlertDialog.Builder(contexto)
            builder.setTitle("Eliminar Categoría")
            builder.setMessage("¿Está seguro de eliminar la categoría ${categoria.nombre}?")

            builder.setPositiveButton("Aceptar") { dialogInterface, _ ->
                val salida = ArregloCategoria().deleteById(categoria.codigo)
                if (salida > 0) {
                    lista.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemRangeChanged(position, lista.size)
                    onDeleteSuccess()
                } else {
                    showAlert("Error al eliminar la categoría")
                }
            }

            builder.setNegativeButton("Cancelar", null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        holder.btnEditar.setOnClickListener {
            val intent = Intent(contexto, CategoriaActualizarActivity::class.java).apply {
                putExtra("codigo", categoria.codigo)
            }
            contexto.startActivity(intent)
        }
    }

    private fun mostrarDetalleCategoria(categoria: Categoria) {
        val mensaje = """
            Nombre: ${categoria.nombre}
            Descripción: ${categoria.descripcion}
            País: ${categoria.pais}
        """.trimIndent()

        AlertDialog.Builder(contexto)
            .setTitle("Detalle de Categoría")
            .setMessage(mensaje)
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun showAlert(message: String) {
        AlertDialog.Builder(contexto)
            .setTitle("Mensaje")
            .setMessage(message)
            .setPositiveButton("Aceptar", null)
            .show()
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
            else -> R.drawable.chifa // Cambia esto por tu imagen por defecto si el nombre no coincide
        }
    }
}
