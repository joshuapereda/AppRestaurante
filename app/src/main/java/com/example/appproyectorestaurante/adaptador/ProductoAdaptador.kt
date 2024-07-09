package com.example.appproyectorestaurante.adaptador

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appproyectorestaurante.ProductoActualizarActivity
import com.example.appproyectorestaurante.R
import com.example.appproyectorestaurante.entidad.Producto
import com.example.appproyectorestaurante.utils.AppConfig

class ProductoAdaptador(var lista: List<Producto>) : RecyclerView.Adapter<ViewProducto>() {

    fun actualizarLista(nuevaLista: List<Producto>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewProducto {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.listado_item_producto_main, parent, false)
        return ViewProducto(item)
    }

    override fun getItemCount(): Int {
        return lista.size
    }
    override fun onBindViewHolder(holder: ViewProducto, position: Int) {
        val producto = lista[position]
        holder.tvIdProducto.text = producto.id_producto.toString()
        holder.tvNombreProducto.text = producto.nombre

        Glide.with(holder.itemView.context)
            .load(producto.foto)
            .placeholder(R.drawable.placeholder_image)
            .into(holder.imgFotoProducto)

        holder.itemView.setOnClickListener {
            val intent = Intent(AppConfig.CONTEXTO, ProductoActualizarActivity::class.java)
            intent.putExtra("id", lista[position].id_producto)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            ContextCompat.startActivity(AppConfig.CONTEXTO, intent, null)
        }

    }
}
