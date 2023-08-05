package com.sixbits.domain.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlbumsListResponse(
    val id: Int,
    val albumId: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
)