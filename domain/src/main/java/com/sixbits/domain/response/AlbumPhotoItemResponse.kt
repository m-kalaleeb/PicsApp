package com.sixbits.domain.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlbumPhotoItemResponse(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
)