package com.example.upp_jobs

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class StoryDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story_detail)

        val story = intent.getParcelableExtra<Story>("story")
        val videoView: VideoView = findViewById(R.id.storyVideoView)
        val imageView: ImageView = findViewById(R.id.storyDetailImageView)

        // Ejemplo: Si el story tiene un campo para video, se puede configurar así
        if (story?.videoUri != null) {
            videoView.setVideoURI(Uri.parse(story.videoUri))
            videoView.start()
        } else {
            imageView.setImageResource(story?.imageResource ?: 0)
            imageView.visibility = View.VISIBLE
            videoView.visibility = View.GONE
        }
    }
}
