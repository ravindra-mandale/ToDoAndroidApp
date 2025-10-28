package com.programminginmyway.todoappnew.data

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

// Singleton DataStore instance
val Context.dataStore by preferencesDataStore(name = "user_prefs")