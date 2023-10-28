package com.example.nyatta

import android.app.Application
import com.example.nyatta.data.AppContainer
import com.example.nyatta.data.DefaultContainer

class NyattaApp: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultContainer(this)
    }
}