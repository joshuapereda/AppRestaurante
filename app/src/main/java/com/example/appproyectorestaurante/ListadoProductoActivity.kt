package com.example.appproyectorestaurante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appproyectorestaurante.adaptador.ProductoAdaptador
import com.example.appproyectorestaurante.entidad.Producto
import com.example.appproyectorestaurante.services.ApiServiceProducto
import com.example.appproyectorestaurante.utils.ApiUtils
import com.example.appproyectorestaurante.utils.AppConfig
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListadoProductoActivity: AppCompatActivity() {
    private lateinit var btnVolverR: Button
    private lateinit var edtBuscarR: TextInputEditText
    private lateinit var btnAgregarNuevoProducto: FloatingActionButton
    private lateinit var rvProductos: RecyclerView
    private lateinit var btnBuscarR: Button
    private lateinit var btnCerrarSesion:Button
    private lateinit var apiProducto: ApiServiceProducto
    private lateinit var lista: ArrayList<Producto>
    private lateinit var adaptador: ProductoAdaptador

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.listado_producto_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnVolverR = findViewById(R.id.btnVolverR)
        edtBuscarR = findViewById(R.id.edtBuscarR)
        btnAgregarNuevoProducto = findViewById(R.id.btnAgregarR)
        rvProductos = findViewById(R.id.rvListadoR)
        btnBuscarR = findViewById(R.id.btnBuscarR)
        btnCerrarSesion=findViewById(R.id.btnCerrarSesion)
        apiProducto = ApiUtils.getAPIProducto()

        btnVolverR.setOnClickListener { volver() }
        btnCerrarSesion.setOnClickListener { cerrarSesion() }
        btnBuscarR.setOnClickListener { buscarProd() }
        btnAgregarNuevoProducto.setOnClickListener { nuevo() }

        lista = ArrayList()
        adaptador = ProductoAdaptador(lista)
        rvProductos.adapter = adaptador
        rvProductos.layoutManager = LinearLayoutManager(this)

        datos()
    }

    fun volver() {
        var intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
    fun cerrarSesion(){
        var intent=Intent(this, AdmCliActivity::class.java)
        startActivity(intent)
    }

    fun buscarProd() {
        val nomProd = edtBuscarR.text.toString().trim()
        if (nomProd.isEmpty()) {
            adaptador.actualizarLista(lista)
        } else {
            val listaFiltrada = lista.filter { it.nombre.contains(nomProd, ignoreCase = true) }
            adaptador.actualizarLista(ArrayList(listaFiltrada))
        }
    }
    fun nuevo(){
        var intentNuevo = Intent(this, ProductoActivity::class.java)
        startActivity(intentNuevo)
    }

    fun datos() {
        apiProducto.findAll().enqueue(object: Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if(response.isSuccessful) {
                    lista.clear()
                    response.body()?.let {
                        lista.addAll(it)
                    }
                    adaptador.actualizarLista(lista)
                } else {
                    showAlert("Error en la respuesta del servidor")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                showAlert(t.localizedMessage ?: "Error desconocido")
            }
        })
    }

    fun showAlert(men: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
