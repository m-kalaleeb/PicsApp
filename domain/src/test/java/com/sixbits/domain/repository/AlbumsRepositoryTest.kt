package com.sixbits.domain.repository

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class AlbumsRepositoryTest {

    private var mockWebServer: MockWebServer? = null

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
    }

    @After
    fun tearDown() {
        mockWebServer?.shutdown()
    }

    @Test
    fun testJsonResponse(): Unit = runBlocking {
        val mockWebServer = mockWebServer!!

        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(sampleJsonResponse))

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        val repo = retrofit.create(AlbumsRepository::class.java)

        val albums = repo.getAlbums()

        assertEquals(1, albums.size)
        assertEquals(7, albums[0].id)
        assertEquals(25, albums[0].albumId)
    }


    val sampleJsonResponse = """
[
  {
    "albumId": 25,
    "id": 7,
    "title": "accusamus beatae ad facilis cum similique qui sunt",
    "url": "https://via.placeholder.com/600/92c952",
    "thumbnailUrl": "https://via.placeholder.com/150/92c952"
  }
]
    """.trimIndent()
}