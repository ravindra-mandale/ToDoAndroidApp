package com.programminginmyway.todoappnew.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.programminginmyway.todoappnew.ShowAlertDialog
import com.programminginmyway.todoappnew.databinding.ActivityLoginBinding
import com.programminginmyway.todoappnew.viewmodel.LoginViewModel
import com.programminginmyway.todoappnew.R
import com.programminginmyway.todoappnew.data.dataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.programminginmyway.todoappnew.Utils.showToast
import kotlinx.coroutines.launch

class LoginScreen : BaseSecureActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var mAuth: FirebaseAuth

    companion object {
        private const val RC_SIGN_IN = 3003
        val USER_LOGGED_IN = booleanPreferencesKey("user_logged_in")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        // ✅ Initialize FirebaseAuth here
        mAuth = FirebaseAuth.getInstance()
        createRequest()

        checkLogin()

        // Observe login success
        viewModel.loginSuccess.observe(this) { success ->
            if (success) {
                startActivity(Intent(this, MainScreen::class.java))
                finish()
            }
        }

        viewModel.snackBarMessage.observe(this) { stringResId ->
            showToast(stringResId, Toast.LENGTH_SHORT)
        }

        // New user
        binding.textviewNewuser.setOnClickListener {
            startActivity(Intent(this, RegistrationScreen::class.java))
        }

        // Forgot password
        binding.textviewForgottenPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordScreen::class.java))
        }

        // Google sign in
        binding.buttonGoogleSignIn.setOnClickListener {
            signInWithGoogle()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitDialog()
            }
        })
    }

    private fun checkLogin() {
        lifecycleScope.launch {
            applicationContext.dataStore.data.collect { prefs ->
                val isLoggedIn = prefs[USER_LOGGED_IN] ?: false
                if (isLoggedIn) {
                    startActivity(Intent(this@LoginScreen, MainScreen::class.java))
                    finish()
                }
            }
        }
    }

    private fun createRequest() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        GoogleSignIn.getLastSignedInAccount(this)
    }

    private fun signInWithGoogle() {
        val intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                firebaseAuthWithGoogle(task)
            } catch (e: Exception) {
                showToast(getString(R.string.sign_in_failed_try_again), Toast.LENGTH_LONG)
            }
        }
    }

    private fun firebaseAuthWithGoogle(completedTask: Task<GoogleSignInAccount>) {
        try {
            completedTask.getResult(ApiException::class.java)
            val acc = GoogleSignIn.getLastSignedInAccount(this)
            val intent = Intent(this, MainScreen::class.java)
            acc?.let { intent.putExtra("UserName", it.displayName) }
            startActivity(intent)
            finish()
        } catch (e: ApiException) {
            e.printStackTrace()
        }
    }

    fun showExitDialog() {
        val alertDialogBuilder = ShowAlertDialog.createAlertDialogForCloseApp(this)
        alertDialogBuilder.setPositiveButton(R.string.positive_text) { _, _ ->
            finishAffinity()
        }
        alertDialogBuilder.setNegativeButton(R.string.negative_text) { dialog, _ ->
            dialog.cancel()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onStart() {
        super.onStart()
        val firebaseUser: FirebaseUser? = mAuth.currentUser
        if (firebaseUser != null) {
            startActivity(Intent(this, MainScreen::class.java))
            finish()
        }
    }
}