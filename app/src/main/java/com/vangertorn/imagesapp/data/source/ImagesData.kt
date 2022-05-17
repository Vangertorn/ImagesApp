package com.vangertorn.imagesapp.data.source

import com.vangertorn.imagesapp.domain.model.ImageModel

interface ImagesData {

    suspend fun getImages(): List<ImageModel>

    suspend fun saveImagesDatabase(images: List<ImageModel>)

    suspend fun changeFavorite(imageId: String, isFavorite: Boolean): List<ImageModel>

    suspend fun getDetails(imageId: String): ImageModel

    suspend fun getFavoriteImages(): List<ImageModel>
}
