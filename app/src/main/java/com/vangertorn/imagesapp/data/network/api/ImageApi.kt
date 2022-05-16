package com.vangertorn.imagesapp.data.network.api

import com.vangertorn.imagesapp.data.network.dto.ImageDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageApi {

    @GET("/v1/images/search")
    suspend fun getRandomImage(): List<ImageDTO>

    @GET("/v1/images/search")
    suspend fun getRandomImages(
        @Query("limit") limit: Int
    ): List<ImageDTO>

    @GET("/v1/images/{id}")
    suspend fun getDetails(
        @Path("id") id: String
    ): ImageDTO
}