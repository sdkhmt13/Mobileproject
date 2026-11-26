package com.example.mobileproject

import android.content.Context
import com.example.mobileproject.data.BookRepository
import com.example.mobileproject.data.BookSearchRepository
import com.example.mobileproject.data.FakeBookSearchRepository
import com.example.mobileproject.data.local.AppDatabase
import com.example.mobileproject.data.local.RoomBookRepository

class AppContainer(context: Context) {

    private val appContext = context.applicationContext

    val bookRepository: BookRepository by lazy {
        RoomBookRepository(AppDatabase.getInstance(appContext).bookDao())
    }

    val searchRepository: BookSearchRepository by lazy {
        FakeBookSearchRepository()
    }
}