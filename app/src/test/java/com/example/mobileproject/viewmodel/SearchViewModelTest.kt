package com.example.mobileproject.viewmodel

import com.example.mobileproject.data.Book
import com.example.mobileproject.data.BookSearchRepository
import com.example.mobileproject.data.InMemoryBookRepository
import com.example.mobileproject.ui.state.SearchUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

private class ControllableSearchRepository : BookSearchRepository {
    var result: List<Book> = emptyList()
    var error: Throwable? = null
    var latencyMs: Long = 0
    var calls: Int = 0

    override suspend fun search(query: String): List<Book> {
        calls++
        if (latencyMs > 0) delay(latencyMs)
        error?.let { throw it }
        return result
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val dune = Book("OL1W", "Dune", "Frank Herbert")

    private lateinit var search: ControllableSearchRepository
    private lateinit var shelf: InMemoryBookRepository
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        search = ControllableSearchRepository()
        shelf = InMemoryBookRepository(emptyList())
        viewModel = SearchViewModel(search, shelf)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialState_isIdle() {
        assertEquals(SearchUiState.Idle, viewModel.uiState.value)
    }

    @Test
    fun query_showsLoadingThenSuccess() = runTest {
        search.result = listOf(dune)
        search.latencyMs = 1_000

        viewModel.onQueryChange("dune")
        advanceTimeBy(500)
        runCurrent()
        assertEquals(SearchUiState.Loading, viewModel.uiState.value)

        advanceUntilIdle()
        assertEquals(SearchUiState.Success(listOf(dune)), viewModel.uiState.value)
    }

    @Test
    fun query_withNoResults_showsEmpty() = runTest {
        search.result = emptyList()

        viewModel.onQueryChange("zzzzqq")
        advanceUntilIdle()

        assertEquals(SearchUiState.Empty("zzzzqq"), viewModel.uiState.value)
    }

    @Test
    fun networkFailure_showsError() = runTest {
        search.error = IOException("no network")

        viewModel.onQueryChange("dune")
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value is SearchUiState.Error)
    }

    @Test
    fun retry_afterError_showsSuccess() = runTest {
        search.error = IOException("no network")
        viewModel.onQueryChange("dune")
        advanceUntilIdle()
        assertTrue(viewModel.uiState.value is SearchUiState.Error)

        search.error = null
        search.result = listOf(dune)
        viewModel.retry()
        advanceUntilIdle()

        assertEquals(SearchUiState.Success(listOf(dune)), viewModel.uiState.value)
    }

    @Test
    fun blankQuery_returnsToIdle_withoutSearching() = runTest {
        viewModel.onQueryChange("dune")
        viewModel.onQueryChange("")
        advanceUntilIdle()

        assertEquals(SearchUiState.Idle, viewModel.uiState.value)
        assertEquals(0, search.calls)
    }

    @Test
    fun fastTyping_sendsOnlyOneRequest() = runTest {
        search.result = listOf(dune)

        viewModel.onQueryChange("d")
        viewModel.onQueryChange("du")
        viewModel.onQueryChange("dun")
        viewModel.onQueryChange("dune")
        advanceUntilIdle()

        assertEquals(1, search.calls)
    }

    @Test
    fun addToShelf_savesBookInRepository() = runTest {
        viewModel.addToShelf(dune)
        advanceUntilIdle()

        assertEquals(listOf(dune), shelf.shelf.first())
    }
}