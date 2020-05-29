package com.batstateu_ros_cpt.attendancesystem2.activities.attendance;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.GlobalApplication;
import com.batstateu_ros_cpt.attendancesystem2.library.logic.SmsLogic;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SmsModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.StudentModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceTardinessModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceTodayModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.Console;
import java.util.Date;
import java.util.logging.ConsoleHandler;

import cn.pedant.SweetAlert.SweetAlertDialog;

@EActivity(R.layout.activity_attendance_tardiness)
public class AttendanceTardinessActivity extends AppCompatActivity {

    @Extra
    int studentID;
    @Extra
    int schoolYearID;
    @Extra
    int gradingPeriod;

    @ViewById
    Toolbar toolbar;
    @ViewById
    TextView tvStudentName;
    @ViewById
    TextView tvClassSectionName;
    @ViewById
    TextView tvPresentTodayCount;
    @ViewById
    TextView tvLateTodayCount;
    @ViewById
    TextView tvTotalLateCount;
    @ViewById
    TextView tvTotalAbsentCount;
    @ViewById
    EditText etMessage;
    @ViewById
    TextView tvContactPerson;
    private String contactPersonNumber;
    private String studentName;

    @Click
    void fab(){
        if(etMessage.getText().toString().isEmpty()){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error")
                    .setContentText("Message must not be empty!")
                    .show();
        }else{
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Send this message!")
                    .setConfirmText("Yes,send it!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            String message = etMessage.getText().toString();
                            sDialog.dismiss();
                            sendMessage(message);

                        }
                    })
                    .show();
        }

    }
    @Background
    void sendMessage(String message) {
        try{

            //Getting intent and PendingIntent instance
            Intent intent=new Intent(this,AttendanceTardinessActivity.class);
            PendingIntent pi=PendingIntent.getActivity(GlobalApplication.getAppContext(), 0, intent,0);
            //Get the SmsManager instance and call the sendTextMessage method to send message
            SmsManager sms=SmsManager.getDefault();
            sms.sendTextMessage(contactPersonNumber, null, message, pi,null);
            SmsModel model = new SmsModel();
            model.setStudentID(studentID);
            model.setSchoolYearID(schoolYearID);
            model.setGradingPeriod(gradingPeriod);
            model.setMessage(message);
            model.setSend(true);
            model.setDate(new Date());
            Factories.createSms().add(model);
            showSMSSuccess();
        }catch (Exception ex){
            showException(ex.getMessage());
        }


    }

    @UiThread
    void showSMSSuccess() {

        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Sent!")
                .setContentText("Message has been sent!")
                .setConfirmText("OK")
                .show();
    }

    @UiThread
    void showException(String message) {

        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error")
                .setContentText(message)
                .show();
    }
    @AfterViews
    void afterViews(){
        setSupportActionBar(toolbar);
        setData();
    }

    private void setData() {
        try{
            AttendanceTardinessModel tardinessModel = Factories.createAttendance().getAttendanceTardiness(studentID,schoolYearID,gradingPeriod);
            AttendanceTodayModel attendanceTodayModel = Factories.createAttendance().getAttendanceToday(studentID,new Date());
            Integer todaySubjectCount = Factories.createAttendance().getTotalSubjectToday(tardinessModel.getClassSectionID(),new Date());
            StudentModel studentModel = Factories.createStudent().get(studentID);
            tvClassSectionName.setText(tardinessModel.getClassSectionName());
            tvLateTodayCount.setText("Late on " + String.valueOf( attendanceTodayModel.getLateCount()) + " out of " + todaySubjectCount.toString() + " Subjects");
            tvPresentTodayCount.setText("Present on " + String.valueOf( attendanceTodayModel.getPresentCount()) + " out of " + todaySubjectCount.toString() + " Subjects");
            tvTotalAbsentCount.setText("Total Absent: " + String.valueOf( tardinessModel.getAbsentCount()));
            tvTotalLateCount.setText("Total Late: " + String.valueOf( tardinessModel.getLateCount()));
            tvContactPerson.setText(studentModel.getContact_person() + "@" + studentModel.getContact_person_number());
            contactPersonNumber = studentModel.getContact_person_number();
            tvStudentName.setText(studentModel.getFullName());
            studentName = tardinessModel.getStudentName();
        }catch (Exception ex){
            Log.e("Error",ex.getMessage());
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_today_absent:
                if (checked)
                    etMessage.setText("Your child " + studentName +
                            " is absent today without any excuses.");
                    break;
            case R.id.radio_3late:
                if (checked)
                    etMessage.setText("Your child " +  studentName +
                            " has been late for 3 consecutive days. Please report to adviser.");
                    break;
            case R.id.radio_3day_absent:
                if (checked)
                    etMessage.setText("Your child "  + studentName +
                            " has been absent for 3 consecutive days with out any excuses. Please report to adviser.");
                    break;
        }
    }
}
