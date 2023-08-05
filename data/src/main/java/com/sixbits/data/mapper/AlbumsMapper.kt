package com.sixbits.data.mapper

import com.sixbits.domain.model.Album
import com.sixbits.domain.model.AlbumPhoto
import com.sixbits.domain.response.AlbumItemResponse
import com.sixbits.domain.response.AlbumPhotoItemResponse

fun AlbumItemResponse.toAlbum(): Album {
    return Album(
        id = id,
        title = title,
        userId = userId
    )
}

fun AlbumPhotoItemResponse.toAlbumPhoto(): AlbumPhoto {
    return AlbumPhoto(
        id, albumId, title, url, thumbnailUrl
    )
}