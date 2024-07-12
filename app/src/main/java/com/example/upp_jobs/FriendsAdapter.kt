package com.example.upp_jobs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class FriendsAdapter(private val friends: List<Friend>) :
    RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.friendName)
        val profileImageView: ImageView = itemView.findViewById(R.id.friendProfileImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_friend, parent, false)
        return FriendViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friends[position]
        holder.nameTextView.text = friend.name
        Picasso.get().load(friend.profilePicture).into(holder.profileImageView)
    }

    override fun getItemCount(): Int {
        return friends.size
    }
}
