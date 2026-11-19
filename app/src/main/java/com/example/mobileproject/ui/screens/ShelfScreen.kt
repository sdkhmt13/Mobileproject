package com.example.mobileproject.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mobileproject.data.ReadingStatus
import com.example.mobileproject.viewmodel.ShelfViewModel
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShelfScreen(
    viewModel: ShelfViewModel,
    onBookClick: (String) -> Unit,
    onSearchClick: () -> Unit
) {
    val books by viewModel.books.collectAsStateWithLifecycle()
    val filter by viewModel.filter.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Shelf") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = filter == null,
                    onClick = { viewModel.setFilter(null) },
                    label = { Text("All") }
                )
                FilterChip(
                    selected = filter == ReadingStatus.WANT,
                    onClick = { viewModel.setFilter(ReadingStatus.WANT) },
                    label = { Text("Want") }
                )
                FilterChip(
                    selected = filter == ReadingStatus.READING,
                    onClick = { viewModel.setFilter(ReadingStatus.READING) },
                    label = { Text("Reading") }
                )
                FilterChip(
                    selected = filter == ReadingStatus.DONE,
                    onClick = { viewModel.setFilter(ReadingStatus.DONE) },
                    label = { Text("Done") }
                )
            }

            if (books.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when (filter) {
                            null -> "Your shelf is empty.\nTap + to add a book."
                            ReadingStatus.WANT -> "No books in your wishlist yet."
                            ReadingStatus.READING -> "You are not reading anything right now."
                            ReadingStatus.DONE -> "You have not finished any books yet."
                        },
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(32.dp)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(books, key = { it.id }) { book ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        ) {
                            Text(book.title)
                            Text(book.author)
                        }
                    }
                }
            }
        }
    }
}