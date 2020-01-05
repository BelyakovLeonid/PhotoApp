package com.example.photoapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoapp.R
import com.example.photoapp.data.db.entities.PhotoResponse

class PhotosAdapter(
    private val dataSet: List<PhotoResponse>,
    private val onClick: (PhotoResponse) -> Unit
) : RecyclerView.Adapter<PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(itemView)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoItem = dataSet[position]
        Glide.with(holder.itemView)
            .load(photoItem.urls.regular)
            .into(holder.photoView)

        holder.photoView.setOnClickListener {
            onClick.invoke(photoItem)
        }
    }
}

class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val photoView = itemView.findViewById<ImageView>(R.id.photo_view)
}