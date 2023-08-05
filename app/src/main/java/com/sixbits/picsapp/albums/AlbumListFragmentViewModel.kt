package com.sixbits.picsapp.albums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sixbits.domain.model.Album
import com.sixbits.domain.usecase.GetAlbumsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AlbumListFragmentViewModel @Inject constructor(
    private val getAlbumsUseCase: GetAlbumsUseCase
) : ViewModel() {

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> get() = _albums

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        viewModelScope.launch {
            val albumsResult = getAlbumsUseCase.execute()

            if (albumsResult.isSuccess) {
                val albums = albumsResult.getOrThrow()
                _albums.postValue(albums)
                Timber.d("Albums $albums")
            } else {
                _error.postValue(albumsResult.exceptionOrNull()?.message ?: "Unknown Error!!")
                Timber.e(albumsResult.exceptionOrNull())
            }
        }
    }
}