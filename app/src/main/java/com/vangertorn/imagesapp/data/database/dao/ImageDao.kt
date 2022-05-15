package com.vangertorn.imagesapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.vangertorn.imagesapp.data.database.etity.ImageEntity

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addImages(images: List<ImageEntity>)

    @Query("SELECT * FROM image")
    fun getImages(): List<ImageEntity>

    @Query("UPDATE image SET isFavorite =:isNewFavorite WHERE imageId =:imageId")
    fun switchFavorite(imageId: String, isNewFavorite: Boolean)

    @Transaction
    fun changeFavorite(imageId: String, isNewFavorite: Boolean): List<ImageEntity> {
        switchFavorite(imageId, isNewFavorite)
        return getImages()
    }
}