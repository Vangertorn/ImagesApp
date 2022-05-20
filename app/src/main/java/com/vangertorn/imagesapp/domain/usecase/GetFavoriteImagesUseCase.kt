package com.vangertorn.imagesapp.domain.usecase

import com.vangertorn.imagesapp.domain.model.ImageModel
import com.vangertorn.imagesapp.domain.repository.ImageRepository
import javax.inject.Inject

class GetFavoriteImagesUseCase@Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend fun execute(): List<ImageModel> {
        return imageRepository.getFavoriteImages()
    }
}
