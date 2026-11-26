package com.example.mobileproject.data.local

import com.example.mobileproject.data.Book
import com.example.mobileproject.data.BookRepository
import com.example.mobileproject.data.ReadingStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomBookRepository(private val dao: BookDao) : BookRepository {

    override val shelf: Flow<List<Book>> =
        dao.observeAll().map { list -> list.map { it.toBook() } }

    override fun observeBook(id: String): Flow<Book?> =
        dao.observeById(id).map { it?.toBook() }

    override suspend fun addBook(book: Book) = dao.insert(book.toEntity())

    override suspend fun updateStatus(id: String, status: ReadingStatus) {
        val current = dao.getById(id) ?: return
        dao.update(current.copy(status = status))
    }

    override suspend fun removeBook(id: String) = dao.deleteById(id)
}