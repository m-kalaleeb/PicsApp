package com.sixbits.data.mapper

import com.sixbits.domain.response.AlbumItemResponse
import com.sixbits.domain.response.AlbumPhotoItemResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class AlbumsMapperTest {

    @Test
    fun testAlbumPhotosMapping() {
        val albumResponse = AlbumPhotoItemResponse(
            id = 5,
            albumId = 7,
            url = "https://test.com",
            thumbnailUrl = "https://test.com/thumbnail",
            title = "Amazing title"
        )

        val albumItem = albumResponse.toAlbumPhoto()

        assertEquals(5, albumItem.id)
        assertEquals(7, albumItem.albumId)
        assertEquals("https://test.com", albumItem.url)
        assertEquals("https://test.com/thumbnail", albumItem.thumbnailUrl)
        assertEquals("Amazing title", albumItem.title)
    }

    @Test
    fun testAlbumMapping() {
        val albumResponse = AlbumItemResponse(
            id = 5,
            userId = 7,
            title = "Amazing title"
        )

        val albumItem = albumResponse.toAlbum()

        assertEquals(5, albumItem.id)
        assertEquals(7, albumItem.userId)
        assertEquals("Amazing title", albumItem.title)
    }
}