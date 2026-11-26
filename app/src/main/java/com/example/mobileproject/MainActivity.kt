package com.example.mobileproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobileproject.data.InMemoryBookRepository
import com.example.mobileproject.ui.navigation.AppNavHost
import com.example.mobileproject.ui.screens.BookDetailScreen
import com.example.mobileproject.ui.theme.MobileprojectTheme
import com.example.mobileproject.viewmodel.BookDetailViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val container = (application as BookTrackerApplication).container
        setContent {
            MobileprojectTheme {
                AppNavHost(container = container)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookDetailPreview() {
    MobileprojectTheme {
        BookDetailScreen(
            viewModel = BookDetailViewModel(InMemoryBookRepository(), "1"),
            onBack = {}
        )
    }
}