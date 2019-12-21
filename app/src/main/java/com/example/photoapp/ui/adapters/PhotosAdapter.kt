package com.example.photoapp.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photoapp.R
import com.example.photoapp.data.network.response.photos.response.ListResponse

class PhotosAdapter(
    private val dataSet: List<ListResponse>
) : RecyclerView.Adapter<PhotoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoViewHolder(itemView)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val photoItem = dataSet[position]
        Log.d("MyTag", "photo Url = ${photoItem.urls.regular}")
        Glide.with(holder.itemView).load(photoItem.urls.regular).into(holder.photoView)
    }
}

class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val photoView = itemView.findViewById<ImageView>(R.id.photo_view)
}