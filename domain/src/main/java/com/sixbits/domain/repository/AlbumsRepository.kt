package com.sixbits.domain.repository

import com.sixbits.domain.model.Album

interface AlbumsRepository {

    /**
     * Gets the albums list from the api
     *
     * @note this can be cached in Room to make an initial load, and get the albums
     * list when this is done, for now, I'll just use the API side.
     */
    suspend fun getAlbums(): Result<List<Album>>
}