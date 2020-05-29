package com.batstateu_ros_cpt.attendancesystem2.activities.user;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.models.ClassSectionModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.StudentModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.user.UserModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

@EActivity(R.layout.activity_user_update)
@OptionsMenu(R.menu.menu_update_basic)
public class UserUpdateActivity extends AppCompatActivity {

    @Extra
    int userID;
    @ViewById
    TextInputEditText etUserName;
    @ViewById
    TextInputEditText etPassword;
    @ViewById
    TextInputEditText etFullName;

    @AfterViews
    void afterViews(){
        setData();
    }

    private void setData() {
        if(userID != 0){
            UserModel model = Factories.createUser().get(userID);
            etFullName.setText(model.getFullName());
            etPassword.setText(model.getPassword());
            etUserName.setText(model.getUsername());

        }
    }


    @Click(R.id.btnSave)
    void save(){
        try{
            if (!validate()) {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Submit")
                        .setContentText("All fields are required!")
                        .show();
                return;
            }
            if(userID > 0){
                UserModel model = new UserModel();
                model.setFullName(etFullName.getText().toString());
                model.setPassword(etPassword.getText().toString());
                model.setUsername(etUserName.getText().toString());

                Factories.createUser().edit(userID,model);
            }else{
                UserModel model = new UserModel();
                model.setFullName(etFullName.getText().toString());
                model.setPassword(etPassword.getText().toString());
                model.setUsername(etUserName.getText().toString());
                Factories.createUser().add(model);
            }
            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Save")
                    .setContentText("Record has been saved!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            setResult(RESULT_OK);
                            finish();
                        }
                    })
                    .show();
        }catch (Exception e){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(e.getMessage())
                    .setContentText("Something went wrong!")
                    .show();
        }



    }
    private boolean validate() {
        if(etUserName.getText().length() == 0){
            return  false;
        }
        if (etPassword.getText().length()== 0){
            return  false;
        }if (etFullName.getText().length()== 0){
            return  false;
        }

        return true;
    }
    @OptionsItem(R.id.action_delete)
    void actionDelete(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Record can't be recover!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Factories.createUser().delete(userID);
                        setResult(RESULT_OK);
                        showDeletedMessage();
                        finish();

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


}
