package com.example.photoapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.photoapp.R
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.local.Glide.GlideApp

class PhotoListAdapter(
    private val onClick: (PhotoResponse) -> Unit
) : PagedListAdapter<PhotoResponse, PhotoViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        Log.d("NewTag", "photo " + position)
        val photoItem = getItem(position)
        if (photoItem != null) {
            holder.bind(photoItem, onClick)
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<PhotoResponse>() {
            override fun areItemsTheSame(oldItem: PhotoResponse, newItem: PhotoResponse): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: PhotoResponse,
                newItem: PhotoResponse
            ): Boolean =
                oldItem == newItem
        }
    }
}

class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val photoView = itemView.findViewById<ImageView>(R.id.photo_view)

    fun bind(photoItem: PhotoResponse, onClick: (PhotoResponse) -> Unit) {

        GlideApp.with(itemView)
            .load(photoItem.urls.regular)
            .placeholder(R.color.colorLight)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(photoView)

        photoView.setOnClickListener {
            onClick.invoke(photoItem)
        }
    }

    companion object {
        fun create(parent: ViewGroup): PhotoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_photo, parent, false)
            return PhotoViewHolder(view)
        }
    }
}
