package com.example.chorebuddy

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.chorebuddy.data.AppContainer
import com.example.chorebuddy.data.AppDataContainer
import com.example.chorebuddy.db.ChoreDatabase

class ChoreApplication : Application() {

    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppDataContainer(this)
    }
}