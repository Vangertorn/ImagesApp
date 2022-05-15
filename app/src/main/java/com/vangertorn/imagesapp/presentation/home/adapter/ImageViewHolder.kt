package com.vangertorn.imagesapp.presentation.home.adapter

import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.vangertorn.imagesapp.GlideApp
import com.vangertorn.imagesapp.R
import com.vangertorn.imagesapp.databinding.ItemImagesBinding
import com.vangertorn.imagesapp.domain.model.ImageModel

class ImageViewHolder(
    private val itemBinding: ItemImagesBinding,
    private val callbacks: ImageAdapter.Callbacks
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(item: ImageModel) {
        itemBinding.root.setOnClickListener {
            callbacks.onItemClicked(item.id)
        }
        itemBinding.ivFavorite.setOnClickListener {
            callbacks.onFavoriteClicked(item.id, item.isFavorite)
        }
        GlideApp
            .with(itemView)
            .load(item.url)
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(itemBinding.ivContent)

        val drawable = if (item.isFavorite) {
            AppCompatResources.getDrawable(itemView.context, R.drawable.ic_favorite_active)
        } else {
            AppCompatResources.getDrawable(itemView.context, R.drawable.ic_favorite_inactive)
        }
        itemBinding.ivFavorite.setImageDrawable(drawable)
    }
}