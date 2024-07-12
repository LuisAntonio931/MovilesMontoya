package com.example.upp_jobs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import android.util.Patterns

class RegisterActivity : AppCompatActivity() {

    private lateinit var txtNombre: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var txtConfirmPassword: EditText
    private lateinit var usernameError: TextView
    private lateinit var emailError: TextView
    private lateinit var passwordError: TextView
    private lateinit var confirmPasswordError: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtNombre = findViewById(R.id.username)
        txtEmail = findViewById(R.id.email)
        txtPassword = findViewById(R.id.password)
        txtConfirmPassword = findViewById(R.id.confirm_password)
        usernameError = findViewById(R.id.username_error)
        emailError = findViewById(R.id.email_error)
        passwordError = findViewById(R.id.password_error)
        confirmPasswordError = findViewById(R.id.confirm_password_error)

        val btnRegister = findViewById<Button>(R.id.register_button)
        val btnBack = findViewById<TextView>(R.id.have_account_text1)

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

        var isValid = true

        if (name.isEmpty()) {
            usernameError.text = "Nombre de usuario requerido"
            txtNombre.setBackgroundResource(R.drawable.edit_text_border_error)
            isValid = false
        } else {
            usernameError.text = ""
            txtNombre.setBackgroundResource(R.drawable.custom_edit_text)
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailError.text = "Correo electrónico no válido"
            txtEmail.setBackgroundResource(R.drawable.edit_text_border_error)
            isValid = false
        } else {
            emailError.text = ""
            txtEmail.setBackgroundResource(R.drawable.custom_edit_text)
        }

        if (pass.isEmpty()) {
            passwordError.text = "Contraseña requerida"
            txtPassword.setBackgroundResource(R.drawable.edit_text_border_error)
            isValid = false
        } else {
            passwordError.text = ""
            txtPassword.setBackgroundResource(R.drawable.custom_edit_text)
        }

        if (passAgain.isEmpty() || pass != passAgain) {
            confirmPasswordError.text = "Las contraseñas no coinciden"
            txtConfirmPassword.setBackgroundResource(R.drawable.edit_text_border_error)
            isValid = false
        } else {
            confirmPasswordError.text = ""
            txtConfirmPassword.setBackgroundResource(R.drawable.custom_edit_text)
        }

        if (!isValid) {
            Snackbar.make(view, "Por favor, corrige los errores en el formulario", Snackbar.LENGTH_LONG).show()
            return
        }

        val url = "http://192.168.0.8:8000/api/users"
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
                startActivity(Intent(this, MainActivity::class.java))
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
