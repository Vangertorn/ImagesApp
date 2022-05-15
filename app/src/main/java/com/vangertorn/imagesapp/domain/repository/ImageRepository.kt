package com.vangertorn.imagesapp.domain.repository

import com.vangertorn.imagesapp.domain.model.ImageModel

interface ImageRepository {

    suspend fun getImagesFromNetwork(): List<ImageModel>

    suspend fun getImagesFromLocal(): List<ImageModel>

    suspend fun addImages(images: List<ImageModel>)

    suspend fun changeFavorite(imageId: String, isFavorite: Boolean): List<ImageModel>
}