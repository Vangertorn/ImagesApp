package com.vangertorn.imagesapp.data.network.api

import com.vangertorn.imagesapp.data.network.dto.ImageDTO
import retrofit2.http.GET

interface ImageApi {

    @GET("/v1/images/search")
    suspend fun getRandomImage(): List<ImageDTO>
}