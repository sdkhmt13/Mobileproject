package com.example.mobileproject.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mobileproject.data.BookRepository
import com.example.mobileproject.ui.screens.BookDetailScreen
import com.example.mobileproject.viewmodel.BookDetailViewModel
import com.example.mobileproject.viewmodel.ViewModelFactory

@Composable
fun AppNavHost(repository: BookRepository) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Shelf.route
    ) {
        composable(Screen.Shelf.route) {
            TemporaryShelfScreen(
                onOpenBook = { id -> navController.navigate(Screen.Detail.createRoute(id)) }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: return@composable
            val detailViewModel: BookDetailViewModel =
                viewModel(factory = ViewModelFactory(repository, bookId))
            BookDetailScreen(viewModel = detailViewModel)
        }
    }
}

// Temporary: will be replaced by B's ShelfScreen
@Composable
private fun TemporaryShelfScreen(onOpenBook: (String) -> Unit) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Temporary screen until ShelfScreen is ready")
            Button(onClick = { onOpenBook("1") }) { Text("Open book 1") }
            Button(onClick = { onOpenBook("2") }) { Text("Open book 2") }
            Button(onClick = { onOpenBook("999") }) { Text("Open missing book") }
        }
    }
}