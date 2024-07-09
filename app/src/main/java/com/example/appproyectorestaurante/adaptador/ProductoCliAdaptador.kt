package com.example.appproyectorestaurante.adaptador

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appproyectorestaurante.R
import com.example.appproyectorestaurante.entidad.ProductoCli

class ProductoCliAdaptador(var lista: List<ProductoCli>) : RecyclerView.Adapter<ViewProductoCli>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewProductoCli {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.listado_item_producto_main, parent, false)
        return ViewProductoCli(item)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewProductoCli, position: Int) {
        val producto = lista[position]
        holder.tvIdProductoC.text = producto.id_productoC.toString()
        holder.tvNombreProductoC.text = producto.nombreC

        // Usar Glide para cargar la imagen desde la URL en el campo "foto"
        Glide.with(holder.itemView.context)
            .load(producto.fotoC)
            .placeholder(R.drawable.placeholder_image) // Imagen de placeholder mientras se carga la imagen real
            .error(R.drawable.error_image) // Imagen en caso de error al cargar la imagen
            .into(holder.imgFotoProductoC)

        // Añadir listener para el botón btnYape
        holder.btnYape.setOnClickListener {
            showPaymentDialog(holder.itemView.context)
        }

        // Añadir listener para mostrar detalles del producto
        holder.itemView.setOnClickListener {
            showProductDetailsDialog(holder.itemView.context, producto)
        }
    }

    private fun showPaymentDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_payment, null)

        builder.setView(dialogView)
            .setPositiveButton("Aceptar", null)
            .setNegativeButton("Cancelar", null)

        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun showProductDetailsDialog(context: Context, producto: ProductoCli) {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_product_details, null)

        // Aquí puedes encontrar y configurar las vistas del diálogo para mostrar los detalles del producto
        val tvIdProductoC = dialogView.findViewById<TextView>(R.id.tvIdProductoC)
        val tvNombreProductoC = dialogView.findViewById<TextView>(R.id.tvNombreProductoC)
        val imgFotoProductoC = dialogView.findViewById<ImageView>(R.id.imgFotoProductoC)

        tvIdProductoC.text = producto.id_productoC.toString()
        tvNombreProductoC.text = producto.nombreC

        // Usar Glide para cargar la imagen en el dialog
        Glide.with(context)
            .load(producto.fotoC)
            .placeholder(R.drawable.placeholder_image) // Imagen de placeholder mientras se carga la imagen real
            .error(R.drawable.error_image) // Imagen en caso de error al cargar la imagen
            .into(imgFotoProductoC)

        builder.setView(dialogView)
            .setPositiveButton("Cerrar", null)

        val alertDialog = builder.create()
        alertDialog.show()
    }
}
