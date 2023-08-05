package com.sixbits.data.mapper

import com.sixbits.domain.model.Album
import com.sixbits.domain.response.AlbumsItemResponse

fun AlbumsItemResponse.toAlbum(): Album {
    return Album(
        id = id,
        albumId = albumId,
        title = title,
        url = url,
        thumbnailUrl = thumbnailUrl
    )
}