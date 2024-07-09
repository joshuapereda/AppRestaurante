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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListadoProductoCliActivity:AppCompatActivity() {
    private lateinit var btnVolverR: Button

    private lateinit var rvProductos: RecyclerView
    private lateinit var apiProducto:ApiServiceProducto
    private lateinit var btnCerrarSesion:Button
    private lateinit var lista:ArrayList<Producto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.listadocli_producto_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnVolverR=findViewById(R.id.btnVolverR)


        rvProductos=findViewById(R.id.rvListadoR)
        btnCerrarSesion=findViewById(R.id.btnCerrarSesion)

        //instanciar
        apiProducto=ApiUtils.getAPIProducto()
        btnVolverR.setOnClickListener { volver() }
        btnCerrarSesion.setOnClickListener { cerrarSesion() }


        lista=ArrayList<Producto>()
        datos()
    }
    fun volver(){
        var intent=Intent(this,MenuActivity::class.java)
        startActivity(intent)
    }
    fun cerrarSesion(){
        var intent=Intent(this, AdmCliActivity::class.java)
        startActivity(intent)
    }

    fun datos(){
        //invocar a la función findAll
        apiProducto.findAll().enqueue(object: Callback<List<Producto>> {
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                if(response.isSuccessful){
                    var adaptador=ProductoAdaptador(response.body()!!)
                    rvProductos.adapter=adaptador
                    rvProductos.layoutManager= LinearLayoutManager(AppConfig.CONTEXTO)
                }
            }
            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                showAlert(t.localizedMessage)
            }
        })
    }
    fun showAlert(men:String){
        val builder= AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(men)
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog =builder.create()
        dialog.show()
    }
}