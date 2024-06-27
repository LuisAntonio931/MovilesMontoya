package com.example.upp_jobs

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_posts)  // Usamos el nuevo layout

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchPosts()

        // Buscar el TextView "¿Qué estás pensando?" por su ID y configurar el listener de clics
        val shareText = findViewById<TextView>(R.id.share_text)
        shareText.setOnClickListener {
            val intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }

        val icon = findViewById<ImageView>(R.id.iconoperfil)
        icon.setOnClickListener {
            val intent = Intent(this, UsersActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchPosts() {
        val url = "http://192.168.84.170:8000/api/posts"
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val posts = mutableListOf<Post>()
                    val dataArray: JSONArray = response.getJSONArray("data")
                    for (i in 0 until dataArray.length()) {
                        val postJson = dataArray.getJSONObject(i).getJSONObject("attributes")
                        val post = Post(
                            id = dataArray.getJSONObject(i).getString("id"),
                            title = postJson.getString("title"),
                            message = postJson.getString("message")
                        )
                        posts.add(post)
                    }
                    postAdapter = PostAdapter(posts)
                    recyclerView.adapter = postAdapter
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            }
        )

        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }
}
