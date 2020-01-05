package com.example.photoapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoapp.R
import com.example.photoapp.data.network.response.collections.CollectionResponse

class CollectionsAdapter(
    private val dataSet: List<CollectionResponse>,
    private val onClick: (CollectionResponse) -> Unit
) : RecyclerView.Adapter<CollectionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_collection, parent, false)
        return CollectionsViewHolder(itemView)
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: CollectionsViewHolder, position: Int) {
        val collectionItem = dataSet[position]
        holder.itemView.setOnClickListener { onClick.invoke(collectionItem) }
        holder.titleView.text = collectionItem.title
        holder.subtitleView.text = "${collectionItem.totalPhotos.toString()} photos"
        Glide.with(holder.itemView).load(collectionItem.coverPhoto.urls.regular)
            .into(holder.photoView)
    }
}

class CollectionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val photoView = itemView.findViewById<ImageView>(R.id.collection_photo)
    val titleView = itemView.findViewById<TextView>(R.id.collection_name)
    val subtitleView = itemView.findViewById<TextView>(R.id.collection_photo_count)
}

