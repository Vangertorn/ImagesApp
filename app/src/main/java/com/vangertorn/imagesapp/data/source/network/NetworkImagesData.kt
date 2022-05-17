package com.vangertorn.imagesapp.data.source.network

import com.vangertorn.imagesapp.data.mapper.toModel
import com.vangertorn.imagesapp.data.mapper.toModelFromDTO
import com.vangertorn.imagesapp.data.network.api.ImageApi
import com.vangertorn.imagesapp.data.source.ImagesData
import com.vangertorn.imagesapp.domain.model.ImageModel
import javax.inject.Inject

class NetworkImagesData @Inject constructor(
    private val api: ImageApi
) : ImagesData {
    override suspend fun getImages(): List<ImageModel> {
        return api.getRandomImages(limit = 10).toModelFromDTO()
    }

    override suspend fun saveImagesDatabase(images: List<ImageModel>) {
        TODO("Not yet implemented")
    }

    override suspend fun changeFavorite(imageId: String, isFavorite: Boolean): List<ImageModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getDetails(imageId: String): ImageModel {
        return api.getDetails(imageId).toModel()
    }

    override suspend fun getFavoriteImages(): List<ImageModel> {
        TODO("Not yet implemented")
    }
}
