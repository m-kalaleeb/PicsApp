package com.sixbits.data.mapper

import com.sixbits.domain.model.Album
import com.sixbits.domain.response.AlbumsListResponse

fun AlbumsListResponse.toAlbum(): Album {
    return Album(
        id = id,
        albumId = albumId,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
    )
}