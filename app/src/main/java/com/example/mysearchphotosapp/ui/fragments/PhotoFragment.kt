package com.example.mysearchphotosapp.ui.fragments

import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.mysearchphotosapp.R
import com.example.mysearchphotosapp.databinding.FragmentPhotoBinding
import com.example.mysearchphotosapp.models.UnsplashPhoto

class PhotoFragment : Fragment(R.layout.fragment_photo) {

    private val binding: FragmentPhotoBinding by viewBinding()
    private val args: PhotoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photo: UnsplashPhoto = args.photo
        setPhotoDetails(photo)
    }

    private fun setPhotoDetails(photo: UnsplashPhoto){
        binding.apply {
            Glide.with(this@PhotoFragment)
                .load(photo.urls.full)
                .error(R.drawable.ic_error)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        textViewCreator.isVisible = true
                        textViewDesc.isVisible = photo.description != null
                        return false
                    }

                })
                .into(imageView)

            textViewDesc.text = photo.description

            val uri = Uri.parse(photo.user.attributionUrl)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            textViewCreator.apply {
                text = "Photo by ${photo.user.name} on Unsplash"
                setOnClickListener {
                    context.startActivity(intent)
                }
                paint.isUnderlineText = true
            }
        }
    }

}