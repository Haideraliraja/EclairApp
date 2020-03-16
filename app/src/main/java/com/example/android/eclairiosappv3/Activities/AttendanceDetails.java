package com.example.android.eclairiosappv3.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.eclairiosappv3.Adapters.LVAttendanceDetailsAdapter;
import com.example.android.eclairiosappv3.Models.AttendanceDetailsModel;
import com.example.android.eclairiosappv3.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

public class AttendanceDetails extends AppCompatActivity {

    ArrayList<String> DateArray;
    ArrayList<String> dateArr;
    ArrayList<String> checkinArr;
    ArrayList<String> checkoutArr;
    ArrayList<Long> totalhoursArr;
    static String months[];
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ListView listView;
    ProgressBar pb;
    long hourstotal;
    TextView TotalHours;
    TextView TotalDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_details);
        listView = (ListView) findViewById(R.id.listviewjson);
        pb = (ProgressBar) findViewById(R.id.progressBar);
        TotalHours = (TextView)findViewById(R.id.thours);
        TotalDays = (TextView)findViewById(R.id.tdays);


        pb.setVisibility(View.INVISIBLE);

        DateArray = new ArrayList<String>();
        dateArr = new ArrayList<String>();
        checkinArr = new ArrayList<String>();
        checkoutArr = new ArrayList<String>();
        totalhoursArr = new ArrayList<Long>();

        //new CalculationModel();
        new BackgroundTask().execute();

    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {

        String json_url;
        String JSON_STRING;


        @Override
        protected void onPreExecute() {

            json_url = "http://192.168.18.5/edbapp/json_get_data.php";

        }

        @Override
        protected String doInBackground(Void... voids) {
            String userid= PreferenceManager.getDefaultSharedPreferences(AttendanceDetails.this).getString("empid","");
            try {
                pb.setVisibility(View.INVISIBLE);
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


                Log.d("userid","Attendance details  -- doInBackground  : "+userid);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("empid", "UTF-8") + "=" + URLEncoder.encode(userid, "UTF-8");

                Log.d("userid","URLEncode  -- doInBackground  : "+data);
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));



                StringBuilder stringBuilder = new StringBuilder();

                while ((JSON_STRING = bufferedReader.readLine()) != null) {

                    stringBuilder.append(JSON_STRING + "\n");
                    Log.d("userid","BufferReader.readLine  -- doInBackground  : "+stringBuilder);
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
           //TextView textView = (TextView)findViewById(R.id.textviewjson);
           //textView.setText(result);


           json_string = result;

            if(json_string==null){
                Toast.makeText(AttendanceDetails.this, "First Get Json Data for Server..", Toast.LENGTH_SHORT).show();
            } else {

                try {

                    Log.d("result","AttendanceDetails--- onPostExecute  : "+result);

                   // textView.setText(result);

                    jsonObject = new JSONObject(result);
                    jsonArray = jsonObject.getJSONArray("server_response");
                    int count=0;
                    ArrayList<AttendanceDetailsModel> list = new ArrayList<>();


                    hourstotal = 0;
                    String CheckIn,CheckOut;//DateAtt;
                    while (count<jsonArray.length()) {

                        JSONObject JO = jsonArray.getJSONObject(count);
                        //DateAtt = JO.getString("empid");
                        CheckIn = JO.getString("checkedin_time");
                        CheckOut = JO.getString("checkedout_time");

                        //Separating date from the checkin_time
                        Log.d("DateConverter","AttendanceDetails--- onPostExecute  : "+CheckIn);
                        DateConverter(CheckIn);
                        Calendar clndr=Calendar.getInstance();
                        clndr.setTimeInMillis(Long.parseLong(CheckOut));
                        int Hours=clndr.get(Calendar.HOUR);
                        int Minutes=clndr.get(Calendar.MINUTE);
                        int AmPm=clndr.get(Calendar.AM_PM);

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
                        checkoutArr.add(HOURS+":"+MINUTES+" "+AM_PM);


                       /* CalculationModel calculationModel = new CalculationModel(CheckIn,CheckOut);
                        listCal.add(calculationModel);

                        int checkIn = Integer.parseInt(CheckIn);
                        int checkOut = Integer.parseInt(CheckOut);
                        Log.d("result2","String parsing to integar :"+checkIn);
                        calculationModel.MilliCoverter(checkIn,checkOut);*/


                       long checkIn = Long.parseLong(CheckIn);
                       long checkOut = Long.parseLong(CheckOut);
                        Log.d("result2"," before DayTimeConverter :"+checkIn);
                        Log.d("result2"," After DayTimeConverter :"+checkOut);

                       DayTimeConverter(checkOut,checkIn);

                        AttendanceDetailsModel attendanceDetailsModel = new AttendanceDetailsModel(CheckIn,CheckOut);
                        list.add(attendanceDetailsModel);
                        count++;

                    }

                    LVAttendanceDetailsAdapter lvAttendanceDetailsAdapter = new LVAttendanceDetailsAdapter(AttendanceDetails.this,list,DateArray,checkinArr,checkoutArr);
                    listView.setAdapter(lvAttendanceDetailsAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        protected void DateConverter(String CheckIn){
            Calendar calendar = Calendar.getInstance();
            // SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            //   String time = "Current Time :" + format.format(calendar.getTimeInMillis());
            Log.d("DateConverter","DateConverter() ---  : "+CheckIn);
            calendar.setTimeInMillis(Long.parseLong(CheckIn));
            // SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss a");

            Log.d("DateConverter","DateConverter after Parse Long --- onPostExecute  : "+CheckIn);

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

            Calendar clndr=Calendar.getInstance();
            clndr.setTimeInMillis(Long.parseLong(CheckIn));
            int Hours=clndr.get(Calendar.HOUR);
            int Minutes=clndr.get(Calendar.MINUTE);
            int AmPm=clndr.get(Calendar.AM_PM);

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
            checkinArr.add(HOURS+":"+MINUTES+" "+AM_PM);

            DateArray.add(calendar.get(Calendar.DAY_OF_MONTH)+"-"+months[calendar.get(Calendar.MONTH)]+"-"+calendar.get(Calendar.YEAR));


            //.setText(calendar.get(Calendar.DAY_OF_MONTH)+"-"+months[calendar.get(Calendar.MONTH)]+"-"+calendar.get(Calendar.YEAR));
        }
    }


    @SuppressLint("SetTextI18n")
    protected void DayTimeConverter(Long checkout, Long checkin){

        long diff = checkout - checkin;
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        Log.d("result2","=== IN DayTimeConverter = "+hours);

        totalhoursArr.add((hours));
        hourstotal = hourstotal + hours;

        Integer arrayLength = (Integer) totalhoursArr.size();

        TotalHours.setText(hourstotal+"");
        TotalDays.setText(""+arrayLength);
        Log.d("result2","=== IN DayTimeConverter == totalhours = "+totalhoursArr);
        Log.d("result2","=== IN DayTimeConverter Total hours = "+hourstotal);
        Log.d("result2","=== IN DayTimeConverter ArraySize = "+arrayLength);
    }

}
