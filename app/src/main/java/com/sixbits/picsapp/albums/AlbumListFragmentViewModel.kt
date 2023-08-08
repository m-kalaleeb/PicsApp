package com.sixbits.picsapp.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sixbits.domain.model.Album
import com.sixbits.domain.usecase.GetAlbumPhotosUseCase
import com.sixbits.domain.usecase.GetAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Albums List ViewModel
 *
 * Note here, I'm using an In-Memory caching strategy.
 * I don't expect users to scroll much in this list, and to use a search
 * feature or a pagination (with a given offset or a page number) if there is a lot
 * of data that needs to scroll through. Hence this approach work.
 *
 * Simply, from a product perspective, you don't want users to scroll a lot, through a data
 * list they need and then if they close the app to loose their progress.
 *
 * Obviously this use case differs in terms of social media apps where the feed is changing.
 *
 * @param getAlbumsUseCase a use case to get all albums from the BE
 * @param getAlbumPhotosUseCase a use case to get a specific album photos from the BE
 *
 */
@HiltViewModel
class AlbumListFragmentViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase,
    private val getAlbumPhotosUseCase: GetAlbumPhotosUseCase
) : ViewModel() {

    private val _albums = MutableLiveData<List<AlbumWithPhotos>>()
    val albums: LiveData<List<AlbumWithPhotos>> get() = _albums

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    // A cache to get fake pagination from
    private var albumsList: List<Album>? = null
    private var albumsWithPhotosList = mutableListOf<AlbumWithPhotos>()
    private val pageSize = 10

    init {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val albumsResult = getAlbumsUseCase.execute()

            if (albumsResult.isFailure) {
                _error.postValue(albumsResult.exceptionOrNull()?.message ?: "Unknown Error!!")
                Timber.e(albumsResult.exceptionOrNull())
                _isLoading.postValue(false)
                return@launch
            }

            val albums = albumsResult.getOrThrow()
            albumsList = albums

            val photosList = albums.take(pageSize).map {
                async {
                    val res = getAlbumPhotosUseCase.execute(it.id)
                    return@async if (res.isSuccess) {
                        AlbumWithPhotos(
                            id = it.id,
                            title = it.title,
                            thumbnailUrl = res.getOrThrow().firstOrNull()?.thumbnailUrl
                        )
                    } else {
                        null
                    }
                }
            }.awaitAll().filterNotNull()

            albumsWithPhotosList.addAll(photosList)

            _isLoading.postValue(false)
            _albums.postValue(photosList)
        }
    }

    fun requestMore() {
        Timber.d("Requesting More")
        val albumsList = albumsList ?: return

        viewModelScope.launch {
            _isLoading.postValue(true)
            if (albumsList.size > albumsWithPhotosList.size) {

                val pageEnd  = if (albumsWithPhotosList.size + pageSize < albumsList.size) {
                    albumsWithPhotosList.size + pageSize
                } else {
                    albumsList.size
                }

                val newPage = albumsList.subList(albumsWithPhotosList.size, pageEnd)

                val photosList = newPage.map {
                    async {
                        val res = getAlbumPhotosUseCase.execute(it.id)
                        return@async if (res.isSuccess) {
                            AlbumWithPhotos(
                                id = it.id,
                                title = it.title,
                                thumbnailUrl = res.getOrThrow().firstOrNull()?.thumbnailUrl
                            )
                        } else {
                            null
                        }
                    }
                }.awaitAll().filterNotNull()

                albumsWithPhotosList.addAll(photosList)
                _albums.postValue(albumsWithPhotosList.toList())
                _isLoading.postValue(false)
            }
        }
    }
}