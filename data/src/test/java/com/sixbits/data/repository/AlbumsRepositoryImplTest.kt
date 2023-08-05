package com.sixbits.data.repository

import com.sixbits.domain.api.AlbumsApi
import com.sixbits.domain.response.AlbumsItemResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.io.IOException

class AlbumsRepositoryImplTest {

    @Test
    fun `get albums successfully`(): Unit = runBlocking {
        val fakeApi = object : AlbumsApi {
            override suspend fun getAlbums(): List<AlbumsItemResponse> {
                return listOf(
                    AlbumsItemResponse(
                        id = 5,
                        albumId = 7,
                        url = "https://test.com",
                        thumbnailUrl = "https://test.com/thumbnail",
                        title = "Amazing title"
                    )
                )
            }
        }

        val repo = AlbumsRepositoryImpl(fakeApi)

        val albumsResult = repo.getAlbums()

        assert(albumsResult.isSuccess)

        val albumsList = albumsResult.getOrThrow()
        assertEquals(5, albumsList[0].id)
        assertEquals(7, albumsList[0].albumId)
        assertEquals("https://test.com", albumsList[0].url)
        assertEquals("https://test.com/thumbnail", albumsList[0].thumbnailUrl)
        assertEquals("Amazing title", albumsList[0].title)
    }

    @Test
    fun `get albums with IO Exception`(): Unit = runBlocking {
        val fakeApi = object : AlbumsApi {
            override suspend fun getAlbums(): List<AlbumsItemResponse> {
                throw IOException()
            }
        }

        val repo = AlbumsRepositoryImpl(fakeApi)

        val result = repo.getAlbums()

        assert(result.isFailure)
        assert(result.exceptionOrNull() is IOException)
    }
}