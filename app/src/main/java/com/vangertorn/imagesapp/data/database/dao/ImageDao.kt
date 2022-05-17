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

    @Query("SELECT * FROM image WHERE isCurrentUpdate = :isCurrentUpdate")
    fun getImages(isCurrentUpdate: Boolean = true): List<ImageEntity>

    @Query("UPDATE image SET isFavorite =:isNewFavorite WHERE imageId =:imageId")
    fun switchFavorite(imageId: String, isNewFavorite: Boolean)

    @Transaction
    fun changeFavorite(imageId: String, isNewFavorite: Boolean): List<ImageEntity> {
        switchFavorite(imageId, isNewFavorite)
        return getImages()
    }

    @Query("DELETE FROM image WHERE isCurrentUpdate = :isCurrentUpdate AND isFavorite =:isFavorite")
    fun clearImage(isCurrentUpdate: Boolean = true, isFavorite: Boolean = false)

    @Query("UPDATE image SET isCurrentUpdate = :isCurrentUpdate")
    fun updateCurrentState(isCurrentUpdate: Boolean = false)

    @Transaction
    fun saveImagesDatabase(images: List<ImageEntity>) {
        clearImage()
        updateCurrentState()
        addImages(images)
    }

    @Query("SELECT * FROM image WHERE isFavorite = :isFavorite")
    fun getFavoriteImages(isFavorite: Boolean = true): List<ImageEntity>
}
