package com.example.mobileproject.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class InMemoryBookRepository : BookRepository {

    private val catalog = listOf(
        Book("1", "Dune", "Frank Herbert", "Sci-fi classic about Arrakis."),
        Book("2", "1984", "George Orwell", "Dystopian novel about surveillance."),
        Book("3", "The Hobbit", "J.R.R. Tolkien", "Bilbo's unexpected journey."),
        Book("4", "Atomic Habits", "James Clear", "Small habits, big results."),
        Book("5", "Clean Code", "Robert C. Martin", "A handbook of software craftsmanship.")
    )

    private val _shelf = MutableStateFlow(
        listOf(
            catalog[0].copy(status = ReadingStatus.READING),
            catalog[1].copy(status = ReadingStatus.DONE)
        )
    )
    override val shelf: Flow<List<Book>> = _shelf.asStateFlow()

    override suspend fun search(query: String): List<Book> =
        if (query.isBlank()) emptyList()
        else catalog.filter {
            it.title.contains(query, ignoreCase = true) ||
                    it.author.contains(query, ignoreCase = true)
        }

    override fun getBook(id: String): Book? =
        _shelf.value.find { it.id == id } ?: catalog.find { it.id == id }

    override fun addBook(book: Book) {
        _shelf.update { list ->
            if (list.any { it.id == book.id }) list else list + book
        }
    }

    override fun updateStatus(id: String, status: ReadingStatus) {
        _shelf.update { list ->
            list.map { if (it.id == id) it.copy(status = status) else it }
        }
    }

    override fun removeBook(id: String) {
        _shelf.update { list -> list.filterNot { it.id == id } }
    }
}