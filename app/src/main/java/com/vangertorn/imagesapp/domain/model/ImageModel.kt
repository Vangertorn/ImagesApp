package com.vangertorn.imagesapp.domain.model

data class ImageModel(
    val id: String,
    val url: String,
    val isFavorite: Boolean,
    val width: String,
    val height: String,
)
