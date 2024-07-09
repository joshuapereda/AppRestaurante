package com.example.appproyectorestaurante

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

class ProductoActivity:AppCompatActivity() {
    private lateinit var btnCerrarProducto:Button
    private lateinit var txtNombProducto:TextInputEditText
    private lateinit var txtPrecioProducto:TextInputEditText
    private lateinit var txtDescripcionProducto:TextInputEditText
    private lateinit var txtCategoriaProducto:TextInputEditText
    private lateinit var ImgFoto:ImageView
    private lateinit var btnGrabarProducto:Button

    private lateinit var apiProducto:ApiServiceProducto

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
        setContentView(R.layout.registro_producto_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnCerrarProducto=findViewById(R.id.btnRegresarProduc)
        txtNombProducto=findViewById(R.id.txtNombProducto)
        txtPrecioProducto=findViewById(R.id.txtPrecioProduc)
        txtDescripcionProducto=findViewById(R.id.txtDescripcionProduc)
        ImgFoto=findViewById(R.id.ImgFoto)
        txtCategoriaProducto=findViewById(R.id.txtCategoriaProduc)
        btnGrabarProducto=findViewById(R.id.btnGrabarProduc)
        //
        apiProducto=ApiUtils.getAPIProducto()
        //

        btnCerrarProducto.setOnClickListener { volver() }
        btnGrabarProducto.setOnClickListener { grabar() }
        ImgFoto.setOnClickListener { foto() }
    }
    fun volver(){
        var intent= Intent(this,ListadoProductoActivity::class.java)
        startActivity(intent)
    }

    fun foto() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Seleccionar Imagen")
        builder.setItems(drawable) { _, which ->
            var imageUrl = drawable[which]
            Glide.with(this)
                .load(imageUrl)
                .into(ImgFoto)
            ImgFoto.tag = imageUrl
        }
        builder.create().show()
    }
        fun grabar(){
            var nom = txtNombProducto.text.toString()
            var pre = try {
                txtPrecioProducto.text.toString().toDouble()
            } catch (e: NumberFormatException) {
                showAlert("Ingrese un precio válido")
                return
            }
            var descrip = txtDescripcionProducto.text.toString()
            var foto = ImgFoto.tag?.toString() ?: ""
            var cat = try {
                txtCategoriaProducto.text.toString().toInt()
            } catch (e: NumberFormatException) {
                showAlert("Ingrese una categoría válida")
                return
            }

            val bean = Producto(0, nom, pre, descrip, foto, cat)
            grabarProducto(bean)
        }

    fun grabarProducto(bean:Producto){
        //invocar a la función save
        apiProducto.save(bean).enqueue(object : Callback<Producto> {
            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {
                if(response.isSuccessful){
                    showAlert("Producto registrado")
                }
            }
            override fun onFailure(call: Call<Producto>, t: Throwable) {
                showAlert("Producto registrado")
            }
        })
    }

    fun showAlert(men:String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Sistema")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }
}