package com.sixbits.domain.usecase

import com.sixbits.domain.model.Album
import com.sixbits.domain.model.AlbumPhoto
import com.sixbits.domain.repository.AlbumsRepository

class GetAlbumPhotosUseCase(
    private val albumsRepository: AlbumsRepository
) : AbstractUseCaseWithParams<Int, List<AlbumPhoto>>() {
    override suspend fun execute(params: Int): Result<List<AlbumPhoto>> {
        return albumsRepository.getAlbumPhotos(params)
    }
}