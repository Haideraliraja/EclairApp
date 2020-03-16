package com.example.android.eclairiosappv3.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.eclairiosappv3.Adapters.SqlAdapter;
import com.example.android.eclairiosappv3.R;

import java.util.Calendar;

public class MarkAttendance extends AppCompatActivity implements SqlAdapter.dateInterface {

    TextView VIEW_DATE, VIEW_TIME;
    String viewDate, viewTime;
    static String months[];
    CardView btnCheckInListener;
    CardView btnCheckOutListener;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_mark_attendance);

        btnCheckInListener= findViewById(R.id.checkInButton);
        btnCheckOutListener= findViewById(R.id.checkOutButton);
        pb = findViewById(R.id.progressBar);

        pb.setVisibility(View.INVISIBLE);

        Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);


        int today = PreferenceManager.getDefaultSharedPreferences(this).getInt("today",0);
        if(today==day){
            Log.d("result"," today==day: "+today);
            btnCheckInListener.setEnabled(false);
            btnCheckInListener.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            btnCheckOutListener.setEnabled(false);
            btnCheckOutListener.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        }
        else{
            Log.d("result"," today==day ----- setEnableDisable() : "+today);
            setEnableDisable();
        }

        VIEW_DATE = (TextView) findViewById(R.id.textViewDate);
        VIEW_TIME = (TextView) findViewById(R.id.textViewTime);

        viewDate = VIEW_DATE.getText().toString();
        Log.d("DateTest", "Date  : " + viewDate);
        viewTime = VIEW_TIME.getText().toString();
        Log.d("TimeTest", "Time   : " + viewTime);


        String method = "getDateTime";
        SqlAdapter sqlAdapter = new SqlAdapter(this);
        sqlAdapter.dateData= MarkAttendance.this;
        sqlAdapter.execute(method);


    }

    public void setEnableDisable(){
        boolean ischeckedin=PreferenceManager.getDefaultSharedPreferences(this).getBoolean("ischeckedin",false);
        Log.d("result","setEnabledFn  : "+ischeckedin);
        if (ischeckedin) {
           btnCheckInListener.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            btnCheckInListener.setEnabled(false);
            Log.d("result"," btnCheckInListener.setEnabled(false): "+ischeckedin);
        }
            else
        {
            /*float f = (float) 15.5;
            btnCheckOutListener.setRadius(f);*/
            btnCheckOutListener.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            btnCheckOutListener.setEnabled(false);

            Log.d("result","btnCheckOutListener.setEnabled(false);: "+ischeckedin);

        }

    }

    public void btnCheckInListener(View view) {
        pb.setVisibility(View.INVISIBLE);

        //get userid from server and to store in sharedpreference, to send it back to store in setAttendance table.
        String userid=PreferenceManager.getDefaultSharedPreferences(this).getString("empid","");


        Log.d("result","btn CheckIn Listener  : "+userid);

        Toast.makeText(this, "Got SP Check in value: "+userid, Toast.LENGTH_LONG).show();

        SqlAdapter sqlAdapter = new SqlAdapter(this);
        sqlAdapter.execute("checked_in", userid,"1");
        Log.d("result","before btnCheckInListener.setEnabled(false)  : "+userid);
        btnCheckInListener.setEnabled(false);

        Log.d("result","After btnCheckInListener.setEnabled(false)  : "+btnCheckInListener);
        btnCheckInListener.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        Log.d("result","before btnCheckOutListener.setEnabled(true)  : "+userid);
        btnCheckOutListener.setEnabled(true);
        btnCheckOutListener.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        Log.d("result","After btnCheckInListener.setEnabled(false)  : "+btnCheckOutListener);
    }

    public void btnCheckOutListener(View view) {

        pb.setVisibility(View.INVISIBLE);
        String empid=PreferenceManager.getDefaultSharedPreferences(this).getString("empid","");
        String attid=PreferenceManager.getDefaultSharedPreferences(this).getString("attid","");
        Log.d("result","btnCheckOutListener  : "+attid);
        Toast.makeText(this, "Got SP Checkout value: "+attid, Toast.LENGTH_LONG).show();

        SqlAdapter sqlAdapter = new SqlAdapter(this);
        sqlAdapter.execute("checked_out", empid,"2",attid);
        Toast.makeText(this, "YOU HAVE MARKED YOUR ATTENDANCE FOR TODAY !", Toast.LENGTH_LONG).show();
        btnCheckOutListener.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        btnCheckOutListener.setEnabled(false);
        Log.d("result"," btnCheckOutListener.setEnabled(false) : "+btnCheckOutListener);


    }

    @Override
    public void getDateResponse(String output) {

        pb.setVisibility(View.INVISIBLE);

        Calendar calendar = Calendar.getInstance();
        // SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        //   String time = "Current Time :" + format.format(calendar.getTimeInMillis());
        calendar.setTimeInMillis(Long.parseLong(output));
       // SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss a");


        months = new String[12];
        months[0] = "January";
        months[1] = "February";
        months[2] = "March";
        months[3] = "April";
        months[4] = "May";
        months[5] = "June";
        months[6] = "July";
        months[7] = "August";
        months[8] = "September";
        months[9] = "October";
        months[10]= "November";
        months[11] = "December";




        TextView textviewdate = findViewById(R.id.textViewDate);
        TextView textviewtime = findViewById(R.id.textViewTime);
        textviewdate.setText(calendar.get(Calendar.DAY_OF_MONTH)+"-"+months[calendar.get(Calendar.MONTH)]+"-"+calendar.get(Calendar.YEAR));
        int Hours=calendar.get(Calendar.HOUR);
        int Minutes=calendar.get(Calendar.MINUTE);
        int AmPm=calendar.get(Calendar.AM_PM);

        String HOURS, MINUTES, AM_PM;
        if(Hours<=9) {
            HOURS = "0" + Hours;
        }else {
            HOURS = "" + Hours;
        }
        if(Minutes<=9) {
            MINUTES = "0" + Minutes;
        }else{
            MINUTES = ""+ Minutes;
        }
        if(AmPm==0) {
            AM_PM = "AM";
        }else{
            AM_PM = "PM";
        }
        textviewtime.setText(HOURS+":"+MINUTES+" "+AM_PM);


    }

   /* public void TestbtnListner(View view) {

        PreferenceManager.getDefaultSharedPreferences(MarkAttendance.this).edit().putBoolean("ischeckedin",false).apply();
        PreferenceManager.getDefaultSharedPreferences(MarkAttendance.this).edit().putString("attid","").apply();
        PreferenceManager.getDefaultSharedPreferences(MarkAttendance.this).edit().putInt("today",0).apply();

        Toast.makeText(this, "PREFERENCES VALUES ARE RESET...", Toast.LENGTH_LONG).show();


    }*/
}
