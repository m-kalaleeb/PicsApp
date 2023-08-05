package com.sixbits.picsapp.di

import com.sixbits.data.repository.AlbumsRepositoryImpl
import com.sixbits.domain.api.AlbumsApi
import com.sixbits.domain.repository.AlbumsRepository
import com.sixbits.domain.usecase.GetAlbumPhotosUseCase
import com.sixbits.domain.usecase.GetAlbumsUseCase
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
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.HEADERS

        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .callTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
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

    // Not a singleton, to allow for distinct requests
    @Provides
    fun provideAlbumsUseCase(
        albumsRepository: AlbumsRepository
    ): GetAlbumsUseCase {
        return GetAlbumsUseCase(albumsRepository = albumsRepository)
    }

    @Provides
    fun provideAlbumPhotosUseCase(
        albumsRepository: AlbumsRepository
    ): GetAlbumPhotosUseCase {
        return GetAlbumPhotosUseCase(albumsRepository)
    }
}


@Module
@InstallIn(SingletonComponent::class)
interface NetworkModuleBinds {
    @Binds
    @Singleton
    fun bindAlbumsRepository(impl: AlbumsRepositoryImpl): AlbumsRepository
}