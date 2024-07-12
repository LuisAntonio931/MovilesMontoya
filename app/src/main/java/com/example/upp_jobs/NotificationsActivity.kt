package com.example.upp_jobs

import Notification
import com.example.upp_jobs.NotificationsAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class NotificationsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var notificationsAdapter: NotificationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        recyclerView = findViewById(R.id.notificationsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchNotifications()
    }

    private fun fetchNotifications() {
        val url = "http://192.168.0.8:8000/api/comments"

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val notifications = mutableListOf<Notification>()
                    val dataArray: JSONArray = response.getJSONArray("data")
                    for (i in 0 until dataArray.length()) {
                        val notificationJson = dataArray.getJSONObject(i).getJSONObject("attributes")
                        val notification = Notification(
                            id = dataArray.getJSONObject(i).getString("id"),
                            title = notificationJson.getString("title"),
                            message = notificationJson.getString("message")
                            // Agrega más campos si es necesario
                        )
                        notifications.add(notification)
                    }
                    notificationsAdapter = NotificationsAdapter(notifications)
                    recyclerView.adapter = notificationsAdapter
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                error.printStackTrace()
                Toast.makeText(this, "Error fetching notifications", Toast.LENGTH_SHORT).show()
            }
        )

        // Añadir la solicitud a la cola de solicitudes
        val queue = Volley.newRequestQueue(this)
        queue.add(request)
    }
}
