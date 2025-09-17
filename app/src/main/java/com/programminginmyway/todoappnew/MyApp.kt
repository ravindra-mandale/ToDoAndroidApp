package com.programminginmyway.todoappnew

import android.app.Application
import com.google.firebase.FirebaseApp
import android.util.Log

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        val app = FirebaseApp.initializeApp(this)
        if (app == null) {
            Log.e("MyApp", "Firebase initialization FAILED. Check google-services.json")
        } else {
            Log.d("MyApp", "Firebase initialized successfully: ${app.name}")
        }
    }
}