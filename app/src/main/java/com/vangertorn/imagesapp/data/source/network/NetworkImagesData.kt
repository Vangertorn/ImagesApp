package com.vangertorn.imagesapp.data.source.network

import com.vangertorn.imagesapp.data.mapper.toModelFromDTO
import com.vangertorn.imagesapp.data.network.api.ImageApi
import com.vangertorn.imagesapp.data.source.ImagesData
import com.vangertorn.imagesapp.domain.model.ImageModel
import javax.inject.Inject

class NetworkImagesData @Inject constructor(
    private val api: ImageApi
): ImagesData {
    override suspend fun getImages(): List<ImageModel> {
        return api.getRandomImage().toModelFromDTO()
    }

    override suspend fun addImages(images: List<ImageModel>) {
        TODO("Not yet implemented")
    }
}