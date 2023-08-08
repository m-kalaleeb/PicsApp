package com.sixbits.picsapp.albums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sixbits.domain.model.Album
import com.sixbits.domain.model.AlbumPhoto
import com.sixbits.domain.repository.AlbumsRepository
import com.sixbits.domain.usecase.GetAlbumPhotosUseCase
import com.sixbits.domain.usecase.GetAlbumsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.stub
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class AlbumListFragmentViewModelTest {

    @Mock
    lateinit var albumsRepository: AlbumsRepository

    @get:Rule
    val instantExecution = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun `when requesting more, the current list plus 10 more should be provided`(): Unit = runTest {
        // Given our API only has n albums
        val albumsNumber = 27

        albumsRepository.stub {
            onBlocking { getAlbums() }.thenReturn(Result.success(generateAlbumList(albumsNumber)))
        }
        albumsRepository.stub {
            onBlocking {
                getAlbumPhotos(any())
            }.thenReturn(Result.success(generateAlbumPhotoList()))
        }

        val vm = AlbumListFragmentViewModel(
            GetAlbumsUseCase(albumsRepository),
            GetAlbumPhotosUseCase(albumsRepository)
        )

        advanceUntilIdle()

        verify(albumsRepository, times(1))
            .getAlbums()
        verify(albumsRepository, times(10))
            .getAlbumPhotos(any())
        assertEquals(10, vm.albums.value!!.size)

        // When requesting more, we should get another portion of the albums with photos.
        vm.requestMore()
        advanceUntilIdle()

        // Make sure we are not requesting new data.
        verify(albumsRepository, times(1))
            .getAlbums()
        verify(albumsRepository, times(20))
            .getAlbumPhotos(any())

        assertEquals(20, vm.albums.value!!.size)

        // when we request even more albums
        vm.requestMore()
        advanceUntilIdle()

        // Then we should get the full list of albums
        verify(albumsRepository, times(1))
            .getAlbums()
        assertEquals(albumsNumber, vm.albums.value!!.size)

        // And requests to the albums photo endpoint should match visible albums
        verify(albumsRepository, times(albumsNumber))
            .getAlbumPhotos(any())
    }

    private fun generateAlbumPhotoList(): List<AlbumPhoto> {
        return listOf(
            AlbumPhoto(
                id = Random.nextInt(),
                albumId = Random.nextInt(),
                title = "Title",
                url = "url",
                thumbnailUrl = "Thumbnail"
            )
        )
    }

    private fun generateAlbumList(listSize: Int): List<Album> {
        val albumList = mutableListOf<Album>()
        for (i in 0 until listSize) {
            albumList.add(generateAlbum())
        }

        return albumList
    }

    private fun generateAlbum(): Album {
        return Album(
            userId = 1,
            id = Random.nextInt(),
            title = "Title"
        )
    }
}