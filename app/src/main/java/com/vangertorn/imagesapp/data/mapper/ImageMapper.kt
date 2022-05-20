package com.vangertorn.imagesapp.data.mapper

import com.vangertorn.imagesapp.data.database.etity.ImageEntity
import com.vangertorn.imagesapp.data.network.dto.ImageDTO
import com.vangertorn.imagesapp.domain.model.ImageModel

fun List<ImageEntity>.toModel(): List<ImageModel> {
    val result = ArrayList<ImageModel>()
    forEach {
        result.add(it.toModel())
    }
    return result
}

fun ImageEntity.toModel() = ImageModel(
    id = imageId,
    url = url,
    isFavorite = isFavorite,
    height = height,
    width = width
)

fun List<ImageModel>.toEntity(): List<ImageEntity> {
    val result = ArrayList<ImageEntity>()
    forEach {
        result.add(it.toEntity())
    }
    return result
}

fun ImageModel.toEntity() = ImageEntity(
    imageId = id,
    url = url,
    isFavorite = isFavorite,
    height = height,
    width = width
)

fun List<ImageDTO>.toModelFromDTO(): List<ImageModel> {
    val result = ArrayList<ImageModel>()
    forEach {
        result.add(it.toModel())
    }
    return result
}

fun ImageDTO.toModel() = ImageModel(
    id = id,
    url = url,
    isFavorite = false,
    width = width.toString(),
    height = height.toString()
)
