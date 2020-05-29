package com.batstateu_ros_cpt.attendancesystem2.library.models;

import java.util.Date;

public class SmsModel {
    int id;
    String message;
    boolean isSend;
    Date date;
    int studentID;
    int schoolYearID;
    int gradingPeriod;


    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public int getSchoolYearID() {
        return schoolYearID;
    }

    public void setSchoolYearID(int schoolYearID) {
        this.schoolYearID = schoolYearID;
    }

    public int getGradingPeriod() {
        return gradingPeriod;
    }

    public void setGradingPeriod(int gradingPeriod) {
        this.gradingPeriod = gradingPeriod;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
}
