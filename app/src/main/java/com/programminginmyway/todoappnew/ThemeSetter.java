package com.programminginmyway.todoappnew;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeSetter {

    public static void applyTheme(boolean isDarkTheme) {
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
