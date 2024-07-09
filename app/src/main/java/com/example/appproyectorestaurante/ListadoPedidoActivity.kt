package com.example.appproyectorestaurante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectorestaurante.adaptador.PedidoAdaptador
import com.example.appproyectorestaurante.controlador.ArregloPedido
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

class ListadoPedidoActivity : AppCompatActivity() {
    private lateinit var btnVolverp: Button
    private lateinit var edtBuscarP: TextInputEditText
    private lateinit var btnBuscarP: Button
    private lateinit var btnCerrarSesion:Button
    private lateinit var btnAgregarP: FloatingActionButton
    private lateinit var rvPedido: RecyclerView
    private lateinit var adaptador: PedidoAdaptador
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.listado_pedido_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        edtBuscarP = findViewById(R.id.edtBuscarP)
        btnBuscarP = findViewById(R.id.btnBuscarP)
        btnAgregarP = findViewById(R.id.btnAgregarP)
        rvPedido = findViewById(R.id.rvPedido)
        btnVolverp = findViewById(R.id.btnVolverp)
        btnCerrarSesion=findViewById(R.id.btnCerrarSesion)

        adaptador = PedidoAdaptador(ArrayList())
        rvPedido.layoutManager = LinearLayoutManager(this)
        rvPedido.adapter = adaptador

        btnAgregarP.setOnClickListener { agregar() }
        btnBuscarP.setOnClickListener { buscar() }
        btnVolverp.setOnClickListener { volver() }
        btnCerrarSesion.setOnClickListener { cerrarSesion() }

        actualizarLista()
    }
    override fun onResume() {
        super.onResume()
        actualizarLista()
    }

     fun agregar() {
        val intent = Intent(this, RegistroPedidoActivity::class.java)
        startActivity(intent)
    }
    fun cerrarSesion(){
        var intent=Intent(this, AdmCliActivity::class.java)
        startActivity(intent)
    }

     fun buscar() {
        val nomcli = edtBuscarP.text.toString()
        val data = if (nomcli.isNotEmpty()) {
            ArregloPedido().findByNomCli(nomcli)
        } else {
            ArregloPedido().findAll()
        }
        adaptador.actualizarLista(data)
    }
    fun volver() {
        val intent = Intent(this, OpcionesAdmiActivity::class.java)
        startActivity(intent)
    }

    fun actualizarLista() {
        val data = ArregloPedido().findAll()
        adaptador.actualizarLista(data)
    }

}
