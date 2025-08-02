package com.programminginmyway.todoappnew

import android.annotation.SuppressLint
import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import android.text.TextUtils
import androidx.lifecycle.viewModelScope
import com.programminginmyway.todoappnew.Database.UserSQLiteOpenHelper
import com.programminginmyway.todoappnew.Model.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

public class RegistrationViewModel(application: Application) : AndroidViewModel(application) {
    val toastMessage = MutableLiveData<String?>()
    val registrationSuccess = MutableLiveData<Boolean>()
    val email = MutableLiveData("")
    val fullName = MutableLiveData("")
    val mobileNo = MutableLiveData("")
    val password = MutableLiveData("")
    val confirmPassword = MutableLiveData("")

    // Error Fields
    val emailError = MutableLiveData<String?>()
    val fullNameError = MutableLiveData<String?>()
    val mobileNoError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()
    val confirmPasswordError = MutableLiveData<String?>()

    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext
    private val userSQLiteOpenHelper =
        UserSQLiteOpenHelper(context)


    fun onRegisterClicked() {
        clearErrors() // Reset errors before validation

        val emailId = email.value?.trim().orEmpty()
        val fullNameStr = fullName.value?.trim().orEmpty()
        val mobileNoStr = mobileNo.value?.trim().orEmpty()
        val passwordStr = password.value?.trim().orEmpty()
        val confirmPasswordStr = confirmPassword.value?.trim().orEmpty()

        var isValid = true

        if (TextUtils.isEmpty(emailId)) {
            emailError.value = context.getString(R.string.email_not_entered_error_message)
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            emailError.value = context.getString(R.string.enter_valid_email_address)
            isValid = false
        }

        if (TextUtils.isEmpty(fullNameStr)) {
            fullNameError.value = context.getString(R.string.fullname_not_entered_error_message)
            isValid = false
        }

        if (TextUtils.isEmpty(mobileNoStr)) {
            mobileNoError.value = context.getString(R.string.mobileno_not_entered_error_message)
            isValid = false
        } else if (!TextUtils.isDigitsOnly(mobileNoStr) || mobileNoStr.length != 10) {
            mobileNoError.value = context.getString(R.string.enter_valid_mobile_number)
            isValid = false
        }

        if (TextUtils.isEmpty(passwordStr)) {
            passwordError.value = context.getString(R.string.password_not_entered_error_message)
            isValid = false
        }

        if (TextUtils.isEmpty(confirmPasswordStr)) {
            confirmPasswordError.value =
                context.getString(R.string.confirm_password_not_entered_error_message)
            isValid = false
        } else if (passwordStr != confirmPasswordStr) {
            confirmPasswordError.value =
                context.getString(R.string.password_confirm_password_not_same_error_message)
            isValid = false
        }

        if (!isValid) return

        viewModelScope.launch {
            // Proceed with registration
            val result = withContext(Dispatchers.IO) {
                userSQLiteOpenHelper.insertUser(
                    Users(emailId, fullNameStr, mobileNoStr, passwordStr)
                )
            }
            if (result) {
                toastMessage.value = context.getString(R.string.registration_success_message)
                registrationSuccess.value = true  // Notify UI
            } else {
                toastMessage.value = context.getString(R.string.registration_failed_message)
                registrationSuccess.value = false  // Notify UI
            }
        }
    }

    private fun clearErrors() {
        emailError.value = null
        fullNameError.value = null
        mobileNoError.value = null
        passwordError.value = null
        confirmPasswordError.value = null
    }

}