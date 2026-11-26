package com.example.mobileproject.data.remote

import com.google.gson.annotations.SerializedName

data class SearchResponseDto(
    val docs: List<BookDocDto> = emptyList()
)

data class BookDocDto(
    val key: String? = null,
    val title: String? = null,
    @SerializedName("author_name") val authorNames: List<String>? = null,
    @SerializedName("first_publish_year") val firstPublishYear: Int? = null,
    @SerializedName("cover_i") val coverId: Long? = null
)