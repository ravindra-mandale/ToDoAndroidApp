package com.programminginmyway.todoappnew.viewmodel

import android.app.Application
import com.programminginmyway.todoappnew.data.dataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.programminginmyway.todoappnew.data.database.roomDb.dao.TaskDao
import com.programminginmyway.todoappnew.data.database.roomDb.model.TaskList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.first

class MainViewModel(private val taskDao: TaskDao, application: Application): AndroidViewModel(application) {

    companion object {
        val USER_LOGGED_IN = booleanPreferencesKey("user_logged_in")
    }

    private val _tasks = MutableLiveData<List<TaskList>>()
    val tasks: LiveData<List<TaskList>> = _tasks

    fun loadTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = taskDao.getAllTasks().reversed()
            _tasks.postValue(list)
        }
    }

    private val appContext = getApplication<Application>() // safe Application context

    fun logout(onComplete: () -> Unit) {
        viewModelScope.launch {
            appContext.dataStore.edit { prefs ->
                prefs[USER_LOGGED_IN] = false
            }
            onComplete()
        }
    }

    suspend fun isLoggedIn(): Boolean {
        val prefs = appContext.dataStore.data.first()
        return prefs[USER_LOGGED_IN] ?: false
    }
}