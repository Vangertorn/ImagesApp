package com.vangertorn.imagesapp.domain.usecase

import com.vangertorn.imagesapp.domain.model.ImageModel
import com.vangertorn.imagesapp.domain.repository.ImageRepository
import javax.inject.Inject

class ChangeFavoriteUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend fun execute(imageId: String, isFavorite: Boolean): List<ImageModel> {
        return imageRepository.changeFavorite(imageId, isFavorite)
    }
}
