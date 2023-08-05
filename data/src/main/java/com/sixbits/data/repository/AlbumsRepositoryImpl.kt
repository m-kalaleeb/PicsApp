package com.sixbits.data.repository

import com.sixbits.data.mapper.toAlbum
import com.sixbits.data.mapper.toAlbumPhoto
import com.sixbits.domain.api.AlbumsApi
import com.sixbits.domain.model.Album
import com.sixbits.domain.model.AlbumPhoto
import com.sixbits.domain.repository.AlbumsRepository
import java.lang.Exception
import javax.inject.Inject

class AlbumsRepositoryImpl @Inject constructor(
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

    override suspend fun getAlbumPhotos(albumId: Int): Result<List<AlbumPhoto>> {
        return try {
            val albumsPhotosResult = albumsApi.getAlbumsPhotos(albumId)
            Result.success(
                albumsPhotosResult.map {
                    it.toAlbumPhoto()
                }
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}