package com.vangertorn.imagesapp.di.module

import com.vangertorn.imagesapp.data.repository.ImageRepositoryImpl
import com.vangertorn.imagesapp.domain.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {

    @Binds
    fun bindImageRepository(imageRepositoryImpl: ImageRepositoryImpl): ImageRepository
}
