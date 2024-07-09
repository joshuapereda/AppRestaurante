package com.example.appproyectorestaurante.adaptador

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectorestaurante.R

class ViewCategoria (vista: View): RecyclerView.ViewHolder(vista) {

    var tvNombre: TextView
    var imgFotoM: ImageView
    var btnEditar:Button
    var btnEliminar:Button


    init {
        tvNombre=vista.findViewById(R.id.tvNombre)
        imgFotoM=vista.findViewById(R.id.imgFotoM)
        btnEditar=vista.findViewById(R.id.btnEditar)
        btnEliminar=vista.findViewById(R.id.btnEliminar)

    }
}