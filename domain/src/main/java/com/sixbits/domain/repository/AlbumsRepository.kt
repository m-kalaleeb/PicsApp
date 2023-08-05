package com.sixbits.domain.repository

import com.sixbits.domain.model.Album

interface AlbumsRepository {

    suspend fun getAlbums(): List<Album>
}