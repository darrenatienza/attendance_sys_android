package com.batstateu_ros_cpt.attendancesystem2.library.models;

import java.sql.Timestamp;

public class SubjectModel {


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }





    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    String title;

    int grade;

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
