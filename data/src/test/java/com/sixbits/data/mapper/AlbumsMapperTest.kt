package com.sixbits.data.mapper

import com.sixbits.domain.response.AlbumsItemResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class AlbumsMapperTest {

    @Test
    fun testAlbumMapping() {
        val albumResponse = AlbumsItemResponse(
            id = 5,
            albumId = 7,
            url = "https://test.com",
            thumbnailUrl = "https://test.com/thumbnail",
            title = "Amazing title"
        )

        val albumItem = albumResponse.toAlbum()

        assertEquals(5, albumItem.id)
        assertEquals(7, albumItem.albumId)
        assertEquals("https://test.com", albumItem.url)
        assertEquals("https://test.com/thumbnail", albumItem.thumbnailUrl)
        assertEquals("Amazing title", albumItem.title)
    }
}