package com.example.mobileproject

import android.app.Application

class BookTrackerApplication : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}