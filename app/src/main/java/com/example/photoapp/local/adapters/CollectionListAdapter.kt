package com.example.photoapp.local.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.photoapp.R
import com.example.photoapp.data.db.entities.CollectionResponse
import com.example.photoapp.local.Glide.GlideApp

class CollectionListAdapter(
    private val onClick: (CollectionResponse) -> Unit
) : PagedListAdapter<CollectionResponse, CollectionViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        return CollectionViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        Log.d("NewTag", "photo " + position)
        val collectionItem = getItem(position)
        if (collectionItem != null) {
            holder.bind(collectionItem, onClick)
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<CollectionResponse>() {
            override fun areItemsTheSame(
                oldItem: CollectionResponse,
                newItem: CollectionResponse
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CollectionResponse,
                newItem: CollectionResponse
            ): Boolean =
                oldItem == newItem
        }
    }
}

class CollectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val photoView = itemView.findViewById<ImageView>(R.id.collection_photo)
    private val titleText = itemView.findViewById<TextView>(R.id.collection_name)
    private val countText = itemView.findViewById<TextView>(R.id.collection_photo_count)

    fun bind(collectionItem: CollectionResponse, onClick: (CollectionResponse) -> Unit) {
        titleText.text = collectionItem.title
        countText.text = "${collectionItem.totalPhotos} Photos"

        GlideApp.with(itemView)
            .load(collectionItem.coverPhoto.urls.regular)
            .placeholder(R.color.colorLight)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(photoView)

        itemView.setOnClickListener {
            onClick.invoke(collectionItem)
        }
    }

    companion object {
        fun create(parent: ViewGroup): CollectionViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_collection, parent, false)
            return CollectionViewHolder(view)
        }
    }
}


