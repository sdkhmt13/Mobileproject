package com.example.mobileproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileproject.data.Book
import com.example.mobileproject.data.BookRepository
import com.example.mobileproject.data.ReadingStatus
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val repository: BookRepository,
    private val bookId: String
) : ViewModel() {


    val book: StateFlow<Book?> = repository.observeBook(bookId)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun changeStatus(status: ReadingStatus) {
        viewModelScope.launch { repository.updateStatus(bookId, status) }
    }

    fun remove(onRemoved: () -> Unit) {
        viewModelScope.launch {
            repository.removeBook(bookId)
            onRemoved()
        }
    }
}