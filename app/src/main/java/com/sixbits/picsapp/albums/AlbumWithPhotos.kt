package com.sixbits.picsapp.albums

import com.sixbits.domain.model.Album
import com.sixbits.domain.model.AlbumPhoto

data class AlbumWithPhotos(
    val album: Album,
    val photos: List<AlbumPhoto>
) {
    val userId: Int get() = album.userId
    val id: Int get() = album.id
    val title: String get() = album.title

    val thumbnailUrl: String? = photos.firstOrNull()?.thumbnailUrl
}