package com.example.mobileproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileproject.data.Book
import com.example.mobileproject.data.BookRepository
import com.example.mobileproject.data.ReadingStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ShelfViewModel(private val repository: BookRepository) : ViewModel() {

    private val _filter = MutableStateFlow<ReadingStatus?>(null)
    val filter: StateFlow<ReadingStatus?> = _filter

    val books: StateFlow<List<Book>> =
        combine(repository.shelf, _filter) { list, filter ->
            if (filter == null) list else list.filter { it.status == filter }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun setFilter(status: ReadingStatus?) {
        _filter.value = status
    }

    fun changeStatus(id: String, status: ReadingStatus) =
        repository.updateStatus(id, status)

    fun removeBook(id: String) = repository.removeBook(id)
}