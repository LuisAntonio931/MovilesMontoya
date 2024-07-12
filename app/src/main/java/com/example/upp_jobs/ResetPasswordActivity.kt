package com.example.upp_jobs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var newPasswordEditText: EditText
    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        // Obtener email del intent
        email = intent.getStringExtra("email") ?: ""
        if (email.isEmpty()) {
            Toast.makeText(this, "Error al obtener el email", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Inicializar vistas
        newPasswordEditText = findViewById(R.id.new_password)
        resetButton = findViewById(R.id.btn_reset_password)

        // Manejar clic en el botón de reseteo
        resetButton.setOnClickListener {
            val newPassword = newPasswordEditText.text.toString().trim()

            // Validación básica de contraseña
            if (newPassword.isEmpty()) {
                newPasswordEditText.error = "Ingrese la nueva contraseña"
                return@setOnClickListener
            }

            // Simular reseteo exitoso de contraseña
            Toast.makeText(this, "Contraseña restablecida exitosamente", Toast.LENGTH_LONG).show()

            // Redirigir al usuario a la actividad de inicio de sesión (simulación)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
