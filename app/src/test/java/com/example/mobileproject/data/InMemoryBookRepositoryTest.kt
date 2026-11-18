package com.example.mobileproject.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class InMemoryBookRepositoryTest {

    private lateinit var repo: InMemoryBookRepository

    @Before
    fun setUp() {
        repo = InMemoryBookRepository()
    }

    @Test
    fun shelf_startsWithSampleBooks() = runTest {
        assertEquals(2, repo.shelf.first().size)
    }

    @Test
    fun addBook_addsNewBookToShelf() = runTest {
        repo.addBook(Book("3", "The Hobbit", "J.R.R. Tolkien"))
        assertEquals(3, repo.shelf.first().size)
    }

    @Test
    fun addBook_doesNotAddDuplicate() = runTest {
        repo.addBook(Book("1", "Dune", "Frank Herbert"))
        assertEquals(2, repo.shelf.first().size)
    }

    @Test
    fun updateStatus_changesStatusOfBook() = runTest {
        repo.updateStatus("1", ReadingStatus.DONE)
        assertEquals(ReadingStatus.DONE, repo.getBook("1")?.status)
    }

    @Test
    fun removeBook_removesFromShelf() = runTest {
        repo.removeBook("1")
        assertNull(repo.shelf.first().find { it.id == "1" })
    }

    @Test
    fun search_findsByTitleIgnoringCase() = runTest {
        val results = repo.search("hobbit")
        assertEquals(1, results.size)
        assertEquals("The Hobbit", results.first().title)
    }

    @Test
    fun search_blankQueryReturnsEmptyList() = runTest {
        assertTrue(repo.search("  ").isEmpty())
    }
}