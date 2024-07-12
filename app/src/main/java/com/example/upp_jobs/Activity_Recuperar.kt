package com.example.upp_jobs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class Activity_Recuperar : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var loginLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperacion)

        // Inicialización de vistas
        emailEditText = findViewById(R.id.email3)
        sendButton = findViewById(R.id.btn_send)
        loginLink = findViewById(R.id.login_link)

        // Manejar clic en el botón de enviar
        sendButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()

            // Validación básica de email
            if (email.isEmpty()) {
                emailEditText.error = "Ingresa tu correo electrónico"
                return@setOnClickListener
            }

            // Simular envío de correo de verificación
            enviarCorreoDeVerificacion(email)
        }

        // Manejar clic en el enlace de inicio de sesión
        loginLink.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun enviarCorreoDeVerificacion(email: String) {
        // Simular envío exitoso de correo de verificación
        Toast.makeText(this, "Correo de recuperación enviado", Toast.LENGTH_LONG).show()

        // Redirigir al usuario a la actividad de verificación
        val intent = Intent(this, VerifyResetCodeActivity::class.java)
        intent.putExtra("email", email) // Pasar el email a la siguiente actividad
        startActivity(intent)
        finish()
    }
}
