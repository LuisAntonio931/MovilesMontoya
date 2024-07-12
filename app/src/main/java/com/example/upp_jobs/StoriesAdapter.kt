package com.example.upp_jobs

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.upp_jobs.StoryDetailActivity

class StoriesAdapter(
    private val storiesList: List<Story>,
    private val context: Context
) : RecyclerView.Adapter<StoriesAdapter.StoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_story, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = storiesList[position]
        // Aquí puedes cargar la imagen o video en tu vista
        //holder.storyImageView.setImageResource(story.imageResource)
        holder.itemView.setOnClickListener {
            // Manejar el click para abrir la historia y reproducir el video
            val intent = Intent(context, StoryDetailActivity::class.java)
            intent.putExtra("story", story)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = storiesList.size

    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val storyImageView: ImageView = itemView.findViewById(R.id.storyImageView)
    }
}
