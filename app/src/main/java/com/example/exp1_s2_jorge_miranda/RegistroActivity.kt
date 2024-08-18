package com.example.exp1_s2_jorge_miranda

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class RegistroActivity : AppCompatActivity() {
    //campos
    private lateinit var nombresEditText:EditText
    private lateinit var apellidoPaternoEditText: EditText
    private lateinit var apellidoMaternoEditText: EditText
    private lateinit var direccionEditText: EditText
    private lateinit var correoEditText: EditText
    private lateinit var contrasenaEditText: EditText
    private lateinit var rutEditText: EditText

    //botones
    private lateinit var regitrarBtn:Button

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("pracientes_preference", Context.MODE_PRIVATE)
    }

    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nombresEditText = findViewById(R.id.registro_nombre)
        apellidoPaternoEditText = findViewById(R.id.registro_apellidopaterno)
        apellidoMaternoEditText = findViewById(R.id.registro_apellidomaterno)
        direccionEditText = findViewById(R.id.registro_direccion)
        correoEditText = findViewById(R.id.registro_correo)
        contrasenaEditText = findViewById(R.id.registro_password)
        rutEditText = findViewById(R.id.registro_rut)

        val pacientes = cargarPacientes()

        regitrarBtn = findViewById(R.id.registrarse_btn)

        regitrarBtn.setOnClickListener{ view ->
            val rut = rutEditText.text.toString()
            val nombres = nombresEditText.text.toString()
            val apellidoPaterno = apellidoPaternoEditText.text.toString()
            val apellidoMaterno = apellidoMaternoEditText.text.toString()
            val direccion = direccionEditText.text.toString()
            val correo = correoEditText.text.toString()
            val contrasena = contrasenaEditText.text.toString()

            val nuevoPaciente = Paciente(rut,nombres, apellidoPaterno, apellidoMaterno, direccion, correo, contrasena)
            pacientes.add(nuevoPaciente)
            guardarPacientes(pacientes)

            Snackbar.make(view, "¡Usuario Registrado Con exito!", Snackbar.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
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

    private fun guardarPacientes (pacientes: MutableList<Paciente>){
        val editar = sharedPreferences.edit()
        val json = gson.toJson(pacientes)
        editar.putString("pacientes_key", json)
        editar.apply()
    }
}