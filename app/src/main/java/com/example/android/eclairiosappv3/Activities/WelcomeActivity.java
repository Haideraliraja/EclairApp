package com.example.android.eclairiosappv3.Activities;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.android.eclairiosappv3.R;
import com.example.android.eclairiosappv3.Models.TopExceptionHandler;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new TopExceptionHandler(this));
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);
//        this.getSupportActionBar().hide();


    }

    public void btnEmpLoginListener(View view) {

        Intent intentEmpLogin = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intentEmpLogin);
        //setContentView(R.layout.layout_login);
    }

    public void btnMgtLoginListener(View view) {
        Intent i2 = new Intent(WelcomeActivity.this, RegisterUser.class);
        startActivity(i2);
        //setContentView(R.layout.layout_login);
    }

    public void btnAdminLoginListener(View view) {
        Intent i3 = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(i3);
        // setContentView(R.layout.layout_login);
    }
    public void onBackPressed(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this);
        builder.setMessage("Do you want to close the App ?");
        builder.setCancelable(true);
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finishAffinity();
            }
        });

        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
