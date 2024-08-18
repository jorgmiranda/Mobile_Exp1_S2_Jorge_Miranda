package com.example.exp1_s2_jorge_miranda

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar


class RecuperarContraActivity : AppCompatActivity() {
    //Botones
    private lateinit var recuperarbtn: Button;
    //campos
    private lateinit var recuperarrut:EditText;
    private lateinit var recuperarcorreo:EditText;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperar_contra)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Botonm
        recuperarbtn = findViewById(R.id.recuperar_contra_btn)
        //Correo
        recuperarrut = findViewById(R.id.recuperar_rut)
        recuperarcorreo = findViewById(R.id.recuperar_correo)

        recuperarbtn.setOnClickListener { view ->
           val rut = recuperarrut.text.toString();
           val correo = recuperarcorreo.text.toString();
           var exito = true;
            if (rut.isEmpty()){
                Snackbar.make(view, "¡Favor de ingresar un Rut!", Snackbar.LENGTH_SHORT).show()
                exito = false;
            }
            else if(correo.isEmpty()){
                Snackbar.make(view, "¡Favor de ingresar un Correo!", Snackbar.LENGTH_SHORT).show()
                exito = false;
            }
            if (exito){
                Snackbar.make(view, "¡Contraseña de recuperación enviada al correo!", Snackbar.LENGTH_LONG).show()
            }

        }
    }
}