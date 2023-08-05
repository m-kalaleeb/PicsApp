package com.sixbits.domain.api

import com.sixbits.domain.response.AlbumsItemResponse
import retrofit2.http.GET

interface AlbumsApi {

    @GET("albums/1/photos")
    suspend fun getAlbums(): List<AlbumsItemResponse>
}