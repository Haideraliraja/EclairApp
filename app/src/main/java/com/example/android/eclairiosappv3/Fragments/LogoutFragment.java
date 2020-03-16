package com.example.android.eclairiosappv3.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.android.eclairiosappv3.Activities.LoginActivity;

public class LogoutFragment extends Fragment {

    Context thiscontext;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        thiscontext = container.getContext();
        DialogMessage();

        return null;
    }


 public void DialogMessage(){

     final AlertDialog.Builder builder = new AlertDialog.Builder(thiscontext);
     builder.setMessage("Do you want to Logout ?");
     builder.setCancelable(true);
     builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialogInterface, int i) {
             PreferenceManager.getDefaultSharedPreferences(thiscontext).edit().putBoolean("isLogin", false).apply();
             Intent Intent = new Intent(thiscontext,LoginActivity.class);
             startActivity(Intent);
             ((Activity)thiscontext).finish();
             ((Activity)thiscontext).finishAffinity();
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