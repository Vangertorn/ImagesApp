package com.vangertorn.imagesapp.di.module

import android.content.Context
import android.net.ConnectivityManager
import com.vangertorn.imagesapp.BuildConfig
import com.vangertorn.imagesapp.data.network.interseptors.AuthInterceptor
import com.vangertorn.imagesapp.data.network.state.NetworkState
import com.vangertorn.imagesapp.data.network.state.NetworkStateImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .addInterceptor(authInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesNetworkConnectionState(
        @ApplicationContext context: Context
    ): NetworkState =
        NetworkStateImpl(
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        )
}
