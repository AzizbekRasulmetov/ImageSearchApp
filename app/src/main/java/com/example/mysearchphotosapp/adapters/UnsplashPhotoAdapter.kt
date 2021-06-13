package com.example.mysearchphotosapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mysearchphotosapp.R
import com.example.mysearchphotosapp.databinding.ItemUnsplashPhotoBinding
import com.example.mysearchphotosapp.models.UnsplashPhoto

class UnsplashPhotoAdapter
    : PagingDataAdapter<UnsplashPhoto, UnsplashPhotoAdapter.PhotoViewHolder>(PHOTO_COMPARATOR) {

    inner class PhotoViewHolder(private val binding: ItemUnsplashPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                photoClickListener?.let {
                    val position = bindingAdapterPosition
                    if (position != -1) {
                        val item = getItem(position)
                        item?.let(it)
                    }
                }
            }
        }

        fun onBind(unsplashPhoto: UnsplashPhoto) {
            binding.apply {
                Glide.with(itemView)
                    .load(unsplashPhoto.urls.regular)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(imageView)

                username.text = unsplashPhoto.user.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            ItemUnsplashPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) holder.onBind(currentItem)
    }

    companion object {

        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<UnsplashPhoto>() {
            override fun areItemsTheSame(oldItem: UnsplashPhoto, newItem: UnsplashPhoto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UnsplashPhoto,
                newItem: UnsplashPhoto
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    private var photoClickListener: ((UnsplashPhoto) -> Unit)? = null

    fun setPhotoClickListener(listener: (UnsplashPhoto) -> Unit) {
        this.photoClickListener = listener
    }

}