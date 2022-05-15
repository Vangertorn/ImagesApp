package com.vangertorn.imagesapp.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.vangertorn.imagesapp.databinding.ItemImagesBinding
import com.vangertorn.imagesapp.domain.model.ImageModel

class ImageAdapter(
    private val callbacks: Callbacks
) : ListAdapter<ImageModel, ImageViewHolder>(
    ImageModel.DIFF_CALLBACK
) {

    interface Callbacks {
        fun onItemClicked(imageId: String)
        fun onFavoriteClicked(imageId: String, isFavorite: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemImagesBinding.inflate(inflater, parent, false)
        return ImageViewHolder(itemBinding, callbacks)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}