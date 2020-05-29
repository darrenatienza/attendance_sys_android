package com.batstateu_ros_cpt.attendancesystem2.library.models.attendance;

import java.util.Date;

public class AttendanceSubjectListModel {
    int subjectID;
    int classSectionID;
    String classSectionName;
    int presentCount;
    int absentCount;
    String subjectName;
    Date date;
    int lateCount;

    public int getLateCount() {
        return lateCount;
    }

    public void setLateCount(int lateCount) {
        this.lateCount = lateCount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public int getClassSectionID() {
        return classSectionID;
    }

    public void setClassSectionID(int classSectionID) {
        this.classSectionID = classSectionID;
    }

    public String getClassSectionName() {
        return classSectionName;
    }

    public void setClassSectionName(String classSectionName) {
        this.classSectionName = classSectionName;
    }



    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(int presentCount) {
        this.presentCount = presentCount;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(int absentCount) {
        this.absentCount = absentCount;
    }
}
