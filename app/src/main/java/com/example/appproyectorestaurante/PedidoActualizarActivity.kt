package com.example.appproyectorestaurante

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.appproyectorestaurante.controlador.ArregloPedido
import com.example.appproyectorestaurante.entidad.Pedido
import com.example.appproyectorestaurante.entidad.Producto
import com.example.appproyectorestaurante.utils.ApiUtils
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PedidoActualizarActivity : AppCompatActivity() {

    private lateinit var btnCerrar: Button
    private lateinit var txtCod: TextInputEditText
    private lateinit var txtNomCliente: TextInputEditText
    private lateinit var txtNroMesa: TextInputEditText
    private lateinit var spnProducto: AutoCompleteTextView
    private lateinit var txtDetallePedido: TextInputEditText
    private lateinit var txtPrecioPedido: TextInputEditText
    private lateinit var btnGrabarPedido: Button
    private lateinit var btnEliminarPedido: Button
    private lateinit var ImgActualizar: ImageView

    private var productos: List<Producto>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registra_editar_pedido_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnCerrar = findViewById(R.id.btnVolverActualizar)
        txtCod = findViewById(R.id.txtCodPedido)
        txtNomCliente = findViewById(R.id.txtClienteActualizar)
        txtNroMesa = findViewById(R.id.txtNroMesaActualizar)
        spnProducto = findViewById(R.id.spnProductoActualizar)
        txtDetallePedido = findViewById(R.id.txtDetalleActualizar)
        txtPrecioPedido = findViewById(R.id.txtPrecioActualizarD)
        btnGrabarPedido = findViewById(R.id.btnActualizarPedido)
        btnEliminarPedido = findViewById(R.id.btnEliminarPedido)
        ImgActualizar = findViewById(R.id.ImgActualizarPedido)

        btnGrabarPedido.setOnClickListener { actualizar() }
        btnCerrar.setOnClickListener { volver() }
        btnEliminarPedido.setOnClickListener { eliminar() }

        cargarProductos()
        datos()
    }

    private fun actualizar() {
        val cod = txtCod.text.toString().toInt()
        val nom = txtNomCliente.text.toString()
        val nroMesa = txtNroMesa.text.toString().toInt()
        val prod = spnProducto.text.toString()
        val detalle = txtDetallePedido.text.toString()
        val precio = txtPrecioPedido.text.toString().toDouble()

        val productoSeleccionado = productos?.find { it.nombre == prod }
        val fotoUrl = productoSeleccionado?.foto ?: ""

        val bean = Pedido(cod, nom, nroMesa, prod, detalle, precio, fotoUrl)
        val salida = ArregloPedido().update(bean)
        if (salida > 0) {
            showAlert("Pedido actualizado correctamente.")
        } else {
            showAlert("Error en la actualización del pedido.")
        }
    }

     fun cargarProductos() {
        val apiService = ApiUtils.getAPIProducto().findAll()
        apiService.enqueue(object : Callback<List<Producto>> {
            override fun onResponse(
                call: Call<List<Producto>>,
                response: Response<List<Producto>>
            ) {
                if (response.isSuccessful) {
                    productos = response.body()
                    productos?.let {
                        val adapter = ArrayAdapter(
                            this@PedidoActualizarActivity,
                            android.R.layout.simple_dropdown_item_1line,
                            it.map { producto -> producto.nombre }
                        )
                        spnProducto.setAdapter(adapter)
                        spnProducto.setOnItemClickListener { _, _, position, _ ->
                            val selected = it[position]
                            spnProducto.setText(selected.nombre, false)
                            cargarImagenProducto(selected)
                        }
                    }
                } else {
                    showAlert("Error al cargar los productos desde la API.")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                showAlert(t.localizedMessage ?: "Error de red")
            }
        })
    }

     fun cargarImagenProducto(producto: Producto) {
        Glide.with(this)
            .load(producto.foto)
            .into(ImgActualizar)
        ImgActualizar.visibility = View.VISIBLE
    }

     fun datos() {
        val info = intent.extras!!
        val bean = ArregloPedido().findById(info.getInt("codigo"))
        txtCod.setText(bean.idped.toString())
        txtNomCliente.setText(bean.nomcli)
        txtNroMesa.setText(bean.nrmesa.toString())
        spnProducto.setText(bean.producto, false)
        txtDetallePedido.setText(bean.detalle)
        txtPrecioPedido.setText(bean.precio.toString())
        val productoSeleccionado = productos?.find { it.nombre == bean.producto }
        productoSeleccionado?.let {
            cargarImagenProducto(it)
        }
    }

     fun volver() {
        val intent = Intent(this, ListadoPedidoActivity::class.java)
        startActivity(intent)
    }

     fun eliminar() {
        val cod = txtCod.text.toString().toInt()
        showAlertConfirm("Seguro de eliminar el Pedido con ID: $cod", cod)
    }

     fun showAlertConfirm(mensaje: String, cod: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmación")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar") { _, _ ->
            val salida = ArregloPedido().delete(cod)
            if (salida > 0) {
                showAlert("Pedido eliminado")
            } else {
                showAlert("Error en la eliminación del pedido")
            }
        }
        builder.setNegativeButton("Cancelar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

     fun showAlert(mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Mensaje")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
