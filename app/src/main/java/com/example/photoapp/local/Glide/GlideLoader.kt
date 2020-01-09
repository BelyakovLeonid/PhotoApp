package com.example.photoapp.local.Glide

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.example.photoapp.R


fun ImageView.load(
    url: String,
    metrics: DisplayMetrics,
    imgSize: Pair<Int, Int>,
    animate: Boolean = false,
    onLoadingFinished: () -> Unit = {}
) {
    val width = metrics.widthPixels
    val newHeight = (imgSize.second.toDouble().div(imgSize.first) * width).toInt()

    //make placeholder by ourselves, because glide can't do it properly
    val placeholderBitmap = Bitmap.createBitmap(width, newHeight, Bitmap.Config.ARGB_8888)
    placeholderBitmap.eraseColor(ContextCompat.getColor(this.context, R.color.colorLight))
    val placeholderDrawable = BitmapDrawable(this.resources, placeholderBitmap)

    val transitionOptions = if (animate)
        DrawableTransitionOptions().crossFade(1000)
    else
        DrawableTransitionOptions()

    val requestOptions = if (animate)
        RequestOptions()
    else
        RequestOptions().dontAnimate()

    Glide.with(this)
        .load(url)
        .dontAnimate()
        .transition(transitionOptions)
        .apply(requestOptions)
        .placeholder(placeholderDrawable)
        .override(width, newHeight)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadingFinished()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadingFinished()
                return false
            }
        })
        .into(this)
}