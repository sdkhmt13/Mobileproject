package com.example.mobileproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mobileproject.AppContainer

class ViewModelFactory(
    private val container: AppContainer,
    private val bookId: String? = null
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(ShelfViewModel::class.java) ->
            ShelfViewModel(container.bookRepository)
        modelClass.isAssignableFrom(SearchViewModel::class.java) ->
            SearchViewModel(container.searchRepository, container.bookRepository)
        modelClass.isAssignableFrom(BookDetailViewModel::class.java) ->
            BookDetailViewModel(container.bookRepository, requireNotNull(bookId))
        else -> throw IllegalArgumentException("Unknown ViewModel: $modelClass")
    } as T
}