package com.example.mobileproject.data

import kotlinx.coroutines.flow.Flow

interface BookRepository {
    val shelf: Flow<List<Book>>
    suspend fun search(query: String): List<Book>
    fun getBook(id: String): Book?
    fun addBook(book: Book)
    fun updateStatus(id: String, status: ReadingStatus)
    fun removeBook(id: String)
}