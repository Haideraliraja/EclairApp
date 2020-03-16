package com.example.android.eclairiosappv3.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.example.android.eclairiosappv3.Activities.MainActivity;

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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;

public class SqlAdapter extends AsyncTask<String,Void,String> {

    String method;

    public dateInterface dateData = null;

    public interface dateInterface{
        void getDateResponse(String output);
    }

    AlertDialog alertDialog;
    Context ctx;

    public SqlAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
//        alertDialog=new AlertDialog.Builder(ctx).create();
//        alertDialog.setTitle("Login Information....");
    }

    @Override
    public String doInBackground(String... params) {

        String reg_url = "http://192.168.18.5/edbapp/registers.php";
        String login_url = "http://192.168.18.5/edbapp/login.php";
        String set_attendance = "http://192.168.18.5/edbapp/setAttendance.php";
        String getDateandTime = "http://192.168.18.5/edbapp/getMillis.php";

        method = params[0];
        if ( method.equals("register") ) {
            String fname = params[1];
            String lname = params[2];
            String cnic = params[3];
            String phno = params[4];
            String user_email = params[5];
            String user_pass = params[6];

            try {
                URL url = new URL(reg_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));

                String data = URLEncoder.encode("fname", "UTF-8")+"="+URLEncoder.encode(fname, "UTF-8")+"&"+
                        URLEncoder.encode("lname", "UTF-8")+"="+URLEncoder.encode(lname, "UTF-8")+"&"+
                        URLEncoder.encode("cnic", "UTF-8")+"="+URLEncoder.encode(cnic, "UTF-8")+"&"+
                        URLEncoder.encode("phno", "UTF-8")+"="+URLEncoder.encode(phno, "UTF-8")+"&"+
                        URLEncoder.encode("user_email", "UTF-8")+"="+URLEncoder.encode(user_email, "UTF-8")+"&"+
                        URLEncoder.encode("user_pass", "UTF-8")+"="+URLEncoder.encode(user_pass, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream, "iso-8859-1")));

                String response = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            if ( method.equals( "login" ) ) {
                String login_email = params[1];
                String login_pass = params[2];
                //String ttt = params[3];
                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data = URLEncoder.encode("user_email", "UTF-8") + "=" + URLEncoder.encode(login_email, "UTF-8") + "&" +
                            URLEncoder.encode("user_pass", "UTF-8") + "=" + URLEncoder.encode(login_pass, "UTF-8") + "&" +
                            URLEncoder.encode("login_user", "UTF-8") + "=" + URLEncoder.encode("ttt", "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream, "iso-8859-1")));

                    String response = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {

                        response += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else
            if ( method.equals( "checked_in" ) ) {
                String empid = params[1];
                String type = params[2];
                try {
                    URL url = new URL(set_attendance);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data = URLEncoder.encode("empid", "UTF-8") + "=" + URLEncoder.encode(empid, "UTF-8") + "&" +
                            URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
                    //+ "&" + URLEncoder.encode("login_user", "UTF-8") + "=" + URLEncoder.encode("ttt", "UTF-8");

                    Log.d("result","checked_in request  : "+data);

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream, "iso-8859-1")));

                    String response = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {

                        response += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else
            if ( method.equals( "checked_out" ) ) {
                String empid = params[1];
                String type = params[2];
                String attid = params[3];
                try {
                    URL url = new URL(set_attendance);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data2 = URLEncoder.encode("empid", "UTF-8") + "=" + URLEncoder.encode(empid, "UTF-8")+"&"+
                            URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8")
                            +"&"+ URLEncoder.encode("a_id", "UTF-8") + "=" + URLEncoder.encode(attid, "UTF-8");

                    Log.d("result","checked_out request  : "+data2);

                    bufferedWriter.write(data2);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream, "iso-8859-1")));

                    String response = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {

                        response += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }  else
            /*if ( method.equals( "login_user_emp" ) ) {
                String empid = params[1];

                try {
                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String data2 = URLEncoder.encode("empid", "UTF-8") + "=" + URLEncoder.encode(empid, "UTF-8");

                    Log.d("result","checked_out request  : "+data2);

                    bufferedWriter.write(data2);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    Log.d("result","checked_out request  : "+data2);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream, "iso-8859-1")));

                    String response = "";
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        Log.d("result","checked_out request while  : "+data2);
                        response += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    Log.d("result","checked_out request before response : "+data2);
                    return response;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }else*/ if ( method.equals( "getDateTime" ) ) {
           /* String view_date = params[1];
            String view_time = params[2];*/
            try {
                URL url = new URL(getDateandTime);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                String data = URLEncoder.encode("login_email", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8") + "&" +
                        URLEncoder.encode("login_pass", "UTF-8") + "=" + URLEncoder.encode("", "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream, "iso-8859-1")));

                String response = "";
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {

                    response += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


    @Override
    protected void onPostExecute(String result) {
        Log.d("result","checked_out request before tryCatch : "+result);

      try{
          Log.d("result","onPostExecute  : "+result);


          if ( result.contains("Login_true") ) {


              JSONObject json = new JSONObject(result);
              String value = json.getString("empid");

              Log.d("result","empid is  : "+value);

              PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString("empid",value).apply();



              Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();

              Intent intent = new Intent(ctx, MainActivity.class);
              ctx.startActivity(intent);
              ((Activity)ctx).finish();
              ((Activity)ctx).finishAffinity();


          }else /*{onFalseLogin();}*/
          if (result.contains("attendance_in") ) {

              Log.d("result","attendance_in is  : "+result);


              JSONObject json = new JSONObject(result);
              String value = json.getString("success");

              Log.d("result","JSONObject json is  : "+value);

              String[] separated = value.split(",,");
              Log.d("result","separated VALUE before trim  : "+separated[1]);
              String newValue = separated[1];
              Log.d("result","separated VALUE After trim  : "+newValue);



              PreferenceManager.getDefaultSharedPreferences(ctx).edit().putString("attid",newValue).apply();
              PreferenceManager.getDefaultSharedPreferences(ctx).edit().putBoolean("ischeckedin",true).apply();

              Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();



//              Intent intent = new Intent(ctx, MarkAttendance.class);
//              ctx.startActivity(intent);


          }else
          if (result.contains("attendance_out") ) {

              Log.d("result","attendance_out is  : "+result);

              PreferenceManager.getDefaultSharedPreferences(ctx).edit().putBoolean("ischeckedin",false).apply();


              Calendar c = Calendar.getInstance();
              int day = c.get(Calendar.DAY_OF_MONTH);

              PreferenceManager.getDefaultSharedPreferences(ctx).edit().putInt("today",day).apply();

              Toast.makeText(ctx, result, Toast.LENGTH_LONG).show();

//              Intent intent = new Intent(ctx, MainActivity.class);
//              ctx.startActivity(intent);


          }else{
              dateData.getDateResponse(result);
//              alertDialog.setMessage(result);
//              alertDialog.show();
              Toast.makeText(ctx,result,Toast.LENGTH_LONG).show();
          }

      }catch (NullPointerException e){} catch (JSONException e) {
          e.printStackTrace();
      }

    }

    private void onFalseLogin() {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(ctx);
        builder.setMessage("Wrong Email or Password. Try Again !");
        builder.setCancelable(true);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ((Activity)ctx).finish();
            }
        });

        builder.setPositiveButton(" ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
