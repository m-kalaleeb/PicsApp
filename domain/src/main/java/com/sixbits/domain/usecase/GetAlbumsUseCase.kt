package com.sixbits.domain.usecase

import com.sixbits.domain.model.Album
import com.sixbits.domain.repository.AlbumsRepository

class GetAlbumsUseCase(
    private val albumsRepository: AlbumsRepository
) : AbstractUseCase<List<Album>>() {
    override suspend fun execute(): Result<List<Album>> {
        return albumsRepository.getAlbums()
    }
}