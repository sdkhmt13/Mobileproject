package com.example.mobileproject.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class InMemoryBookRepository(
    initial: List<Book> = listOf(
        Book("1", "Dune", "Frank Herbert", "Sci-fi classic about Arrakis.", ReadingStatus.READING),
        Book("2", "1984", "George Orwell", "Dystopian novel about surveillance.", ReadingStatus.DONE)
    )
) : BookRepository {

    private val _shelf = MutableStateFlow(initial)
    override val shelf: Flow<List<Book>> = _shelf.asStateFlow()

    override fun observeBook(id: String): Flow<Book?> =
        _shelf.map { list -> list.find { it.id == id } }

    override suspend fun addBook(book: Book) {
        _shelf.update { list ->
            if (list.any { it.id == book.id }) list else list + book
        }
    }

    override suspend fun updateStatus(id: String, status: ReadingStatus) {
        _shelf.update { list ->
            list.map { if (it.id == id) it.copy(status = status) else it }
        }
    }

    override suspend fun removeBook(id: String) {
        _shelf.update { list -> list.filterNot { it.id == id } }
    }
}