package com.example.mobileproject

import android.content.Context
import com.example.mobileproject.data.BookRepository
import com.example.mobileproject.data.BookSearchRepository
import com.example.mobileproject.data.local.AppDatabase
import com.example.mobileproject.data.local.RoomBookRepository
import com.example.mobileproject.data.remote.OpenLibraryBookSearchRepository
import com.example.mobileproject.data.remote.RetrofitClient
import com.example.mobileproject.data.settings.SettingsRepository

class AppContainer(context: Context) {

    private val appContext = context.applicationContext

    val bookRepository: BookRepository by lazy {
        RoomBookRepository(AppDatabase.getInstance(appContext).bookDao())
    }

    val searchRepository: BookSearchRepository by lazy {
        OpenLibraryBookSearchRepository(RetrofitClient.api)
    }

    val settingsRepository: SettingsRepository by lazy {
        SettingsRepository(appContext)
    }
}