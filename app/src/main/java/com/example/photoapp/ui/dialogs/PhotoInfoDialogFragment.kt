package com.example.photoapp.ui.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.photoapp.R
import com.example.photoapp.data.network.response.PhotoDetailResponse

class PhotoInfoDialogFragment : DialogFragment() {

    private lateinit var currentPhoto: PhotoDetailResponse

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(this.context)
            .inflate(R.layout.fragment_dialog_photo_info, null, false)
        bindUi(view)

        return AlertDialog.Builder(this.context!!)
            .setView(view)
            .create()
    }

    private fun bindUi(view: View) {
        val exif = currentPhoto.exif

        view.findViewById<TextView>(R.id.info_dimensions_text).text =
            resources.getString(R.string.info_dimensions, currentPhoto.width, currentPhoto.height)

        view.findViewById<TextView>(R.id.info_make_text).text = if (exif?.make == null) {
            resources.getString(R.string.info_make_empty)
        } else {
            resources.getString(R.string.info_make, exif.make)
        }

        view.findViewById<TextView>(R.id.info_camera_text).text = if (exif?.model == null) {
            resources.getString(R.string.info_model_empty)
        } else {
            resources.getString(R.string.info_model, exif.model)
        }

        view.findViewById<TextView>(R.id.info_exposure_text).text =
            if (exif?.exposureTime == null) {
                resources.getString(R.string.info_exposure_empty)
            } else {
                resources.getString(R.string.info_exposure, exif.exposureTime)
            }

        view.findViewById<TextView>(R.id.info_aperture_text).text = if (exif?.aperture == null) {
            resources.getString(R.string.info_aperture_empty)
        } else {
            resources.getString(R.string.info_aperture, exif.aperture)
        }

        view.findViewById<TextView>(R.id.info_iso_text).text = if (exif?.iso == null) {
            resources.getString(R.string.info_iso_empty)
        } else {
            resources.getString(R.string.info_iso, exif.iso)
        }

        view.findViewById<TextView>(R.id.info_focal_text).text = if (exif?.focalLength == null) {
            resources.getString(R.string.info_focal_empty)
        } else {
            resources.getString(R.string.info_focal, exif.focalLength)
        }
    }

    fun setCurrentPhoto(photo: PhotoDetailResponse) {
        this.currentPhoto = photo
    }
}