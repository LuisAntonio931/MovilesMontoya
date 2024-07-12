package com.example.upp_jobs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

class FriendsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var friendsAdapter: FriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)  // Usamos el nuevo layout

        recyclerView = findViewById(R.id.friendsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        fetchFriends()
    }

    private fun fetchFriends() {
        val url = "http://192.168.0.8:8000/api/users"
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val friends = mutableListOf<Friend>()
                    val dataArray: JSONArray = response.getJSONArray("data")
                    for (i in 0 until dataArray.length()) {
                        val friendJson = dataArray.getJSONObject(i).getJSONObject("attributes")
                        val friend = Friend(
                            id = dataArray.getJSONObject(i).getString("id"),
                            name = friendJson.getString("name"),
                            profilePicture = friendJson.getString("profile_picture")
                        )
                        friends.add(friend)
                    }
                    friendsAdapter = FriendsAdapter(friends)
                    recyclerView.adapter = friendsAdapter
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
