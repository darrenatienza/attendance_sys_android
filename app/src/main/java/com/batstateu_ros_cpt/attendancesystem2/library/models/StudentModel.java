package com.batstateu_ros_cpt.attendancesystem2.library.models;

import android.app.admin.SystemUpdatePolicy;

public class StudentModel {
    Integer id;
    String fullName;
    String address;
    String contact_person;
    public Integer getClass_section_id() {
        return class_section_id;
    }

    public void setClass_section_id(Integer class_section_id) {
        this.class_section_id = class_section_id;
    }

    Integer class_section_id;
    public String getStudent_code() {
        return student_code;
    }

    public void setStudent_code(String student_code) {
        this.student_code = student_code;
    }

    String student_code;

    public String getContact_person_number() {
        return contact_person_number;
    }

    public void setContact_person_number(String contact_person_number) {
        if (contact_person_number == ""){

        }
        this.contact_person_number = contact_person_number;
    }

    String contact_person_number;

    public String getContact_person() {
        return contact_person;
    }

    public void setContact_person(String contact_person) {
        this.contact_person = contact_person;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public StudentModel(){

    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


}
