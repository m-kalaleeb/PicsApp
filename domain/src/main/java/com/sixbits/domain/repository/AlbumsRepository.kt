package com.sixbits.domain.repository

import com.sixbits.domain.response.AlbumsListResponse
import retrofit2.http.GET

interface AlbumsRepository {

    @GET("albums/1/photos")
    suspend fun getAlbums(): List<AlbumsListResponse>
}