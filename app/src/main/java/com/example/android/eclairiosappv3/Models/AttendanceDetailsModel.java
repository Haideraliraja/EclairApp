package com.example.android.eclairiosappv3.Models;

public class AttendanceDetailsModel {

   //private String DateAtt;
    private String checkIn,checkOut;


    public AttendanceDetailsModel(String checkIn, String checkOut){

    // this.setDateAtt(DateAtt);
        this.setCheckIn(checkIn);
        this.setCheckOut(checkOut);

    }

  // public String getDateAtt() { return DateAtt; }

    //public void setDateAtt(String DateAtt) { this.DateAtt = DateAtt; }
    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }
}
