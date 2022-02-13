package com.example.recipecomposee.presentation

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

   // should be saved in data store or cache
    val isDark = mutableStateOf(true)

    fun toggleDarkTheme(){
        isDark.value = !isDark.value
    }
}