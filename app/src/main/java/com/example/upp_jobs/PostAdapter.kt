package com.example.upp_jobs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(
    private var posts: List<Post>,
    private val clickListener: PostClickListener
) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    interface PostClickListener {
        fun onEditClicked(post: Post)
        fun onDeleteClicked(post: Post)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)

        holder.editButton.setOnClickListener {
            clickListener.onEditClicked(post)
        }

        holder.deleteButton.setOnClickListener {
            clickListener.onDeleteClicked(post)
        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    fun updatePosts(newPosts: List<Post>) {
        posts = newPosts
        notifyDataSetChanged()
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.postTitle)
        private val message: TextView = itemView.findViewById(R.id.postMessage)
        val editButton: ImageView = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)

        fun bind(post: Post) {
            title.text = post.title
            message.text = post.message
        }
    }
}
