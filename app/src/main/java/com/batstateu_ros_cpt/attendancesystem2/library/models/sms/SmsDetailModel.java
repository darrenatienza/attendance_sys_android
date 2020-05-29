package com.batstateu_ros_cpt.attendancesystem2.library.models.sms;

public class SmsDetailModel {
    int id;
    String contactPerson;
    String contactPersonNumber;
    String message;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPersonNumber() {
        return contactPersonNumber;
    }

    public void setContactPersonNumber(String contactPersonNumber) {
        this.contactPersonNumber = contactPersonNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
