package com.batstateu_ros_cpt.attendancesystem2.library.models.sms;

import java.util.Date;

public class SmsListModel {
    int id;
    Date date;
    String recipient;
    boolean isSend;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
}
