package com.sixbits.domain.api

import com.sixbits.domain.response.AlbumsListResponse
import retrofit2.http.GET

interface AlbumsApi {

    @GET("albums/1/photos")
    suspend fun getAlbums(): List<AlbumsListResponse>
}