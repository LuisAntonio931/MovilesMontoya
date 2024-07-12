package com.example.upp_jobs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Data class for marketplace items
data class MarketplaceItem(val title: String, val description: String)

class MarketplaceAdapter(private val items: List<MarketplaceItem>) : RecyclerView.Adapter<MarketplaceAdapter.MarketplaceViewHolder>() {

    // ViewHolder class to hold the item views
    class MarketplaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.itemTitle)
        val description: TextView = itemView.findViewById(R.id.itemDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketplaceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.marketpalce_item, parent, false)
        return MarketplaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: MarketplaceViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.description.text = item.description
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
