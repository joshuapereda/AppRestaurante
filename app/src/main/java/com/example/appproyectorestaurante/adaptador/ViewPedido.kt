package com.example.appproyectorestaurante.adaptador

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectorestaurante.R

class ViewPedido(item: View): RecyclerView.ViewHolder(item) {
    var tvNomCliente: TextView = item.findViewById(R.id.tvNomCliente)
    var tvProducto: TextView = item.findViewById(R.id.tvProducto)
    var tvPrecioPedido: TextView = item.findViewById(R.id.tvPrecioPedido)
    var imgFotoPedido: ImageView = item.findViewById(R.id.imgFotoPedido)
    var btnYape: Button = item.findViewById(R.id.btnYape)
}
