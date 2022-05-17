package com.vangertorn.imagesapp.data.repository

import com.vangertorn.imagesapp.data.source.factory.DataSourceFactory
import com.vangertorn.imagesapp.domain.model.ImageModel
import com.vangertorn.imagesapp.domain.repository.ImageRepository
import com.vangertorn.imagesapp.util.Source
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val dataSourceFactory: DataSourceFactory
) : ImageRepository {

    override suspend fun getImagesFromNetwork(): List<ImageModel> {
        return dataSourceFactory.create(Source.NETWORK).getImages()
    }

    override suspend fun getImagesFromLocal(): List<ImageModel> {
        return dataSourceFactory.create(Source.LOCAL).getImages()
    }

    override suspend fun saveImagesDatabase(images: List<ImageModel>) {
        dataSourceFactory.create(Source.LOCAL).saveImagesDatabase(images)
    }

    override suspend fun changeFavorite(imageId: String, isFavorite: Boolean): List<ImageModel> {
        return dataSourceFactory.create(Source.LOCAL).changeFavorite(imageId, isFavorite)
    }

    override suspend fun getDetails(imageId: String): ImageModel {
       return dataSourceFactory.create(Source.NETWORK).getDetails(imageId)
    }
}