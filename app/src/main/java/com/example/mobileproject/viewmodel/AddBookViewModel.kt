package com.example.mobileproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileproject.data.Book
import com.example.mobileproject.data.BookRepository
import com.example.mobileproject.data.ReadingStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

data class AddBookUiState(
    val title: String = "",
    val author: String = "",
    val year: String = "",
    val status: ReadingStatus = ReadingStatus.WANT,
    val titleError: String? = null,
    val authorError: String? = null,
    val yearError: String? = null
)

class AddBookViewModel(private val repository: BookRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(AddBookUiState())
    val uiState: StateFlow<AddBookUiState> = _uiState.asStateFlow()

    fun onTitleChange(value: String) {
        _uiState.update { it.copy(title = value, titleError = null) }
    }

    fun onAuthorChange(value: String) {
        _uiState.update { it.copy(author = value, authorError = null) }
    }

    fun onYearChange(value: String) {
        // Only digits, at most 4
        _uiState.update { it.copy(year = value.filter { ch -> ch.isDigit() }.take(4), yearError = null) }
    }

    fun onStatusChange(status: ReadingStatus) {
        _uiState.update { it.copy(status = status) }
    }

    fun save(onSaved: () -> Unit) {
        val state = _uiState.value
        val year = state.year.toIntOrNull()

        val titleError = if (state.title.isBlank()) "Enter a title" else null
        val authorError = if (state.author.isBlank()) "Enter an author" else null
        val yearError =
            if (state.year.isNotEmpty() && (year == null || year !in 1..2100)) "Enter a valid year" else null

        if (titleError != null || authorError != null || yearError != null) {
            _uiState.update {
                it.copy(titleError = titleError, authorError = authorError, yearError = yearError)
            }
            return
        }

        viewModelScope.launch {
            repository.addBook(
                Book(
                    id = "manual-${UUID.randomUUID()}",
                    title = state.title.trim(),
                    author = state.author.trim(),
                    status = state.status,
                    year = year
                )
            )
            onSaved()
        }
    }
}