package com.programminginmyway.todoappnew.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.programminginmyway.todoappnew.data.database.roomDb.dao.TaskDao

class MainViewModelFactory(
    private val taskDao: TaskDao,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(taskDao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
