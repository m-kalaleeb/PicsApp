package com.sixbits.domain.model

data class AlbumPhoto(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
)
