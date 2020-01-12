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
            "Dimensions: ${currentPhoto.width} x ${currentPhoto.height}"

        view.findViewById<TextView>(R.id.info_make_text).text = if (exif?.make == null) {
            "Make: ---"
        } else {
            "Make: ${exif.make}"
        }

        view.findViewById<TextView>(R.id.info_camera_text).text = if (exif?.model == null) {
            "Model: ---"
        } else {
            "Model: ${exif.model}"
        }

        view.findViewById<TextView>(R.id.info_exposure_text).text =
            if (exif?.exposureTime == null) {
                "Exposure: ---"
            } else {
                "Exposure: ${exif.exposureTime}"
            }

        view.findViewById<TextView>(R.id.info_aperture_text).text = if (exif?.aperture == null) {
            "Aperture: ---"
        } else {
            "Aperture: ${exif.aperture}"
        }

        view.findViewById<TextView>(R.id.info_iso_text).text = if (exif?.iso == null) {
            "Iso: ---"
        } else {
            "Iso: ${exif.aperture}"
        }

        view.findViewById<TextView>(R.id.info_focal_text).text = if (exif?.focalLength == null) {
            "Focal length: ---"
        } else {
            "Focal length: ${exif.focalLength}"
        }
    }

    fun setCurrentPhoto(photo: PhotoDetailResponse) {
        this.currentPhoto = photo
    }
}