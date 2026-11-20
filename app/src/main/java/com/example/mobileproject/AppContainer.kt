package com.example.mobileproject

import com.example.mobileproject.data.BookRepository
import com.example.mobileproject.data.InMemoryBookRepository

object AppContainer {
    val repository: BookRepository = InMemoryBookRepository()
}