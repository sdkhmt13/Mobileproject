package com.example.mobileproject.data.remote

import com.example.mobileproject.data.Book
import com.example.mobileproject.data.BookSearchRepository

class OpenLibraryBookSearchRepository(
    private val api: OpenLibraryApi
) : BookSearchRepository {

    override suspend fun search(query: String): List<Book> {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) return emptyList()
        return api.search(trimmed).docs.mapNotNull { it.toBook() }
    }
}