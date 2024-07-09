package com.example.appproyectorestaurante.adaptador

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appproyectorestaurante.PedidoActualizarActivity
import com.example.appproyectorestaurante.R
import com.example.appproyectorestaurante.entidad.Pedido


class PedidoAdaptador(private var lista: ArrayList<Pedido>) : RecyclerView.Adapter<ViewPedido>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPedido {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido_main, parent, false)
        return ViewPedido(vista)
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    override fun onBindViewHolder(holder: ViewPedido, position: Int) {
        val pedido = lista[position]

        holder.tvNomCliente.text = pedido.nomcli
        holder.tvProducto.text = pedido.producto
        holder.tvPrecioPedido.text = pedido.precio.toString()

        Glide.with(holder.itemView.context)
            .load(pedido.foto)
            .placeholder(R.drawable.placeholder_image)
            .into(holder.imgFotoPedido)

        // Añadir listener para el botón btnYape
        holder.btnYape.setOnClickListener {
            showPaymentDialog(holder.itemView.context)
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, PedidoActualizarActivity::class.java)
            intent.putExtra("codigo", pedido.idped)
            holder.itemView.context.startActivity(intent)
        }
    }

    fun actualizarLista(data: ArrayList<Pedido>) {
        lista.clear()
        lista.addAll(data)
        notifyDataSetChanged()
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
}
