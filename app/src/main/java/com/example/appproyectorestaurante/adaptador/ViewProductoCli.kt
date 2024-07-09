package com.example.appproyectorestaurante.adaptador

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectorestaurante.R

class ViewProductoCli(vista:View):RecyclerView.ViewHolder(vista) {
    //declarar atributos
    var tvIdProductoC:TextView
    var tvNombreProductoC:TextView
    var imgFotoProductoC:ImageView
    var btnYape:Button

    init {
        tvIdProductoC=vista.findViewById(R.id.tvIdProductoC)
        tvNombreProductoC=vista.findViewById(R.id.tvNombreProductoC)
        imgFotoProductoC=vista.findViewById(R.id.imgFotoProductoC)
        btnYape=vista.findViewById(R.id.btnYape)
    }
}