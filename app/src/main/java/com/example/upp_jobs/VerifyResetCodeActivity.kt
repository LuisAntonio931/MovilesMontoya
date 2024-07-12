package com.example.upp_jobs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class VerifyResetCodeActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var codeEditText: EditText
    private lateinit var verifyButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_reset_code)

        // Obtener el email del intent
        email = intent.getStringExtra("email") ?: ""
        if (email.isEmpty()) {
            Toast.makeText(this, "Error al obtener el email", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Inicializar vistas
        codeEditText = findViewById(R.id.reset_code)
        verifyButton = findViewById(R.id.btn_verify_code)

        // Manejar clic en el botón de verificar
        verifyButton.setOnClickListener {
            val code = codeEditText.text.toString().trim()

            // Validar que el código no esté vacío
            if (code.isEmpty()) {
                codeEditText.error = "Ingrese el código de verificación"
                return@setOnClickListener
            }

            // Simular verificación exitosa del código
            if (code == "123456") { // Simulación de código de verificación válido
                Toast.makeText(this, "Código de verificación correcto", Toast.LENGTH_LONG).show()

                // Redirigir al usuario a la actividad de reseteo de contraseña (simulación)
                val intent = Intent(this, ResetPasswordActivity::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Código de verificación incorrecto", Toast.LENGTH_LONG).show()
            }
        }
    }
}
