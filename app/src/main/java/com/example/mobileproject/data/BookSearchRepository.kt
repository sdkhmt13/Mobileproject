package com.example.mobileproject.data

interface BookSearchRepository {
    suspend fun search(query: String): List<Book>
}