package com.vangertorn.imagesapp.di.module

import android.content.Context
import androidx.room.Room
import com.vangertorn.imagesapp.data.database.ImageRoomDatabase
import com.vangertorn.imagesapp.data.database.dao.ImageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "image_db"

    @Provides
    @Singleton
    fun provideImageRoomDatabase(@ApplicationContext context: Context): ImageRoomDatabase {
        return Room.databaseBuilder(
            context,
            ImageRoomDatabase::class.java,
            DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideImageDao(imageRoomDatabase: ImageRoomDatabase): ImageDao {
        return imageRoomDatabase.imageDao()
    }
}
