package com.programminginmyway.todoappnew.screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.programminginmyway.todoappnew.Model.Users;
import com.programminginmyway.todoappnew.R;
import com.programminginmyway.todoappnew.Database.UserSQLiteOpenHelper;

public class RegistrationScreen extends AppCompatActivity {

    UserSQLiteOpenHelper userSQLiteOpenHelper;
    private Button buttonRegister;
    private EditText editTextEmailId, editTextFullName, editTextMobileNo, editTextPassword, editTextConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_registration);
        buttonRegister = findViewById(R.id.button_register);
        editTextEmailId = findViewById(R.id.edittext_email_id);
        editTextFullName = findViewById(R.id.edittext_full_name);
        editTextMobileNo = findViewById(R.id.edittext_mobile_number);
        editTextPassword = findViewById(R.id.edittext_password);
        editTextConfirmPassword = findViewById(R.id.edittext_confirm_password);

        userSQLiteOpenHelper = new UserSQLiteOpenHelper(this);
        buttonRegister.setOnClickListener(view -> {
            doRegistration();
        });
    }

    private void doRegistration() {
        String emailId, fullName, mobileNo, password, confirmPassword;
        emailId = editTextEmailId.getText().toString().trim();
        fullName = editTextFullName.getText().toString().trim();
        mobileNo = editTextMobileNo.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(emailId)) {
            // Check the email EditText. If it is empty, show an error message
            String errorMessage = getString(R.string.email_not_entered_error_message);
            editTextEmailId.setError(errorMessage);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
            // Check email id valid or not
            Toast.makeText(getApplicationContext(), getString(R.string.enter_valid_email_address), Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(fullName)) {
            // Check the fullName EditText. If it is empty, show an error message
            String errorMessage = getString(R.string.fullname_not_entered_error_message);
            editTextFullName.setError(errorMessage);
        } else if (TextUtils.isEmpty(mobileNo)) {
            // Check the mobileNo EditText. If it is empty, show an error message
            String errorMessage = getString(R.string.mobileno_not_entered_error_message);
            editTextMobileNo.setError(errorMessage);
        } else if (!TextUtils.isDigitsOnly(mobileNo) || mobileNo.length() != 10) {
            // Check mobile number valid or not
            Toast.makeText(getApplicationContext(), getString(R.string.enter_valid_mobile_number), Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(password)) {
            // Check the password EditText. If it is empty, show an error message
            String errorMessage = getString(R.string.password_not_entered_error_message);
            editTextPassword.setError(errorMessage);
        } else if (TextUtils.isEmpty(confirmPassword)) {
            // Check the confirmPassword EditText. If it is empty, show an error message
            String errorMessage = getString(R.string.confirm_password_not_entered_error_message);
            editTextConfirmPassword.setError(errorMessage);
        } else if (!password.equals(confirmPassword)) {
            // Check the password and confirmPassword are same. If they are not same, show an error message
            String errorMessage = getString(R.string.password_confirm_password_not_same_error_message);
            editTextConfirmPassword.setError(errorMessage);
        } else {
            boolean result = userSQLiteOpenHelper.insertUser(new Users(emailId, fullName, mobileNo, password));
            if (result) {
                String registrationSuccessMessage = getString(R.string.registration_success_message);
                Toast.makeText(RegistrationScreen.this, registrationSuccessMessage, Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegistrationScreen.this, LoginScreen.class));
            } else {
                String registrationFailedMessage = getString(R.string.registration_failed_message);
                Toast.makeText(RegistrationScreen.this, registrationFailedMessage, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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