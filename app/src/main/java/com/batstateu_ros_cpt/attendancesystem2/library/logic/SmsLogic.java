package com.batstateu_ros_cpt.attendancesystem2.library.logic;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

import com.batstateu_ros_cpt.attendancesystem2.activities.sms.SmsDetailActivity;
import com.batstateu_ros_cpt.attendancesystem2.library.GlobalApplication;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IBase;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.ISms;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.ISubject;
import com.batstateu_ros_cpt.attendancesystem2.library.data.SmsData;
import com.batstateu_ros_cpt.attendancesystem2.library.data.StudentData;
import com.batstateu_ros_cpt.attendancesystem2.library.data.SubjectData;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SmsModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.StudentModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SubjectModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.sms.SmsDetailModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.sms.SmsListModel;


import java.util.List;

public class SmsLogic implements IBase<SmsModel>, ISms {

    @Override
    public List<SmsModel> getAll() {
        Context context = GlobalApplication.getAppContext();
        SmsData data = SmsData.getInstance(context,null);
        data.open();
        List<SmsModel> list = data.getAll();
        data.close();
        return  list;
    }

    @Override
    public List<SmsModel> getAll(String criteria) {
        Context context = GlobalApplication.getAppContext();
        SmsData data = SmsData.getInstance(context,null);
        data.open();
        List<SmsModel> list = data.getAll(criteria);
        data.close();
        return  list;
    }

    @Override
    public SmsModel get(Integer id) {
        Context context = GlobalApplication.getAppContext();
        SmsData data = SmsData.getInstance(context,null);
        data.open();
        SmsModel model = data.get(id);
        data.close();
        return  model;
    }


    @Override
    public void add(SmsModel model) {
        Context context = GlobalApplication.getAppContext();
        SmsData data = SmsData.getInstance(context,null);
        data.open();
        data.add(model);
        data.close();
    }

    @Override
    public void edit(Integer id, SmsModel model) {
        Context context = GlobalApplication.getAppContext();
        SmsData data = SmsData.getInstance(context,null);
        data.open();
        data.edit(id,model);
        data.close();
    }

    @Override
    public void delete(Integer id) {
        Context context = GlobalApplication.getAppContext();
        SmsData data = SmsData.getInstance(context,null);
        data.open();
        data.delete(id);
        data.close();
    }




    @Override
    public List<SmsListModel> getSmsList(int schoolYearID, int gradingPeriod) {
        Context context = GlobalApplication.getAppContext();
        SmsData data = SmsData.getInstance(context,null);
        data.open();
        List<SmsListModel> list = data.getSmsList(schoolYearID,gradingPeriod);
        data.close();
        return  list;
    }

    @Override
    public SmsDetailModel getSmsDetail(int id) {
        Context context = GlobalApplication.getAppContext();
        SmsData data = SmsData.getInstance(context,null);
        data.open();
        SmsDetailModel detail = data.getSmsDetail(id);
        data.close();
        return  detail;
    }

    @Override
    public Integer getUnsentSMSCount() {
        Context context = GlobalApplication.getAppContext();
        SmsData data = SmsData.getInstance(context,null);
        data.open();
        int smsCount = data.getUnsentSMSCount();
        data.close();
        return  smsCount;
    }

    @Override
    public void SendSMS(int id) {

        Context context = GlobalApplication.getAppContext();
        SmsData data = SmsData.getInstance(context,null);
        StudentData studentDatadata = StudentData.getInstance(context,null);
        data.open();
        studentDatadata.open();
        SmsModel model = data.get(id);
            StudentModel studentModel = studentDatadata.get(model.getStudentID());
            if (studentModel != null){
                sendSMS(studentModel.getContact_person_number(),model.getMessage());
                data.setSmsSend(id);
            }


        data.close();
        studentDatadata.close();
    }

    @Override
    public Integer getSentSMSCount() {
        Context context = GlobalApplication.getAppContext();
        SmsData data = SmsData.getInstance(context,null);
        data.open();
        int smsCount = data.getSentSMSCount();
        data.close();
        return  smsCount;
    }

    void sendSMS(String number, String message){
        //Getting intent and PendingIntent instance
        Intent intent=new Intent(GlobalApplication.getAppContext(),SmsLogic.class);
        PendingIntent pi=PendingIntent.getActivity(GlobalApplication.getAppContext(), 0, intent,0);

//Get the SmsManager instance and call the sendTextMessage method to send message
        SmsManager sms=SmsManager.getDefault();
        sms.sendTextMessage(number, null, message, pi,null);
    }
}
