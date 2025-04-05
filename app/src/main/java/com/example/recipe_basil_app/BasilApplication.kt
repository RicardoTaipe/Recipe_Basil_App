package com.example.recipe_basil_app

import android.app.Application
import com.example.recipe_basil_app.data.AppContainerImpl
import com.example.recipe_basil_app.data.AppContainer

class BasilApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl()
    }
}