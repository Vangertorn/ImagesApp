package com.vangertorn.imagesapp.domain.model

import androidx.recyclerview.widget.DiffUtil

data class ImageModel(
    val id: String,
    val url: String,
    val isFavorite: Boolean,
    val width: String,
    val height: String,
) {
    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ImageModel>() {
            override fun areItemsTheSame(
                oldItem: ImageModel,
                newItem: ImageModel
            ): Boolean =
                oldItem::class == newItem::class

            override fun areContentsTheSame(
                oldItem: ImageModel,
                newItem: ImageModel
            ): Boolean =
                newItem == oldItem
        }
    }
}


