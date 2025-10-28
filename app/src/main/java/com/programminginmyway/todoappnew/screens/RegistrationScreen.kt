package com.programminginmyway.todoappnew.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.programminginmyway.todoappnew.R
import com.programminginmyway.todoappnew.Utils.showToast
import com.programminginmyway.todoappnew.viewmodel.RegistrationViewModel
import com.programminginmyway.todoappnew.databinding.ActivityRegistrationScreenBinding

class RegistrationScreen : BaseSecureActivity() {
    private lateinit var binding: ActivityRegistrationScreenBinding
    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        // Initialize binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registration_screen)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        onBackPressedDispatcher.addCallback(this) {
            showExitDialog()
        }

        // Observe toast messages from ViewModel
        viewModel.toastMessage.observe(this) { message ->
            message?.let {
                showToast(it, Toast.LENGTH_SHORT)
                viewModel.toastMessage.value = null // Reset to avoid showing again
            }
        }

        // Observe registration success status from ViewModel
        viewModel.registrationSuccess.observe(this) { success ->
            if (success == true) {
                startActivity(Intent(this, LoginScreen::class.java))
                finish() // Optional: Close Registration screen
            }
        }
    }

    private fun showExitDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_title_exit)
            .setMessage(R.string.alert_dialog_message_exit)
            .setCancelable(false)
            .setPositiveButton(R.string.positive_text) { _, _ ->
                finishAffinity()
            }
            .setNegativeButton(R.string.negative_text) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }
}