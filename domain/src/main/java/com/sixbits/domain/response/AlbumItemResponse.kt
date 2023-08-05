package com.sixbits.domain.response

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlbumItemResponse(
    val userId: Int,
    val id: Int,
    val title: String,
)
