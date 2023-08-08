package com.sixbits.domain.usecase

import com.sixbits.domain.model.AlbumPhoto
import com.sixbits.domain.repository.AlbumsRepository

/**
 * GetAlbumPhotosUseCase
 *
 * a use case to get a specific album photos from the BE
 */
class GetAlbumPhotosUseCase(
    private val albumsRepository: AlbumsRepository
) : AbstractUseCaseWithParams<Int, List<AlbumPhoto>>() {
    override suspend fun execute(params: Int): Result<List<AlbumPhoto>> {
        return albumsRepository.getAlbumPhotos(params)
    }
}