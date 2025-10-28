package com.programminginmyway.todoappnew.screens

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.programminginmyway.todoappnew.R
import com.programminginmyway.todoappnew.ShowAlertDialog
import com.programminginmyway.todoappnew.Utils.showToast

class ForgotPasswordScreen : BaseSecureActivity() {

    // Use 'lateinit' for non-nullable views that will be initialized in onCreate.
    private lateinit var editTextEmailId: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_forgot_password_screen)

        // Initialize views using findViewById
        editTextEmailId = findViewById(R.id.edittext_email_id)
        val resetPassword = findViewById<Button>(R.id.button_reset_password)

        // Set a click listener using a lambda for more concise code.
        resetPassword.setOnClickListener {
            validateAndReset()
        }
    }

    private fun validateAndReset() {
        val emailId = editTextEmailId.text.toString().trim()

        // 'when' is the Kotlin equivalent of a more powerful 'switch' statement.
        when {
            // Use Kotlin's isNullOrEmpty() for better readability.
            emailId.isEmpty() -> {
                // Check the email EditText. If it is empty, show an error message.
                val errorMessage = getString(R.string.email_not_entered_error_message)
                editTextEmailId.error = errorMessage
            }

            !Patterns.EMAIL_ADDRESS.matcher(emailId).matches() -> {
                // Check if email id is valid or not.
                // This is how you call the Kotlin extension function natively.
                showToast(R.string.enter_valid_email_address, Toast.LENGTH_SHORT)
            }

            else -> {
                // TODO: Send email for resetting password
                showToast(R.string.this_feature_is_not_yet_available, Toast.LENGTH_LONG)
            }
        }
    }

    // Function name changed to follow Kotlin conventions (camelCase).
    private fun showExitDialog() {
        val alertDialogBuilder = ShowAlertDialog.createAlertDialogForCloseApp(this)

        // Use Kotlin lambdas for a much cleaner listener syntax.
        alertDialogBuilder.setPositiveButton(R.string.positive_text) { _, _ ->
            finishAffinity()
        }
        alertDialogBuilder.setNegativeButton(R.string.negative_text) { dialog, _ ->
            dialog.cancel()
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
