package com.vangertorn.imagesapp.data.source

import com.vangertorn.imagesapp.domain.model.ImageModel

interface ImagesData {

    suspend fun getImages(): List<ImageModel>

    suspend fun addImages(images: List<ImageModel>)
}