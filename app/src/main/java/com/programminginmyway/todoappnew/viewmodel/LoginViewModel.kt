package com.programminginmyway.todoappnew.viewmodel

import android.app.Application
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.programminginmyway.todoappnew.R
import com.programminginmyway.todoappnew.Utils.PasswordUtils
import com.programminginmyway.todoappnew.data.database.roomDb.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.datastore.preferences.core.edit
import com.programminginmyway.todoappnew.data.dataStore
import com.programminginmyway.todoappnew.viewmodel.MainViewModel.Companion.USER_LOGGED_IN

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    // Two-way binding variables
    val emailId = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loginSuccess = MutableLiveData<Boolean>()
    // LiveData for showing Toast messages in the UI
    val snackBarMessage = SingleLiveEvent<Int>() // Using Int for String resource ID

    private val context = application.applicationContext
    private val userDao = AppDatabase.getDatabase(application).userDao()

    fun validateAndLogin() {
        val emailValue = emailId.value?.trim() ?: ""
        val passwordValue = password.value?.trim() ?: ""

        when {
            emailValue.isEmpty() -> {
                snackBarMessage.value = R.string.email_not_entered_error_message
            }

            !Patterns.EMAIL_ADDRESS.matcher(emailValue).matches() -> {
                //Toast.makeText(context, R.string.enter_valid_email_address, Toast.LENGTH_SHORT)
                  //  .show()
                snackBarMessage.value = R.string.enter_valid_email_address
            }

            passwordValue.isEmpty() -> {
                snackBarMessage.value = R.string.password_not_entered_error_message
            }

            else -> {
                checkLogin(emailValue, passwordValue)
            }
        }
    }

    private fun checkLogin(email: String, password: String) {
//        val editor = prefs.edit()
//        var found = false
//        val userList: List<Users> = dbHelper.checkAllUsers()
//        for (user in userList) {
//            if (user.emailId == email && user.password == password) {
//                found = true
//                Toast.makeText(context, R.string.login_success_message, Toast.LENGTH_SHORT).show()
//                editor.putBoolean(LoginScreen.USER_LOGIN_KEY, true)
//                editor.apply()
//                loginSuccess.postValue(true)
//                break
//            }
//        }
//        if (!found) {
//            Toast.makeText(context, R.string.login_failed_message, Toast.LENGTH_SHORT).show()
//        }
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                userDao.getUserByEmail(email)
            }
            if (user != null && PasswordUtils.isPasswordValid(password, user.PASSWORD)) {
                //Toast.makeText(context, R.string.login_success_message, Toast.LENGTH_SHORT).show()
                snackBarMessage.value = R.string.login_success_message
                //prefs.edit { putBoolean(LoginScreen.USER_LOGIN_KEY, true) }
                val appContext = getApplication<Application>()
                appContext.dataStore.edit { prefs ->
                    prefs[USER_LOGGED_IN] = true
                }
                loginSuccess.postValue(true)
            } else {
                snackBarMessage.value = R.string.login_failed_message
                //Toast.makeText(context, R.string.login_failed_message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}