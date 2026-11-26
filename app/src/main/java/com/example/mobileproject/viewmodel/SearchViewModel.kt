package com.example.mobileproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileproject.data.Book
import com.example.mobileproject.data.BookRepository
import com.example.mobileproject.data.BookSearchRepository
import com.example.mobileproject.ui.state.SearchUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn


private const val SEARCH_DEBOUNCE_MS = 500L

class SearchViewModel(
    private val searchRepository: BookSearchRepository,
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val _uiState = MutableStateFlow<SearchUiState>(SearchUiState.Idle)
    val uiState: StateFlow<SearchUiState> = _uiState

    val shelfIds: StateFlow<Set<String>> = bookRepository.shelf
        .map { books -> books.map { it.id }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptySet())

    private var searchJob: Job? = null

    fun onQueryChange(value: String) {
        _query.value = value
        searchJob?.cancel()
        if (value.isBlank()) {
            _uiState.value = SearchUiState.Idle
            return
        }
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_MS)
            performSearch(value)
        }
    }

    fun retry() {
        searchJob?.cancel()
        searchJob = viewModelScope.launch { performSearch(_query.value) }
    }

    fun addToShelf(book: Book) {
        viewModelScope.launch { bookRepository.addBook(book) }
    }

    private suspend fun performSearch(query: String) {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) {
            _uiState.value = SearchUiState.Idle
            return
        }
        _uiState.value = SearchUiState.Loading
        _uiState.value = try {
            val books = searchRepository.search(trimmed)
            if (books.isEmpty()) SearchUiState.Empty(trimmed) else SearchUiState.Success(books)
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            SearchUiState.Error("No internet connection. Check your network and try again.")
        } catch (e: HttpException) {
            SearchUiState.Error("Server error (${e.code()}). Please try again.")
        } catch (e: Exception) {
            SearchUiState.Error("Something went wrong. Please try again.")
        }
    }
}