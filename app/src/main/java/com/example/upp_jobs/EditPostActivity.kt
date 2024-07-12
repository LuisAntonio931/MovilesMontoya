package com.example.upp_jobs

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class EditPostActivity : AppCompatActivity() {

    private lateinit var editPostTitle: EditText
    private lateinit var editPostMessage: EditText
    private lateinit var buttonSavePost: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_post)

        // Obtener referencias a los elementos de UI
        editPostTitle = findViewById(R.id.editPostTitle)
        editPostMessage = findViewById(R.id.editPostMessage)
        buttonSavePost = findViewById(R.id.buttonSavePost)

        // Obtener el ID del post que se está editando
        val postId = intent.getStringExtra("postId")

        // Aquí deberías implementar la lógica para cargar los detalles del post actual desde tu API
        // y mostrar esos detalles en los EditText
        // Por simplicidad, asumiremos que ya tienes los datos del post y los muestras en los EditText

        // Configurar el click listener para el botón de guardar
        buttonSavePost.setOnClickListener {
            // Obtener los nuevos valores de título y mensaje del EditText
            val newTitle = editPostTitle.text.toString().trim()
            val newMessage = editPostMessage.text.toString().trim()

            // Validar que los campos no estén vacíos
            if (newTitle.isEmpty() || newMessage.isEmpty()) {
                Toast.makeText(this@EditPostActivity, "Por favor ingrese título y mensaje", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear el objeto JSON con los datos actualizados del post
            val updatedPost = JSONObject()
            updatedPost.put("title", newTitle)
            updatedPost.put("message", newMessage)

            // URL de tu API para actualizar el post
            val url = "http://192.168.0.8:8000/api/posts/$postId"

            // Crear la solicitud PUT usando JsonObjectRequest de Volley
            val request = JsonObjectRequest(Request.Method.PUT, url, updatedPost,
                Response.Listener { response ->
                    // Manejar la respuesta si la actualización fue exitosa
                    Toast.makeText(this@EditPostActivity, "Post actualizado correctamente", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                },
                Response.ErrorListener { error ->
                    // Manejar cualquier error en la solicitud
                    Toast.makeText(this@EditPostActivity, "Error al actualizar el post: ${error.message}", Toast.LENGTH_SHORT).show()
                })

            // Agregar la solicitud a la cola de Volley
            Volley.newRequestQueue(this@EditPostActivity).add(request)
        }
    }
}
