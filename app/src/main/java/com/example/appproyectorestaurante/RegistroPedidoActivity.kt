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

class RegistroPedidoActivity : AppCompatActivity() {
    private lateinit var btnCerrar: Button
    private lateinit var txtNomCliente: TextInputEditText
    private lateinit var txtNroMesa: TextInputEditText
    private lateinit var spnProducto: AutoCompleteTextView
    private lateinit var txtDetallePedido: TextInputEditText
    private lateinit var txtPrecioPedido: TextInputEditText
    private lateinit var btnGrabarPedido: Button
    private lateinit var ImgActualizar: ImageView
    private var productos: List<Producto>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registra_pedido_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnCerrar = findViewById(R.id.btnCerrar)
        txtNomCliente = findViewById(R.id.txtNomCliente)
        txtNroMesa = findViewById(R.id.txtNroMesa)
        spnProducto = findViewById(R.id.spnProducto)
        txtDetallePedido = findViewById(R.id.txtDetallePedido)
        txtPrecioPedido = findViewById(R.id.txtPrecioPedido)
        btnGrabarPedido = findViewById(R.id.btnGrabarPedido)
        ImgActualizar = findViewById(R.id.ImgActualizar)
        btnGrabarPedido.setOnClickListener { grabar() }
        btnCerrar.setOnClickListener { cerrar() }
        cargarProductos()
    }

    fun grabar() {
        val nom = txtNomCliente.text.toString().trim()
        val nroMesaStr = txtNroMesa.text.toString().trim()
        val prod = spnProducto.text.toString().trim()
        val detalle = txtDetallePedido.text.toString().trim()
        val precioStr = txtPrecioPedido.text.toString().trim()

        if (nom.isEmpty() || nroMesaStr.isEmpty() || prod.isEmpty() || detalle.isEmpty() || precioStr.isEmpty()) {
            showAlert("Por favor complete todos los campos")
            return
        }

        val nroMesa: Int = try {
            nroMesaStr.toInt()
        } catch (e: NumberFormatException) {
            showAlert("Número de mesa inválido.")
            return
        }

        val precio: Double = try {
            precioStr.toDouble()
        } catch (e: NumberFormatException) {
            showAlert("Precio inválido.")
            return
        }

        val index = productos?.indexOfFirst { it.nombre == prod } ?: -1
        val fotoUrl = if (index != -1) productos!![index].foto ?: "" else ""

        val bean = Pedido(0, nom, nroMesa, prod, detalle, precio, fotoUrl)
        val salida = ArregloPedido().save(bean)
        if (salida > 0) {
            showAlert("Pedido registrado correctamente.")
            txtNomCliente.text?.clear()
            txtNroMesa.text?.clear()
            spnProducto.text?.clear()
            txtDetallePedido.text?.clear()
            txtPrecioPedido.text?.clear()
            ImgActualizar.visibility = View.GONE
        } else {
            showAlert("Error en el registro del pedido.")
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
                        val nombresProductos = it.map { producto -> producto.nombre }
                        val adapter = ArrayAdapter(
                            this@RegistroPedidoActivity,
                            android.R.layout.simple_dropdown_item_1line,
                            nombresProductos
                        )
                        spnProducto.setAdapter(adapter)
                        spnProducto.setOnItemClickListener { parent, view, position, id ->
                            val selectedProducto = parent.getItemAtPosition(position) as String
                            cargarImagenProducto(selectedProducto)
                        }
                    }
                } else {
                    showAlert("Error al cargar los productos desde la API.")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                showAlert(t.localizedMessage)
            }
        })
    }

    fun cargarImagenProducto(nombreProducto: String) {
        val index = productos?.indexOfFirst { it.nombre == nombreProducto } ?: -1
        if (index != -1) {
            val imageUrl = productos!![index].foto
            Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .into(ImgActualizar)
            ImgActualizar.visibility = View.VISIBLE
        } else {
            ImgActualizar.visibility = View.GONE
        }
    }

    fun cerrar() {
        val intent = Intent(this, ListadoPedidoActivity::class.java)
        startActivity(intent)
    }

    fun showAlert(men: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("SISTEMA")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
