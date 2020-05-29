package com.batstateu_ros_cpt.attendancesystem2.library.models;

public class ClassSectionModel {
    int id;
    String name;
    int grade;
    String schoolYear;
    int schoolYearID;

    public int getSchoolYearID() {
        return schoolYearID;
    }

    public void setSchoolYearID(int schoolYearID) {
        this.schoolYearID = schoolYearID;
    }



    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }
}
