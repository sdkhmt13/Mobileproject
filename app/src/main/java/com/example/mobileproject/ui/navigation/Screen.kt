package com.example.mobileproject.ui.navigation

sealed class Screen(val route: String) {
    object Shelf : Screen("shelf")
    object Search : Screen("search")
    object Settings : Screen("settings")
    object Stats : Screen("stats")
    object AddBook : Screen("add_book")
    object Detail : Screen("detail/{bookId}") {
        fun createRoute(bookId: String) = "detail/$bookId"
    }
}