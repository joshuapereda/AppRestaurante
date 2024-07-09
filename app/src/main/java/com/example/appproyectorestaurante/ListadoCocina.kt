package com.example.appproyectorestaurante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectorestaurante.adaptador.PedidoAdaptador
import com.example.appproyectorestaurante.controlador.ArregloPedido
import com.example.appproyectorestaurante.entidad.Pedido
import com.google.android.material.textfield.TextInputEditText

class ListadoCocina : AppCompatActivity() {
    private lateinit var btnVolverc: Button
    private lateinit var edtBuscarC: TextInputEditText
    private lateinit var btnBuscarC: Button
    private lateinit var btnCerrarSesion:Button
    private lateinit var rvCocina: RecyclerView
    private lateinit var adaptador: PedidoAdaptador
    private var listaPedidos = ArrayList<Pedido>()
    private val arregloPedido = ArregloPedido()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.listado_cocina_main)

        btnVolverc = findViewById(R.id.btnVolverc)
        edtBuscarC = findViewById(R.id.edtBuscarC)
        btnBuscarC = findViewById(R.id.btnBuscarC)
        rvCocina = findViewById(R.id.rvCocina)
        btnCerrarSesion=findViewById(R.id.btnCerrarSesion)

        adaptador = PedidoAdaptador(listaPedidos)
        rvCocina.layoutManager = LinearLayoutManager(this)
        rvCocina.adapter = adaptador

        btnVolverc.setOnClickListener { volver() }
        btnCerrarSesion.setOnClickListener { cerrarSesion() }
        btnBuscarC.setOnClickListener { buscarPorProducto() }
        cargarPedidos()
    }

     fun cargarPedidos() {
         val arregloPedido = ArregloPedido()
         val listaPedidos = arregloPedido.findAll()
         adaptador.actualizarLista(listaPedidos)

     }
    fun cerrarSesion(){
        var intent=Intent(this, AdmCliActivity::class.java)
        startActivity(intent)
    }

     fun volver() {
        val intent = Intent(this, OpcionesAdmiActivity::class.java)
        startActivity(intent)
    }

    fun buscarPorProducto() {
        val productoBuscado = edtBuscarC.text.toString().trim()
        if (productoBuscado.isNotEmpty()) {
            val pedidosEncontrados = arregloPedido.findByProducto(productoBuscado)
            listaPedidos.clear()
            listaPedidos.addAll(pedidosEncontrados)
            adaptador.actualizarLista(listaPedidos)
        } else {
            cargarPedidos() 
        }
    }

}
