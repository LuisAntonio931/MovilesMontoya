package com.example.upp_jobs

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UsersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)  // Usamos el nuevo layout

        /*
        findViewById<ImageView>(R.id.edit_profile_button).setOnClickListener {
            startActivity(Intent(this, Activity_editar::class.java))
        }

         */
    }
}