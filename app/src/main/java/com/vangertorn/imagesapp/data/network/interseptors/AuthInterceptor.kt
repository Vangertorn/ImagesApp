package com.vangertorn.imagesapp.data.network.interseptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request
            .newBuilder()
            .addHeader(AUTHORIZATION, API_KEY)
            .build()

        return chain.proceed(newRequest)
    }

    companion object {
        private const val API_KEY = "609352ed-c12c-48ee-a48e-421a5422502f"
        private const val AUTHORIZATION = "x-api-key"
    }
}