package com.example.appproyectorestaurante

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginYRegistroCliActivity : AppCompatActivity() {
    private lateinit var txtUsuarioCli:EditText
    private lateinit var txtContraseñaCli:EditText
    private lateinit var imgRegresarB:ImageView
    private lateinit var btnIngresarCli:Button
    private lateinit var txtRegistroCli:TextView
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_registro_clie_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtUsuarioCli=findViewById(R.id.txtUsuarioCli)
        txtContraseñaCli=findViewById(R.id.txtContraseñaCli)
        imgRegresarB=findViewById(R.id.imgRegresarB)
        btnIngresarCli=findViewById(R.id.btnIngresarCli)
        txtRegistroCli=findViewById(R.id.txtRegistroCli)
        imgRegresarB.setOnClickListener { regresarB() }
        btnIngresarCli.setOnClickListener { ingreso() }
        txtRegistroCli.setOnClickListener{registro()}
        mAuth=FirebaseAuth.getInstance()
    }
    fun regresarB(){
        var intent=Intent(this,AdmCliActivity::class.java)
        startActivity(intent)
    }

    fun ingreso(){
        title = "Autenticación"
        val email = txtUsuarioCli.text.toString()
        val password = txtContraseñaCli.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    showProfile(it.result?.user?.email?:"", ProviderType.BASIC)
                } else {
                    showAlert("No se encuentra registrado")
                }
            }
        } else {
            showAlert("Debes completar todos los campos")
        }
    }
    fun registro(){
        title = "Autenticación"
        val email = txtUsuarioCli.text.toString()
        val password = txtContraseñaCli.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        showProfile(it.result?.user?.email?:"", ProviderType.BASIC)
                } else {
                    showAlert("Se ha producido un error al autenticar al usuario")
                }
            }
        } else {
            showAlert("Debes completar todos los campos")
        }
    }

    fun showAlert(mensaje:String){
        val builder=AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar",null)
        val dialog:AlertDialog=builder.create()
        dialog.show()
    }
    fun showProfile(email:String, provider:ProviderType){
        val profileIntent=Intent(this,PerfilCliActivity::class.java).apply {
            putExtra("email",email)
            putExtra("provider",provider.name)
        }
        startActivity(profileIntent)
    }
}