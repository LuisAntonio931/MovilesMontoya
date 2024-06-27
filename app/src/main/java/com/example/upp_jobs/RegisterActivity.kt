package com.example.upp_jobs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtNombre: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var txtConfirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtNombre = findViewById(R.id.name_register)
        txtEmail = findViewById(R.id.email_register)
        txtPassword = findViewById(R.id.password_register)
        txtConfirmPassword = findViewById(R.id.password_confirmation_register)

        val btnRegister = findViewById<Button>(R.id.btn_register)
        val btnBack = findViewById<Button>(R.id.btn_back)

        btnRegister.setOnClickListener {
            clickBtnRegister(it)
        }

        btnBack.setOnClickListener {
            // Volver a la pantalla de inicio de sesión
            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Finaliza esta actividad para que no quede en el back stack
        }
    }

    private fun clickBtnRegister(view: View) {
        val name = txtNombre.text.toString().trim()
        val email = txtEmail.text.toString().trim()
        val pass = txtPassword.text.toString().trim()
        val passAgain = txtConfirmPassword.text.toString().trim()

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || passAgain.isEmpty()) {
            Snackbar.make(view, "Por favor, completa todos los campos", Snackbar.LENGTH_LONG).show()
            return
        } else if (pass != passAgain) {
            Snackbar.make(view, "Lo sentimos, las contraseñas no coinciden", Snackbar.LENGTH_LONG).show()
            return
        }

        val url = "http://192.168.84.170:8000/api/users"
        val body = JSONObject().apply {
            val attributes = JSONObject().apply {
                put("name", name)
                put("email", email)
                put("password", pass)
            }
            put("data", JSONObject().apply {
                put("attributes", attributes)
            })
        }

        val request = JsonObjectRequest(
            Request.Method.POST, url, body,
            { response ->
                // Manejar la respuesta exitosa del servidor
                Snackbar.make(view, "Felicidades, usuario agregado exitosamente", Snackbar.LENGTH_LONG).show()
                // Volver a la pantalla de inicio de sesión después de un registro exitoso
                startActivity(Intent(this, LoginActivity::class.java))
                finish() // Finaliza esta actividad para que no quede en el back stack
            },
            { error ->
                // Manejar el error de la solicitud
                Snackbar.make(view, "Error al registrar usuario: ${error.message}", Snackbar.LENGTH_LONG).show()
                error.printStackTrace()
            }
        )

        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }
}
