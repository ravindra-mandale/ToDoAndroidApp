package com.programminginmyway.todoappnew.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.programminginmyway.todoappnew.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        setContentView(R.layout.activity_splash);
        //splashScreen.setKeepVisibleCondition(()->true);
        //splashScreen.setKeepVisibleCondition(() -> true);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, LoginScreen.class));
                finish();
//            }
//        }, 2000);
    }
}