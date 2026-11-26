package com.example.mobileproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mobileproject.AppContainer
import com.example.mobileproject.ui.screens.BookDetailScreen
import com.example.mobileproject.ui.screens.SearchScreen
import com.example.mobileproject.ui.screens.SettingsScreen
import com.example.mobileproject.ui.screens.ShelfScreen
import com.example.mobileproject.viewmodel.BookDetailViewModel
import com.example.mobileproject.viewmodel.SearchViewModel
import com.example.mobileproject.viewmodel.SettingsViewModel
import com.example.mobileproject.viewmodel.ShelfViewModel
import com.example.mobileproject.viewmodel.ViewModelFactory

@Composable
fun AppNavHost(container: AppContainer) {
    val navController = rememberNavController()
    val back: () -> Unit = { navController.popBackStack() }

    NavHost(
        navController = navController,
        startDestination = Screen.Shelf.route
    ) {
        composable(Screen.Shelf.route) {
            val shelfViewModel: ShelfViewModel =
                viewModel(factory = ViewModelFactory(container))
            ShelfScreen(
                viewModel = shelfViewModel,
                onBookClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) },
                onSearchClick = { navController.navigate(Screen.Search.route) },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(Screen.Search.route) {
            val searchViewModel: SearchViewModel =
                viewModel(factory = ViewModelFactory(container))
            SearchScreen(viewModel = searchViewModel, onBack = back)
        }

        composable(Screen.Settings.route) {
            val settingsViewModel: SettingsViewModel =
                viewModel(factory = ViewModelFactory(container))
            SettingsScreen(viewModel = settingsViewModel, onBack = back)
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("bookId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: return@composable
            val detailViewModel: BookDetailViewModel =
                viewModel(factory = ViewModelFactory(container, bookId))
            BookDetailScreen(viewModel = detailViewModel, onBack = back)
        }
    }
}