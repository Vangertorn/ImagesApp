package com.vangertorn.imagesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vangertorn.imagesapp.data.database.dao.ImageDao
import com.vangertorn.imagesapp.data.database.etity.ImageEntity

@Database(
    version = 1,
    entities = [ImageEntity::class],
    exportSchema = true
)
abstract class ImageRoomDatabase : RoomDatabase() {

    abstract fun imageDao(): ImageDao
}
