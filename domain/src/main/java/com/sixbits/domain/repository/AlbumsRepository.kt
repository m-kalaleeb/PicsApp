package com.sixbits.domain.repository

import com.sixbits.domain.model.Album
import com.sixbits.domain.model.AlbumPhoto

interface AlbumsRepository {

    /**
     * Gets the albums list from the api
     *
     * @note this can be cached in Room to make an initial load, and get the albums
     * list when this is done, for now, I'll just use the API side.
     *
     * @note for now, I'm assuming that I'll get all the albums in a single request.
     * If this changes to a paginated request, offset will be needed in this call.
     */
    suspend fun getAlbums(): Result<List<Album>>

    /**
     * Gets a specific album photos from the api.
     */
    suspend fun getAlbumPhotos(albumId: Int): Result<List<AlbumPhoto>>
}