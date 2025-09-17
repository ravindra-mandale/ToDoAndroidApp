package com.programminginmyway.todoappnew.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.programminginmyway.todoappnew.R
import com.programminginmyway.todoappnew.Utils.PasswordUtils
import com.programminginmyway.todoappnew.database.AppDatabase
import com.programminginmyway.todoappnew.screens.LoginScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    // Two-way binding variables
    val emailId = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val loginSuccess = MutableLiveData<Boolean>()

    private val context = application.applicationContext
    private val prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.SHARED_PREFERENCE_NAME), 0)
//    private val dbHelper = UserSQLiteOpenHelper(context)
//    private val auth = FirebaseAuth.getInstance()
    private val userDao = AppDatabase.getDatabase(application).userDao()

    fun validateAndLogin() {
        val emailValue = emailId.value?.trim() ?: ""
        val passwordValue = password.value?.trim() ?: ""

        when {
            emailValue.isEmpty() -> {
                Toast.makeText(
                    context,
                    R.string.email_not_entered_error_message,
                    Toast.LENGTH_SHORT
                ).show()
            }

            !Patterns.EMAIL_ADDRESS.matcher(emailValue).matches() -> {
                Toast.makeText(context, R.string.enter_valid_email_address, Toast.LENGTH_SHORT)
                    .show()
            }

            passwordValue.isEmpty() -> {
                Toast.makeText(
                    context,
                    R.string.password_not_entered_error_message,
                    Toast.LENGTH_SHORT
                ).show()
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
            val user = withContext(Dispatchers.IO) { userDao.getUserByEmail(email) }
            if (user != null && PasswordUtils.hashPassword(password) == user.PASSWORD) {
                Toast.makeText(context, R.string.login_success_message, Toast.LENGTH_SHORT).show()
                prefs.edit().putBoolean(LoginScreen.USER_LOGIN_KEY, true).apply()
                loginSuccess.postValue(true)
            } else {
                Toast.makeText(context, R.string.login_failed_message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun clearErrors() {
        emailId.value = null
        password.value = null
    }
}