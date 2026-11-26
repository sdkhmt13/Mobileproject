package com.example.mobileproject.viewmodel

package com.example.mobileproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobileproject.data.BookRepository
import com.example.mobileproject.data.ReadingStatus
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ShelfStats(
    val total: Int = 0,
    val want: Int = 0,
    val reading: Int = 0,
    val done: Int = 0
)

class StatsViewModel(repository: BookRepository) : ViewModel() {

    // Recalculated automatically every time the shelf in Room changes
    val stats: StateFlow<ShelfStats> = repository.shelf
        .map { books ->
            ShelfStats(
                total = books.size,
                want = books.count { it.status == ReadingStatus.WANT },
                reading = books.count { it.status == ReadingStatus.READING },
                done = books.count { it.status == ReadingStatus.DONE }
            )
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ShelfStats())
}