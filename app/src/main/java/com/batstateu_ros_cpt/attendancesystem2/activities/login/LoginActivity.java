package com.batstateu_ros_cpt.attendancesystem2.activities.login;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.batstateu_ros_cpt.attendancesystem2.MainActivity;
import com.batstateu_ros_cpt.attendancesystem2.MainActivity_;
import com.batstateu_ros_cpt.attendancesystem2.Misc.MyPrefs_;
import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.models.user.UserModel;
import com.github.thunder413.datetimeutils.DateTimeStyle;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @ViewById
    EditText etUserName;
    @ViewById
    EditText etPassword;
    @Pref
    MyPrefs_ myPrefs;
    private Date expDate;

    @AfterViews
    void afterViews(){
        expDate = DateTimeUtils.formatDate("2020-04-27");
        //CheckExpiration();
        DateTimeUtils.setTimeZone("GMT+8");
    }
    void login(){
        try{
            String userName = etUserName.getText().toString();
            String password = etPassword.getText().toString();
            UserModel user = Factories.createUser().get(userName,password);
            if (user.getId() != null){

                myPrefs.userFullName().put(user.getFullName());
                etUserName.setText("");
                etPassword.setText("");
                MainActivity_.intent(this).start();

            }else{
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Trial Version")
                        .setContentText("Invalid credentials!")

                        .show();
            }
        }catch (Exception ex){

        }
    }
    void ShowTrialVersionNotif(){
        String expDate =  ("This application is trial version and will expire on ").concat(DateTimeUtils.formatWithPattern(this.expDate,"EEEE, MMMM dd, yyyy"));
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Trial Version")
                .setContentText(expDate)
                .show();
    }
    void CheckExpiration(){

        if(new Date().after(expDate)){
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);

            sweetAlertDialog.setTitleText("Trial Version Expires")
                    .setContentText("This application has been expired. Please contact developer.")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismiss();
                            finish();
                        }
                    })
                    .show();
            sweetAlertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    finish();
                }
            });



        }else{
            ShowTrialVersionNotif();
        }
    }
    @Click
    void btnLogin(){
        login();
    }
}
