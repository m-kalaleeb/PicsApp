package com.sixbits.domain.api

import com.sixbits.domain.response.AlbumItemResponse
import com.sixbits.domain.response.AlbumPhotoItemResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumsApi {

    @GET("albums/{album_id}/photos")
    suspend fun getAlbumsPhotos(@Path("album_id") id: Int): List<AlbumPhotoItemResponse>

    @GET("albums")
    suspend fun getAlbums(): List<AlbumItemResponse>
}