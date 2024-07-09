package com.example.appproyectorestaurante

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.appproyectorestaurante.entidad.Producto
import com.example.appproyectorestaurante.services.ApiServiceProducto
import com.example.appproyectorestaurante.utils.ApiUtils
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoActualizarActivity:AppCompatActivity() {
    private lateinit var btnCerrarProductoActualizar:Button
    private lateinit var txtIdProductoActualizar:TextInputEditText
    private lateinit var txtNombProductoActualizar: TextInputEditText
    private lateinit var txtPrecioProductoActualizar: TextInputEditText
    private lateinit var txtDescripcionProductoActualizar: TextInputEditText
    private lateinit var ImgFotoActualizar: ImageView
    private lateinit var txtCategoriaProductoActualizar:TextInputEditText
    private lateinit var btnActualizarProducto: Button
    private lateinit var btnEliminarProducto:Button
    //
    private lateinit var apiProducto: ApiServiceProducto

    private var drawable = arrayOf(
        "https://imgur.com/rGTz0bB.jpg", "https://imgur.com/RLI3lD5.jpg", "https://imgur.com/10o83QB.jpg",
        "https://imgur.com/A6wD4Rs.jpg","https://imgur.com/WakB4sG.jpg","https://imgur.com/r3kSt1c.jpg",
        "https://imgur.com/SHkDGze.jpg","https://imgur.com/6Sf7UMJ.jpg","https://imgur.com/PvuYP55.jpg",
        "https://imgur.com/X3YyF2y.jpg","https://imgur.com/PwTa2ZS.jpg","https://imgur.com/Oqvtp8L.jpg",
        "https://imgur.com/SfjSt5J.jpg","https://imgur.com/xbFeimW.jpg","https://imgur.com/lSpZWHg.jpg",
        "https://imgur.com/KmmV1XU.jpg","https://imgur.com/vfaEm8J.jpg","https://imgur.com/w0nZ3GW"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.registro_editar_producto_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnCerrarProductoActualizar=findViewById(R.id.btnCerrarProductoActualizar)
        txtIdProductoActualizar=findViewById(R.id.txtIdProductoActualizar)
        txtNombProductoActualizar=findViewById(R.id.txtNombProductoActualizar)
        txtPrecioProductoActualizar=findViewById(R.id.txtPrecioProductoActualizar)
        txtDescripcionProductoActualizar=findViewById(R.id.txtDescripcionProductoActualizar)
        ImgFotoActualizar=findViewById(R.id.ImgFotoActualizar)
        txtCategoriaProductoActualizar=findViewById(R.id.txtCategoriaProductoActualizar)
        btnActualizarProducto=findViewById(R.id.btnActualizarProducto)
        btnEliminarProducto=findViewById(R.id.btnEliminarProducto)
        //
        apiProducto=ApiUtils.getAPIProducto()
        //
        btnActualizarProducto.setOnClickListener { grabar() }
        btnCerrarProductoActualizar.setOnClickListener { volver() }
        btnEliminarProducto.setOnClickListener { eliminar() }
        ImgFotoActualizar.setOnClickListener { foto() }
        verDatos()
    }
    fun eliminar(){
        var idText = txtIdProductoActualizar.text.toString()
        if (idText.isBlank()) {
            showAlert("El ID del producto no puede estar vacío")
            return
        }
        var id = idText.toInt()
        showAlertEliminar("Seguro de eliminar Producto con ID: $id", id)
    }
    fun foto(){
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Seleccionar Imagen")
        builder.setItems(drawable) { _, which ->
            var imageUrl = drawable[which]
            Glide.with(this)
                .load(imageUrl)
                .into(ImgFotoActualizar)
            ImgFotoActualizar.tag = imageUrl
        }
        builder.create().show()
    }
    fun grabar(){
        var id=txtIdProductoActualizar.text.toString().toInt()
        var nom=txtNombProductoActualizar.text.toString()
        var pre=txtPrecioProductoActualizar.text.toString().toDouble()
        var descrip=txtDescripcionProductoActualizar.text.toString()
        var foto=ImgFotoActualizar.tag?.toString() ?: ""
        var cat=txtCategoriaProductoActualizar.text.toString().toInt()

        var bean=Producto(id,nom,pre,descrip,foto,cat)

        //invocar a la función update
        apiProducto.update(bean).enqueue(object: Callback<Producto> {
            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                if(response.isSuccessful){
                    showAlert("Producto actualizado")
                }
            }
            override fun onFailure(call: Call<Producto>, t: Throwable) {
                showAlert("Producto actualizado")
            }
        })
    }
    fun volver(){
        var intent= Intent(this,ListadoProductoActivity::class.java)
        startActivity(intent)
    }
    fun showAlert(men:String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Sistema")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }
    fun showAlertEliminar(men:String,cod:Int){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Sistema")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener {
                dialogInterface: DialogInterface, i: Int ->
            //invocar a la función deleteById
            apiProducto.deleteById(cod).enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(response.isSuccessful)
                        showAlert("Producto eliminado")
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    showAlert(t.localizedMessage)
                }
            })
        })
        builder.setNegativeButton("Cancelar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }
    fun verDatos(){
        var info=intent.extras!!
        //recuperar clave "codigo"
        var id=info.getInt("id")
        //invocar a la funcion findById
        apiProducto.findById(id).enqueue(object: Callback<Producto> {
            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                if(response.isSuccessful){
                    var bean=response.body()
                    //validar
                    if(bean==null)
                        showAlert("Id de producto no existe")
                    else{
                        txtIdProductoActualizar.setText(bean.id_producto.toString())
                        txtNombProductoActualizar.setText(bean.nombre)
                        txtPrecioProductoActualizar.setText(bean.precio.toString())
                        txtDescripcionProductoActualizar.setText(bean.descripcion)
                        Glide.with(this@ProductoActualizarActivity)
                            .load(bean.foto)
                            .into(ImgFotoActualizar)
                        txtCategoriaProductoActualizar.setText(bean.id_categoria.toString())
                    }
                }
            }
            override fun onFailure(call: Call<Producto>, t: Throwable) {
                showAlert(t.localizedMessage)
            }
        })
    }
}