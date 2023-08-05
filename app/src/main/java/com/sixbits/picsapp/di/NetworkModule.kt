package com.sixbits.picsapp.di

import com.sixbits.data.repository.AlbumsRepositoryImpl
import com.sixbits.domain.api.AlbumsApi
import com.sixbits.domain.repository.AlbumsRepository
import com.sixbits.picsapp.config.Config
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient()
    }


    @Provides
    @Singleton
    @AlbumsRetrofit
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Config.ALBUMS_API)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAlbumsApi(
        @AlbumsRetrofit retrofit: Retrofit
    ): AlbumsApi = retrofit.create()

    // TODO: Make this a bind
    @Provides
    @Singleton
    fun provideAlbumsRepository(api: AlbumsApi): AlbumsRepository = AlbumsRepositoryImpl(api)
}
