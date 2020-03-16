package com.example.android.eclairiosappv3.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.example.android.eclairiosappv3.Adapters.SqlAdapter;
import com.example.android.eclairiosappv3.R;

public class RegisterUser extends AppCompatActivity {

    EditText RE_FNAME, RE_LNAME, RE_CNIC,RE_USER_PHN, RE_USER_EMAIL, RE_USER_PASS, RE_USER_ConPASS;
    String fname,lname,phno, cnic, user_email, user_pass;
    TextView cirRegButton;
    AwesomeValidation awesomeValidation;
    ProgressBar pb;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        updateUI();

        RE_FNAME = (EditText) findViewById(R.id.RegEmp_fname);
        RE_LNAME = (EditText) findViewById(R.id.RegEmp_lname);
        RE_CNIC = (EditText) findViewById(R.id.RegEmp_Cnic);
        RE_USER_PHN = (EditText) findViewById(R.id.RegEmp_Phn);
        RE_USER_EMAIL = (EditText) findViewById(R.id.RegEmp_email);
        RE_USER_PASS = (EditText) findViewById(R.id.RegEmp_pass);
        pb = (ProgressBar) findViewById(R.id.progressBar);

        pb.setVisibility(View.INVISIBLE);


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateUI() {

        RE_FNAME = (EditText) findViewById(R.id.RegEmp_fname);
        RE_LNAME = (EditText) findViewById(R.id.RegEmp_lname);
        RE_CNIC = (EditText) findViewById(R.id.RegEmp_Cnic);
        RE_USER_PHN = (EditText) findViewById(R.id.RegEmp_Phn);
        RE_USER_EMAIL = (EditText) findViewById(R.id.RegEmp_email);
        RE_USER_PASS = (EditText) findViewById(R.id.RegEmp_pass);
        RE_USER_ConPASS = (EditText) findViewById(R.id.RegEmp_passConfirm);


        cirRegButton = (TextView) findViewById(R.id.cirRegButton);




        awesomeValidation.addValidation(RegisterUser.this, R.id.RegEmp_fname,"[a-zA-Z0-9_-]+",R.string.fname);
        awesomeValidation.addValidation(RegisterUser.this,R.id.RegEmp_lname,"[a-zA-Z0-9_-]+",R.string.lname);
        awesomeValidation.addValidation(RegisterUser.this, R.id.RegEmp_Cnic, new SimpleCustomValidation() {
            @Override
            public boolean compare(String s) {
                try {

                    if (s.length() < 13){
                        return false;
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        },R.string.ecnic);



        awesomeValidation.addValidation(RegisterUser.this,R.id.RegEmp_Phn, RegexTemplate.TELEPHONE,R.string.phno);
        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{8,}";
        awesomeValidation.addValidation(RegisterUser.this, R.id.RegEmp_email, Patterns.EMAIL_ADDRESS,R.string.empemail);
        awesomeValidation.addValidation(RegisterUser.this, R.id.RegEmp_pass, regexPassword , R.string.password);
        awesomeValidation.addValidation(RegisterUser.this, R.id.RegEmp_passConfirm,R.id.RegEmp_pass,R.string.conpassword);




        cirRegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(awesomeValidation.validate()){

                    Toast.makeText(RegisterUser.this, "User Registeration Successful..", Toast.LENGTH_SHORT).show();
                    userRegister();

                }else{
                    Toast.makeText(RegisterUser.this, "Registeration Failed ! please insert correct information..", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void userRegister() {

        pb.setVisibility(View.VISIBLE);
        fname = RE_FNAME.getText().toString();
        lname = RE_LNAME.getText().toString();
        cnic = RE_CNIC.getText().toString();
        Log.d("cnicTest","value of cnic : "+cnic);
        phno = RE_USER_PHN.getText().toString();
        user_email = RE_USER_EMAIL.getText().toString();
        user_pass = RE_USER_PASS.getText().toString();
        String method = "register";
        SqlAdapter sqlAdapter = new SqlAdapter(this);
        sqlAdapter.execute(method, fname,lname, cnic, phno, user_email, user_pass);
       PreferenceManager.getDefaultSharedPreferences(RegisterUser.this).edit().putInt("today",0).apply();


        finish();


    }


    public void RegbtnCancelListener(View view) {
        finish();
    }
}