package com.vangertorn.imagesapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vangertorn.imagesapp.data.database.etity.ImageEntity

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addImages(images: List<ImageEntity>)

    @Query("SELECT * FROM image")
    fun getImages(): List<ImageEntity>

}