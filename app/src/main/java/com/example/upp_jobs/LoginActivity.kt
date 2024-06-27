package com.example.upp_jobs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class LoginActivity : AppCompatActivity() {

    private lateinit var txtEmail: EditText
    private lateinit var txtName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtener referencias a los elementos de la interfaz de usuario
        txtEmail = findViewById(R.id.email_login)
        txtName = findViewById(R.id.password_login) // Campo de nombre

        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnRegister = findViewById<Button>(R.id.btn_back)

        // Manejar clic en botón de Login
        btnLogin.setOnClickListener {
            val email = txtEmail.text.toString().trim()
            val name = txtName.text.toString().trim()

            if (email.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                loginUser(email, name)
            }
        }

        // Manejar clic en botón de Register
        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser(email: String, name: String) {
        val url = "http://192.168.84.170:8000/api/users?email=$email&name=$name"

        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val jsonArray = response.getJSONArray("data")
                    var userFound = false

                    for (i in 0 until jsonArray.length()) {
                        val user = jsonArray.getJSONObject(i)
                        val attributes = user.getJSONObject("attributes")
                        val userName = attributes.getString("name")
                        val userEmail = attributes.getString("email")

                        if (userName.equals(name, ignoreCase = true) && userEmail.equals(email, ignoreCase = true)) {
                            userFound = true
                            break
                        }
                    }

                    if (userFound) {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error al iniciar sesión: Nombre y/o correo incorrectos", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    Toast.makeText(this, "Error al iniciar sesión: Respuesta inválida del servidor", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            },
            { error ->
                if (error.networkResponse != null) {
                    val statusCode = error.networkResponse.statusCode
                    when (statusCode) {
                        404 -> {
                            Toast.makeText(this, "Recurso no encontrado en el servidor", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this, "Error en la conexión con el servidor: $statusCode", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Error en la conexión con el servidor: ${error.message}", Toast.LENGTH_SHORT).show()
                }
                error.printStackTrace()
            }
        )

        Volley.newRequestQueue(this).add(request)
    }
}
