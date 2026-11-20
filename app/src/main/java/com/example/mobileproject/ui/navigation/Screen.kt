package com.example.mobileproject.ui.navigation

sealed class Screen(val route: String) {
    object Shelf : Screen("shelf")
    object Search : Screen("search")
    object Detail : Screen("detail/{bookId}") {
        fun createRoute(bookId: String) = "detail/$bookId"
    }
}