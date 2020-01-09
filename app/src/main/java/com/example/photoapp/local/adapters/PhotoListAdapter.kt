package com.example.photoapp.local.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.photoapp.R
import com.example.photoapp.data.db.entities.PhotoResponse
import com.example.photoapp.local.Glide.load

class PhotoListAdapter(
    private val onClick: (View, PhotoResponse) -> Unit
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

    fun bind(photoItem: PhotoResponse, onClick: (View, PhotoResponse) -> Unit) {
        ViewCompat.setTransitionName(photoView, "transitionName${photoItem.id}")

        photoView.load(
            photoItem.urls.regular,
            itemView.context.resources.displayMetrics,
            Pair(photoItem.width, photoItem.height),
            true
        )

        photoView.setOnClickListener {
            onClick.invoke(photoView, photoItem)
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
