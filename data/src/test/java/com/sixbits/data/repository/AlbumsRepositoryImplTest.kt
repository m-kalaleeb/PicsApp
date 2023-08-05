package com.sixbits.data.repository

import com.sixbits.domain.api.AlbumsApi
import com.sixbits.domain.response.AlbumItemResponse
import com.sixbits.domain.response.AlbumPhotoItemResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException

class AlbumsRepositoryImplTest {

    @Test
    fun `get albums successfully`(): Unit = runBlocking {
        val fakeApi = object : AlbumsApi {
            override suspend fun getAlbums(): List<AlbumItemResponse> {
                return listOf(
                    AlbumItemResponse(
                        id = 5,
                        userId = 7,
                        title = "Amazing title"
                    )
                )
            }

            override suspend fun getAlbumsPhotos(id: Int): List<AlbumPhotoItemResponse> {
                return emptyList()
            }
        }

        val repo = AlbumsRepositoryImpl(fakeApi)

        val albumsResult = repo.getAlbums()

        assert(albumsResult.isSuccess)

        val albumsList = albumsResult.getOrThrow()
        assertEquals(5, albumsList[0].id)
        assertEquals(7, albumsList[0].userId)
        assertEquals("Amazing title", albumsList[0].title)
    }

    @Test
    fun `get albums with IO Exception`(): Unit = runBlocking {
        val fakeApi = object : AlbumsApi {
            override suspend fun getAlbums(): List<AlbumItemResponse> {
                throw IOException()
            }

            override suspend fun getAlbumsPhotos(id: Int): List<AlbumPhotoItemResponse> {
                return emptyList()
            }
        }

        val repo = AlbumsRepositoryImpl(fakeApi)

        val result = repo.getAlbums()

        assert(result.isFailure)
        assert(result.exceptionOrNull() is IOException)
    }
}