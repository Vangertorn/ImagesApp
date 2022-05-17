package com.vangertorn.imagesapp.data.source.factory

import com.vangertorn.imagesapp.data.source.ImagesData
import com.vangertorn.imagesapp.data.source.local.LocalImagesData
import com.vangertorn.imagesapp.data.source.network.NetworkImagesData
import com.vangertorn.imagesapp.util.Source
import javax.inject.Inject

class DataSourceFactory @Inject constructor(
    private val networkImagesData: NetworkImagesData,
    private val localImagesData: LocalImagesData
) {

    fun create(source: Source): ImagesData {
        return when (source) {
            Source.LOCAL -> localImagesData
            Source.NETWORK -> networkImagesData
        }
    }
}
