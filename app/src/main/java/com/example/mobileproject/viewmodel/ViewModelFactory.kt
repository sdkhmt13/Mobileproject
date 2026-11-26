package com.example.mobileproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileproject.data.BookRepository
import com.example.mobileproject.data.BookSearchRepository
import com.example.mobileproject.data.FakeBookSearchRepository

class ViewModelFactory(
    private val repository: BookRepository,
    private val searchRepository: BookSearchRepository = FakeBookSearchRepository(),
    private val bookId: String? = null
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(ShelfViewModel::class.java) ->
            ShelfViewModel(repository)
        modelClass.isAssignableFrom(SearchViewModel::class.java) ->
            SearchViewModel(searchRepository, repository)
        modelClass.isAssignableFrom(BookDetailViewModel::class.java) ->
            BookDetailViewModel(repository, requireNotNull(bookId))
        else -> throw IllegalArgumentException("Unknown ViewModel: $modelClass")
    } as T
}