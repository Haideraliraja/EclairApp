package com.example.android.eclairiosappv3.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.android.eclairiosappv3.Adapters.SqlAdapter;
import com.example.android.eclairiosappv3.R;

public class LoginActivity extends AppCompatActivity {

    EditText ET_EMAIL, ET_PASS;
    String login_email, login_pass;
    CheckBox Remember;
    TextView cirLoginButton;
    AwesomeValidation awesomeValidation;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        updateUI();

        ET_EMAIL = (EditText) findViewById(R.id.editTextEmail);
        ET_PASS = (EditText) findViewById(R.id.editTextPassword);
        Remember = (CheckBox) findViewById(R.id.rememberme);
        pb = (ProgressBar) findViewById(R.id.progressBar);

        pb.setVisibility(View.INVISIBLE);
        setRememberMe();
    }

    private void updateUI() {

        ET_EMAIL = (EditText) findViewById(R.id.editTextEmail);
        ET_PASS = (EditText) findViewById(R.id.editTextPassword);

        cirLoginButton = (TextView) findViewById(R.id.cirLoginButton);

        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(LoginActivity.this, R.id.editTextEmail, Patterns.EMAIL_ADDRESS, R.string.empemail);
        awesomeValidation.addValidation(LoginActivity.this, R.id.editTextPassword, regexPassword, R.string.password);


        cirLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( awesomeValidation.validate() ) {
                btnLoginListener();

                    if ( Remember.isChecked() ) {
                        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putBoolean("isLogin", true).apply();
                        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("user_email",ET_EMAIL.getText().toString()).apply();
                        PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putString("user_pass",ET_PASS.getText().toString()).apply();
                        Toast.makeText(LoginActivity.this, "User Login Successful..", Toast.LENGTH_SHORT).show();
                    } else {
                     //   PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit().putBoolean("isLogin", false).apply();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Please insert correct information..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void btnLoginListener() {
        pb.setVisibility(View.VISIBLE);
        login_email = ET_EMAIL.getText().toString();
        login_pass = ET_PASS.getText().toString();
       String method = "login";
        SqlAdapter sqlAdapter = new SqlAdapter(this);
        sqlAdapter.execute(method, login_email, login_pass);

    }

    public void btnCancelListener(View view) {
         finish();
    }

    public void setRememberMe() {

        boolean isLogin = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("isLogin", false);
        Log.d("islogin", "setRememberMe() - Login Act  : " + isLogin);
        if ( isLogin ) {
            Remember.setChecked(true);

            String getEmail = PreferenceManager.getDefaultSharedPreferences(this).getString("user_email","");
            String getPass = PreferenceManager.getDefaultSharedPreferences(this).getString("user_pass","");

            ET_EMAIL.setText(getEmail);
            ET_PASS.setText(getPass);

          /*String empid=PreferenceManager.getDefaultSharedPreferences(this).getString("empid","");

            String method = "login_user_emp";
            SqlAdapter sqlAdapter = new SqlAdapter(this);
            sqlAdapter.execute(method, empid);*/

        }


    }
}
