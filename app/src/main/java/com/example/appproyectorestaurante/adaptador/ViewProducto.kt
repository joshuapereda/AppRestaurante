package com.example.appproyectorestaurante.adaptador

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectorestaurante.R

class ViewProducto(vista:View):RecyclerView.ViewHolder(vista) {
    //declarar atributos
    var tvIdProducto:TextView
    var tvNombreProducto:TextView
    var imgFotoProducto:ImageView


    init {
        tvIdProducto=vista.findViewById(R.id.tvIdProducto)
        tvNombreProducto=vista.findViewById(R.id.tvNombreProducto)
        imgFotoProducto=vista.findViewById(R.id.imgFotoProducto)

    }
}