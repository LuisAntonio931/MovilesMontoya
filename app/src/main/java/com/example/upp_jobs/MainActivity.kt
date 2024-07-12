package com.example.upp_jobs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity(), PostAdapter.PostClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var postAdapter: PostAdapter
    private val posts = mutableListOf<Post>()
    private val allPosts = mutableListOf<Post>() // Para mantener todos los posts sin filtrar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_posts)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Creación del adapter pasando el listener adecuado
        postAdapter = PostAdapter(posts, this)
        recyclerView.adapter = postAdapter

        fetchPosts()
        setupCategorySpinner()

        // Configurar los listeners para los iconos (ejemplo con el botón de perfil)
        findViewById<ImageView>(R.id.homeImage).setOnClickListener {
            startActivity(Intent(this, HomeFragment::class.java))
        }
        findViewById<ImageView>(R.id.plusImage).setOnClickListener {
            startActivity(Intent(this, MarketPlace::class.java))
        }
        findViewById<TextView>(R.id.share_text).setOnClickListener {
            startActivity(Intent(this, PostActivity::class.java))
        }
        findViewById<ImageView>(R.id.iconoperfil).setOnClickListener {
            startActivity(Intent(this, UsersActivity::class.java))
        }
        findViewById<ImageView>(R.id.homeIcon).setOnClickListener {
            startActivity(Intent(this, FriendsActivity::class.java))
        }
        findViewById<ImageView>(R.id.notificationImage).setOnClickListener {
            startActivity(Intent(this, NotificationsActivity::class.java))
        }
        findViewById<ImageView>(R.id.alarm_notificate).setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        findViewById<ImageView>(R.id.home_iconregister).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun fetchPosts() {
        val url = "http://192.168.0.8:8000/api/posts"
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val dataArray: JSONArray = response.getJSONArray("data")
                    for (i in 0 until dataArray.length()) {
                        val postJson = dataArray.getJSONObject(i).getJSONObject("attributes")
                        val post = Post(
                            id = dataArray.getJSONObject(i).getString("id"),
                            title = postJson.getString("title"),
                            message = postJson.getString("message"),
                            category = postJson.optString("category", "General")
                        )
                        allPosts.add(post)
                    }
                    posts.clear()
                    posts.addAll(allPosts)
                    postAdapter.notifyDataSetChanged()
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

    private fun setupCategorySpinner() {
        val categories = listOf("Todos", "Categoría 1", "Categoría 2", "Categoría 3") // Ejemplo de categorías
        val spinner = findViewById<Spinner>(R.id.categorySpinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = categories[position]
                filterPostsByCategory(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hacer nada
            }
        }
    }

    private fun filterPostsByCategory(category: String) {
        val filteredPosts = if (category == "Todos") {
            allPosts
        } else {
            allPosts.filter { it.category == category }
        }
        posts.clear()
        posts.addAll(filteredPosts)
        postAdapter.notifyDataSetChanged()
    }

    // Implementación de los métodos de PostAdapter.PostClickListener
    override fun onEditClicked(post: Post) {
        editPost(post)
    }

    override fun onDeleteClicked(post: Post) {
        deletePost(post)
    }

    private fun editPost(post: Post) {
        // Abrir EditPostActivity para editar el post
        val intent = Intent(this, EditPostActivity::class.java)
        intent.putExtra("postId", post.id)
        startActivityForResult(intent, EDIT_POST_REQUEST)
    }

    private fun deletePost(post: Post) {
        val url = "http://192.168.0.8:8000/api/posts/${post.id}"
        val request = StringRequest(Request.Method.DELETE, url,
            { response ->
                // Si la eliminación fue exitosa, actualizar la lista de posts
                allPosts.remove(post)
                posts.remove(post)
                postAdapter.notifyDataSetChanged()
            },
            { error ->
                error.printStackTrace()
            }
        )

        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_POST_REQUEST && resultCode == RESULT_OK) {
            fetchPosts()
        }
    }

    companion object {
        const val EDIT_POST_REQUEST = 1
    }
}
