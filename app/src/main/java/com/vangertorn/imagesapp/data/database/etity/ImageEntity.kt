package com.vangertorn.imagesapp.data.database.etity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image")
data class ImageEntity(
    @PrimaryKey
    val imageId: String,
    val url: String,
    val width: String,
    val height: String,
    val isFavorite: Boolean = false
)
