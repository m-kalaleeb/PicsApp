package com.sixbits.data.repository

import com.sixbits.data.mapper.toAlbum
import com.sixbits.domain.api.AlbumsApi
import com.sixbits.domain.model.Album
import com.sixbits.domain.repository.AlbumsRepository
import java.lang.Exception

class AlbumsRepositoryImpl(
    private val albumsApi: AlbumsApi
) : AlbumsRepository {

    override suspend fun getAlbums(): Result<List<Album>> {
        return try {
            val albumsResult = albumsApi.getAlbums()
            Result.success(
                albumsResult.map {
                    it.toAlbum()
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}