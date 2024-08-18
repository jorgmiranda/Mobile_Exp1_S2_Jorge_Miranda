package com.example.exp1_s2_jorge_miranda

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    //Navegación
    private lateinit var loginBtn:Button;

    private lateinit var registraseTextView: TextView;

    private lateinit var recuperarcontraTextView:TextView;

    //Text Fields
    private lateinit var rutEditText:EditText;
    private lateinit var passEditText: EditText

    //SharedPreferences
    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("pracientes_preference", Context.MODE_PRIVATE)
    }

    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        registraseTextView = findViewById(R.id.txtview_registro)
        recuperarcontraTextView = findViewById(R.id.txtview_recupear_contra)
        loginBtn = findViewById(R.id.login_btn)

        registraseTextView.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        recuperarcontraTextView.setOnClickListener {
            val intent = Intent(this, RecuperarContraActivity::class.java)
            startActivity(intent)
        }

        loginBtn.setOnClickListener {
            rutEditText = findViewById(R.id.usuario_input)
            passEditText = findViewById(R.id.password_input)
            val rut = rutEditText.text.toString();
            val pass = passEditText.text.toString()

            if(rut.equals("admin") && pass.equals("123.pass")){
                val intent = Intent(this, AdminActivity::class.java)
                startActivity(intent)
            }else{
                val pacientes = cargarPacientes()
                var registrado = false;
                pacientes?.forEach { paciente ->
                    if (paciente.rut.equals(rut) && paciente.contrasena.equals(pass)){
                        registrado = true
                        return@forEach
                    }
                }

                if (registrado){
                    val intent = Intent(this, UserActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "¡Crendenciales incorrectas!", Toast.LENGTH_LONG).show();
                }


            }

        }
    }

    private fun cargarPacientes(): MutableList<Paciente>{
        val json = sharedPreferences.getString("pacientes_key", null)
        return if(json != null){
            val type = object : TypeToken<MutableList<Paciente>>() {}.type
            gson.fromJson(json, type)
        }else{
            mutableListOf()
        }
    }


}