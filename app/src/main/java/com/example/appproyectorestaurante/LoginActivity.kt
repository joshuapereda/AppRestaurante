package com.example.appproyectorestaurante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appproyectorestaurante.entidad.LoginApp
import com.example.appproyectorestaurante.services.ApiServiceLoginApp
import com.example.appproyectorestaurante.utils.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var txtUsuarioL: EditText
    private lateinit var txtContraseñaL: EditText
    private lateinit var imgRegresarB: ImageView
    private lateinit var btnIngresarL: Button

    private lateinit var apiLoginApp: ApiServiceLoginApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtUsuarioL = findViewById(R.id.txtUsuarioL)
        txtContraseñaL = findViewById(R.id.txtContraseñaL)
        imgRegresarB = findViewById(R.id.imgRegresarB)
        btnIngresarL = findViewById(R.id.btnIngresarL)

        apiLoginApp = ApiUtils.getAPILoginApp()

        imgRegresarB.setOnClickListener { regresarB() }
        btnIngresarL.setOnClickListener { ingresar() }
    }

    private fun regresarB() {
        val intent = Intent(this, AdmCliActivity::class.java)
        startActivity(intent)
    }

    private fun ingresar() {
        val usuario = txtUsuarioL.text.toString().trim()
        val contrasenia = txtContraseñaL.text.toString().trim()

        if (usuario.isEmpty() || contrasenia.isEmpty()) {
            showAlert("Por favor, ingresar todos los campos")
            return
        }

        val loginApp = LoginApp(usuario = usuario, contrasenia = contrasenia)
        val call = apiLoginApp.login(loginApp)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Si el inicio de sesión fue exitoso, mostrar el menú de opciones de administrador
                    showMenu()
                } else {
                    showAlert("Error en la solicitud: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                showAlert("Error de red: " + t.message)
            }
        })
    }

    private fun showAlert(mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun showMenu() {
        val menuIntent = Intent(this, OpcionesAdmiActivity::class.java)
        startActivity(menuIntent)
        finish()
    }
}
