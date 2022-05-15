package com.vangertorn.imagesapp.domain.usecase

import com.vangertorn.imagesapp.domain.model.ImageModel
import com.vangertorn.imagesapp.domain.repository.ImageRepository
import com.vangertorn.imagesapp.util.Source
import javax.inject.Inject

class GetImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {
    suspend fun execute(): List<ImageModel> {

        return imageRepository.getImagesFromLocal().ifEmpty {
            imageRepository.getImagesFromNetwork()
        }
    }
}