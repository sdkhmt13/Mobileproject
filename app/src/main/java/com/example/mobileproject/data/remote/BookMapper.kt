package com.example.mobileproject.data.remote

import com.example.mobileproject.data.Book

private const val COVERS_BASE_URL = "https://covers.openlibrary.org/b/id/"

fun BookDocDto.coverUrl(): String? =
    coverId?.let { "$COVERS_BASE_URL$it-M.jpg" }

fun BookDocDto.toBook(): Book? {
    val bookId = key?.substringAfterLast('/') ?: return null
    val bookTitle = title ?: return null
    return Book(
        id = bookId,
        title = bookTitle,
        author = authorNames?.firstOrNull() ?: "Unknown author",
        coverUrl = coverUrl(),
        year = firstPublishYear
    )
}