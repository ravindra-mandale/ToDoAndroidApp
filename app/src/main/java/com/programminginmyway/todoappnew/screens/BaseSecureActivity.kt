package com.programminginmyway.todoappnew.screens

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

open class BaseSecureActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Prevent screenshots and screen recording
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        super.onCreate(savedInstanceState)
    }
}