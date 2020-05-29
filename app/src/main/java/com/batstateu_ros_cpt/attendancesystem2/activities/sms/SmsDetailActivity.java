package com.batstateu_ros_cpt.attendancesystem2.activities.sms;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.widget.EditText;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.models.sms.SmsDetailModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import cn.pedant.SweetAlert.SweetAlertDialog;

@EActivity(R.layout.activity_sms_detail)
@OptionsMenu(R.menu.menu_update_basic)
public class SmsDetailActivity extends AppCompatActivity {

    @Extra
    int id;
    @ViewById
    EditText etRecipient;
    @ViewById
    EditText etMessage;
    @AfterViews
    void AfterView(){
        setData();
    }

    private void setData() {

        SmsDetailModel smsDetailModel = Factories.createSms().getSmsDetail(id);
        etRecipient.setText(smsDetailModel.getContactPerson() + " [" + smsDetailModel.getContactPersonNumber() + "]");
        etMessage.setText(smsDetailModel.getMessage());
    }
    /** menu click*/
    @OptionsItem(R.id.action_delete)
    void delete(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Record can't be recover!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Factories.createSms().delete(id);
                        setResult(RESULT_OK);
                        finish();
                        showDeletedMessage();
                    }
                })
                .show();
    }
    void showDeletedMessage(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Deleted!")
                .setContentText("Record has been deleted!")
                .setConfirmText("OK")
                .show();
    }
    @Click(R.id.btnSend)
    void send(){

        Factories.createSms().SendSMS(id);
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

}
