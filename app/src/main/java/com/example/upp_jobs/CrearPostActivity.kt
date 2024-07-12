package com.example.upp_jobs

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class CrearPostActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextMessage: EditText
    private lateinit var editTextSlug: EditText
    private lateinit var editTextUserId: EditText
    private lateinit var editTextCategoryId: EditText
    private lateinit var btnEnviarPost: Button

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crearnuevopost)

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextMessage = findViewById(R.id.editTextMessage)
        editTextSlug = findViewById(R.id.editTextSlug)
        editTextUserId = findViewById(R.id.editTextUserId)
        editTextCategoryId = findViewById(R.id.editTextCategoryId)
        btnEnviarPost = findViewById(R.id.btnEnviarPost)

        btnEnviarPost.setOnClickListener {
            val title = editTextTitle.text.toString()
            val message = editTextMessage.text.toString()
            val slug = editTextSlug.text.toString()
            val userId = editTextUserId.text.toString()
            val categoryId = editTextCategoryId.text.toString()

            if (title.isNotEmpty() && message.isNotEmpty() && slug.isNotEmpty() && userId.isNotEmpty() && categoryId.isNotEmpty()) {
                crearPost(title, message, slug, userId, categoryId)
            } else {
                Toast.makeText(this, "Por favor, llene todos los campos", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun crearPost(title: String, message: String, slug: String, userId: String, categoryId: String) {
        val url = "http://192.168.0.8:8000/api/posts"
        val json = """
            {
                "data": {
                    "attributes": {
                        "title": "$title",
                        "message": "$message",
                        "slug": "$slug",
                        "user_id": $userId
                    },
                    "relationships": {
                        "category": {
                            "data": {
                                "id": $categoryId
                            }
                        }
                    }
                }
            }
        """

        val body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@CrearPostActivity, "Error al crear el post", Toast.LENGTH_LONG).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                runOnUiThread {
                    if (response.isSuccessful) {
                        Toast.makeText(this@CrearPostActivity, "Post creado exitosamente", Toast.LENGTH_LONG).show()
                        finish() // Regresar a la actividad anterior
                    } else {
                        Toast.makeText(this@CrearPostActivity, "Error: ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
}
