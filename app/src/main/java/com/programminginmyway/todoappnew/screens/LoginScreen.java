package com.programminginmyway.todoappnew.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.programminginmyway.todoappnew.Model.Users;
import com.programminginmyway.todoappnew.R;
import com.programminginmyway.todoappnew.ShowAlertDialog;
import com.programminginmyway.todoappnew.Database.UserSQLiteOpenHelper;

import java.util.List;

public class LoginScreen extends AppCompatActivity {

    private static final int RC_SIGN_IN = 3003;
    SharedPreferences sp;
    public static final String USER_LOGIN_KEY = "USERLOGIN";
    UserSQLiteOpenHelper userSQLiteOpenHelper;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences(getString(R.string.SHARED_PREFERENCE_NAME), MODE_PRIVATE);
        userSQLiteOpenHelper = new UserSQLiteOpenHelper(LoginScreen.this);
        mAuth = FirebaseAuth.getInstance();
        createRequest();
        final EditText editTextEmailId = findViewById(R.id.edittext_email_id);
        final EditText editTextPassword = findViewById(R.id.edittext_password);
        final Button buttonLogin = findViewById(R.id.button_login);
        final TextView textViewForgottenPassword = findViewById(R.id.textview_forgotten_password);
        final TextView textViewNewUser = findViewById(R.id.textview_newuser);
        final Button buttonSignInWithGoogle = findViewById(R.id.button_google_signIn);
        boolean userLogin = sp.getBoolean(USER_LOGIN_KEY, false);
        if (userLogin) {
            finish();
            startActivity(new Intent(LoginScreen.this, MainScreen.class));
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showExitDialog();
            }
        });

        // do login is already registered
        buttonLogin.setOnClickListener(view -> {

            String emailId = editTextEmailId.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            // Check the email id EditText. If it is empty, show an error message
            if (TextUtils.isEmpty(emailId)) {
                String errorMessageEmail = getString(R.string.email_not_entered_error_message);
                editTextEmailId.setError(errorMessageEmail);
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailId).matches()) {
                Toast.makeText(getApplicationContext(), getString(R.string.enter_valid_email_address), Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(password)) {    // Check the password EditText. If it is empty, show an error message
                String errorMessagePassword = getString(R.string.password_not_entered_error_message);
                editTextPassword.setError(errorMessagePassword);
            } else { // Username and password EditText are not empty, then check login credentials
                checkLogin(emailId, password);
            }
        });
        // redirect to registration screen, if new user
        textViewNewUser.setOnClickListener(view -> startActivity(new Intent(LoginScreen.this, RegistrationScreenNew.class)));
        // redirect to forgot password screen, if user forgot his password
        textViewForgottenPassword.setOnClickListener(v -> startActivity(new Intent(LoginScreen.this, ForgotPasswordScreen.class)));

        buttonSignInWithGoogle.setOnClickListener(view -> signIn());
    }

    private void createRequest() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

    }

    // this will open popup window to show list of google emails through which you want to sign in or choose google account from recently logged in
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                firebaseAuthWithGoogle(task);
            } catch (Exception e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(getApplicationContext(), "Sign in Failed, try again", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(Task<GoogleSignInAccount> completedTask) {
        // Google Sign In was successful, authenticate with Firebase
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acc = GoogleSignIn.getLastSignedInAccount(this);
            Intent intent = new Intent(LoginScreen.this, MainScreen.class);
            if (acc != null) {
                intent.putExtra("UserName", acc.getDisplayName());
            }
            startActivity(intent);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    private void checkLogin(String emailId, String password) {
        int flag = 0;
        //session management using shared preferences
        SharedPreferences.Editor editor = sp.edit();
        //User login
        List<Users> userlist = userSQLiteOpenHelper.checkAllUsers();
        if (!userlist.isEmpty()) {
            for (Users users : userlist) {
                if (users.getEmailId().equals(emailId) && users.getPassword().equals(password)) {
                    //successful login on matching above condition, otherwise failed
                    flag = 1;
                    String toastMessage = getString(R.string.login_success_message);
                    Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
                    editor.putBoolean(USER_LOGIN_KEY, true);
                    editor.apply();
                    startActivity(new Intent(LoginScreen.this, MainScreen.class));
                }
            }
        }
        if (flag == 0) {
            String toastMessage = getString(R.string.login_failed_message);
            Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();
        }
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

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            Intent intent = new Intent(this, MainScreen.class);
            startActivity(intent);
        }
    }
}