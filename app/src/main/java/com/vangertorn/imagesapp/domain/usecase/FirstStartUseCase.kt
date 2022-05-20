package com.vangertorn.imagesapp.domain.usecase

import com.vangertorn.imagesapp.domain.repository.ImageRepository
import javax.inject.Inject

class FirstStartUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend fun execute() {
        if (imageRepository.getImagesFromLocal().isEmpty()) {
            val images = imageRepository.getImagesFromNetwork()
            if (images.isNotEmpty()) {
                imageRepository.saveImagesDatabase(images)
            }
        }
    }
}
