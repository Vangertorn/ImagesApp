package com.vangertorn.imagesapp.domain.usecase

import com.vangertorn.imagesapp.domain.model.ImageModel
import com.vangertorn.imagesapp.domain.repository.ImageRepository
import javax.inject.Inject

class GetRandomImagesUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend fun execute(): List<ImageModel>? {
        val images = imageRepository.getImagesFromNetwork()
        return if (images.isNotEmpty()) {
            imageRepository.saveImagesDatabase(images)
            images
        } else null
    }
}
