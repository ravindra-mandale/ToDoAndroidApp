package com.programminginmyway.todoappnew

import androidx.appcompat.app.AppCompatDelegate

object ThemeSetter {
    //singleton object- this ThemeSetter class has only one instance throughout the application, no need to use new keyword
    fun applyTheme(isDarkTheme: Boolean) {
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}