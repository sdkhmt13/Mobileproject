package com.example.mobileproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mobileproject.data.InMemoryBookRepository
import com.example.mobileproject.data.settings.ThemeMode
import com.example.mobileproject.ui.navigation.AppNavHost
import com.example.mobileproject.ui.screens.BookDetailScreen
import com.example.mobileproject.ui.theme.MobileprojectTheme
import com.example.mobileproject.viewmodel.BookDetailViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // One container for the whole app: Room, Retrofit and DataStore are created there once
        val container = (application as BookTrackerApplication).container

        setContent {
            // Theme is read from DataStore and survives app restart
            val themeMode by container.settingsRepository.themeMode
                .collectAsStateWithLifecycle(initialValue = ThemeMode.SYSTEM)

            BookTrackerApp(container = container, themeMode = themeMode)
        }
    }
}

@Composable
fun BookTrackerApp(container: AppContainer, themeMode: ThemeMode) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    MobileprojectTheme(darkTheme = darkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AppNavHost(container = container)
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