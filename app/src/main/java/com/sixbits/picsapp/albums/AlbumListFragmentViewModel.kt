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
        val albumsList = albumsList ?: return

        viewModelScope.launch {
            _isLoading.postValue(true)
            if (albumsList.size > albumsWithPhotosList.size) {

                val photosList = albumsList.subList(0, albumsWithPhotosList.size + pageSize).map {
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
                _albums.postValue(albumsWithPhotosList)
                _isLoading.postValue(false)
            }
        }
    }
}