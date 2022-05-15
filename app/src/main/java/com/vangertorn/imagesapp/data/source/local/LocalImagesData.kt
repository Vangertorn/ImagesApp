package com.vangertorn.imagesapp.data.source.local

import com.vangertorn.imagesapp.data.database.dao.ImageDao
import com.vangertorn.imagesapp.data.mapper.toEntity
import com.vangertorn.imagesapp.data.mapper.toModel
import com.vangertorn.imagesapp.data.source.ImagesData
import com.vangertorn.imagesapp.domain.model.ImageModel
import javax.inject.Inject

class LocalImagesData @Inject constructor(
    private val db: ImageDao
) : ImagesData {
    override suspend fun getImages(): List<ImageModel> {
        return db.getImages().toModel()
    }

    override suspend fun addImages(images: List<ImageModel>) {
        db.addImages(images.toEntity())
    }

    override suspend fun changeFavorite(imageId: String, isFavorite: Boolean): List<ImageModel> {
        return db.changeFavorite(imageId, !isFavorite).toModel()
    }
}