package com.batstateu_ros_cpt.attendancesystem2.library.models.attendance;

public class AttendanceAbsentListModel {
        int studentID;
        String fullName;
        String  subjectTitle;
        int absents;
        String number;
        String recipient;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public int getAbsents() {
        return absents;
    }

    public void setAbsents(int absents) {
        this.absents = absents;
    }
}
