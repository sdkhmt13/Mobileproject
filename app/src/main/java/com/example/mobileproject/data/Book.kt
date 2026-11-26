package com.example.mobileproject.data

data class Book(
    val id: String,
    val title: String,
    val author: String,
    val description: String = "",
    val status: ReadingStatus = ReadingStatus.WANT,
    val coverUrl: String? = null,
    val year: Int? = null
)