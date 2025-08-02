package com.programminginmyway.todoappnew.screens;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.programminginmyway.todoappnew.R;

public class ForgotPasswordScreen extends AppCompatActivity {
    private EditText editTextEmailId;
    private Button resetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_forgot_password_screen);

        editTextEmailId = findViewById(R.id.edittext_email_id);
        resetPassword = findViewById(R.id.button_reset_password);
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

    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(R.string.alert_dialog_title_exit);
        alertDialogBuilder.setMessage(R.string.alert_dialog_message_exit);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton(R.string.positive_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                System.exit(0);
            }
        });
        alertDialogBuilder.setNegativeButton(R.string.negative_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}