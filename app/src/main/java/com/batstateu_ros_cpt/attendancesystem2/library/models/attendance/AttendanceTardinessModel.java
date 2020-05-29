package com.batstateu_ros_cpt.attendancesystem2.library.models.attendance;

public class AttendanceTardinessModel {

    int studentID;
    String studentName;
    int schoolYearID;
    String classSectionName;
    int absentCount;
    int lateCount;
    int gradingPeriod;
    int classSectionID;

    public int getClassSectionID() {
        return classSectionID;
    }

    public void setClassSectionID(int classSectionID) {
        this.classSectionID = classSectionID;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getSchoolYearID() {
        return schoolYearID;
    }

    public void setSchoolYearID(int schoolYearID) {
        this.schoolYearID = schoolYearID;
    }

    public String getClassSectionName() {
        return classSectionName;
    }

    public void setClassSectionName(String classSectionName) {
        this.classSectionName = classSectionName;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(int absentCount) {
        this.absentCount = absentCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public void setLateCount(int lateCount) {
        this.lateCount = lateCount;
    }

    public int getGradingPeriod() {
        return gradingPeriod;
    }

    public void setGradingPeriod(int gradingPeriod) {
        this.gradingPeriod = gradingPeriod;
    }
}
