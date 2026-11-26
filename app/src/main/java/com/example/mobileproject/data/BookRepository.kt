package com.example.mobileproject.data

import kotlinx.coroutines.flow.Flow

interface BookRepository {
    val shelf: Flow<List<Book>>
    fun observeBook(id: String): Flow<Book?>
    suspend fun addBook(book: Book)
    suspend fun updateStatus(id: String, status: ReadingStatus)
    suspend fun removeBook(id: String)
}