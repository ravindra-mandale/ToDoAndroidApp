package com.programminginmyway.todoappnew.screens;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.splashscreen.SplashScreen;

import com.programminginmyway.todoappnew.R;
import com.programminginmyway.todoappnew.ShowAlertDialog;

public class ForgotPasswordScreen extends BaseSecureActivity {
    private EditText editTextEmailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_forgot_password_screen);

        editTextEmailId = findViewById(R.id.edittext_email_id);
        Button resetPassword = findViewById(R.id.button_reset_password);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.todo_toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(ForgotPasswordScreen.this, LoginScreen.class));
//            }
//        });
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailId;
                emailId = editTextEmailId.getText().toString().trim();
                if (TextUtils.isEmpty(emailId)) {
                    // Check the email EditText. If it is empty, show an error message
                    String errorMessage = getString(R.string.email_not_entered_error_message);
                    editTextEmailId.setError(errorMessage);
                } else if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
                    // Check email id valid or not
                    Toast.makeText(getApplicationContext(), getString(R.string.enter_valid_email_address), Toast.LENGTH_LONG).show();
                } else {
                    // todo send email for resetting password
                    Toast.makeText(getApplicationContext(), "This feature is not yet avaiable", Toast.LENGTH_LONG).show();
                }
            }
        });

          //activate in future when required
//        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                showExitDialog();
//            }
//        });
    }

    public void showExitDialog() {
        final AlertDialog.Builder alertDialogBuilder = ShowAlertDialog.createAlertDialogForCloseApp(this);
        alertDialogBuilder.setPositiveButton(R.string.positive_text, (dialog, which) -> {
            finishAffinity();
        });
        alertDialogBuilder.setNegativeButton(R.string.negative_text, (dialog, which) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}