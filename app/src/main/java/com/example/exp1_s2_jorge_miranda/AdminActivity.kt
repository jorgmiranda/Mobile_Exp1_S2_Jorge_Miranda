package com.example.exp1_s2_jorge_miranda

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.gridlayout.widget.GridLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AdminActivity : AppCompatActivity() {
    //layout
    private lateinit var tableLayout : TableLayout

    private val sharedPreferences: SharedPreferences by lazy {
        getSharedPreferences("pracientes_preference", Context.MODE_PRIVATE)
    }

    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tableLayout = findViewById(R.id.tableLayout)
        val pacientes = cargarPacientes()

        pacientes?.forEach{ paciente ->
            val tableRow = TableRow(this)
            tableRow.addView(createTextView(paciente.rut))
            tableRow.addView(createTextView(paciente.nombres))
            tableRow.addView(createTextView(paciente.apellidoPaterno))
            tableRow.addView(createTextView(paciente.correo))
            tableRow.addView(createTextView(paciente.contrasena))

            tableLayout.addView(tableRow)
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

    private fun createTextView(text: String): TextView {
        val textView = TextView(this)
        textView.text = text
        textView.setPadding(8, 8, 8, 8)
        textView.background = ContextCompat.getDrawable(this, R.drawable.bordes)
        textView.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT,
            1f
        )
        return textView
    }
}