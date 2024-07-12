package com.example.upp_jobs

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CrearActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crearpost)  // Usamos el nuevo layout

        findViewById<ImageView>(R.id.btnPublicar).setOnClickListener {
            startActivity(Intent(this, CrearPostActivity::class.java))
        }

    }


}