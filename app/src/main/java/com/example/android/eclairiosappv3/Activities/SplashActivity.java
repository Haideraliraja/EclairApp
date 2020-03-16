package com.example.android.eclairiosappv3.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.android.eclairiosappv3.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                boolean isLogin = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this).getBoolean("isLogin", false);
                Log.d("islogin", "setRememberMe() - Login Act  : " + isLogin);
                if ( isLogin ) {

                    Intent intentEmpLoginMain = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intentEmpLoginMain);
                    finish();
                } else {

                    Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 3000);
    }
}