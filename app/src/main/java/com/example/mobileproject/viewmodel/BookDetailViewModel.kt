package com.example.mobileproject.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mobileproject.data.Book
import com.example.mobileproject.data.BookRepository
import com.example.mobileproject.data.ReadingStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class BookDetailViewModel(
    private val repository: BookRepository,
    private val bookId: String
) : ViewModel() {

    private val _book = MutableStateFlow(repository.getBook(bookId))
    val book: StateFlow<Book?> = _book

    fun changeStatus(status: ReadingStatus) {
        repository.updateStatus(bookId, status)
        _book.value = repository.getBook(bookId)
    }

    fun remove() = repository.removeBook(bookId)
}