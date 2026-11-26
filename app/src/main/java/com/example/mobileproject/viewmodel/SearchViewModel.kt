package com.example.mobileproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileproject.data.Book
import com.example.mobileproject.data.BookRepository
import com.example.mobileproject.data.BookSearchRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchRepository: BookSearchRepository,
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _results = MutableStateFlow<List<Book>>(emptyList())
    val results: StateFlow<List<Book>> = _results

    fun onQueryChange(value: String) {
        _query.value = value
        viewModelScope.launch { _results.value = searchRepository.search(value) }
    }

    fun addToShelf(book: Book) {
        viewModelScope.launch { bookRepository.addBook(book) }
    }
}