package com.example.android.eclairiosappv3.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android.eclairiosappv3.Activities.AttendanceDetails;
import com.example.android.eclairiosappv3.Models.AttendanceDetailsModel;
import com.example.android.eclairiosappv3.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LVAttendanceDetailsAdapter extends ArrayAdapter {

    ArrayList<AttendanceDetailsModel> list = new ArrayList();
    ArrayList<String> DateArray = new ArrayList<>();
    ArrayList<String> checkin = new ArrayList<>();
    ArrayList<String> checkout = new ArrayList<>();

    public LVAttendanceDetailsAdapter(@NonNull Context context,ArrayList<AttendanceDetailsModel> list,ArrayList<String> DateArray,ArrayList<String> checkin,ArrayList<String> checkout) {
        super(context, R.layout.row_layout_attendancedetails);

        this.list = list;
        this.DateArray = DateArray;
        this.checkin = checkin;
        this.checkout = checkout;

    }

    public void add(AttendanceDetailsModel object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row;
        row = convertView;
        ADMHolder admHolder;


        if ( row == null ) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout_attendancedetails, parent, false);
            admHolder = new ADMHolder();
            admHolder.txDate = row.findViewById(R.id.tx_datejson);
            admHolder.tx_Cin = row.findViewById(R.id.tx_checkedInjson);
            admHolder.tx_C_out = row.findViewById(R.id.tx_checkedOutjson);

            row.setTag(admHolder);

        } else {

            admHolder = (ADMHolder) row.getTag();
        }

        AttendanceDetailsModel attendanceDetailsModel = list.get(position);
       admHolder.txDate.setText(DateArray.get(position));
        admHolder.tx_Cin.setText(checkin.get(position));
        admHolder.tx_C_out.setText(checkout.get(position));


        return row;
    }

    static class ADMHolder {

        TextView txDate;
        TextView tx_Cin, tx_C_out;
    }
}
