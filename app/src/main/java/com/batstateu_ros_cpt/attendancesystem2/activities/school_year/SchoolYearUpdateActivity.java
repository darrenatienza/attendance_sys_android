package com.batstateu_ros_cpt.attendancesystem2.activities.school_year;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SchoolYearModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.user.UserModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import cn.pedant.SweetAlert.SweetAlertDialog;

@EActivity(R.layout.activity_school_year_update)
@OptionsMenu(R.menu.menu_update_basic)
public class SchoolYearUpdateActivity extends AppCompatActivity {

    @Extra
    int schoolYearID;
    @ViewById
    EditText etSchoolYearName;

    @AfterViews
    void afterViews(){
        setData();
    }

    private void setData() {
        if(schoolYearID != 0){
            SchoolYearModel model = Factories.createSchoolYear().get(schoolYearID);
            etSchoolYearName.setText(model.getName());

        }
    }


    @Click(R.id.btnSave)
    void save(){
        try{
            if(schoolYearID > 0){
                SchoolYearModel model = new SchoolYearModel();
                model.setName(etSchoolYearName.getText().toString());
                model.setActive(false);
                Factories.createSchoolYear().edit(schoolYearID,model);
            }else{
                SchoolYearModel model = new SchoolYearModel();
                model.setName(etSchoolYearName.getText().toString());
                model.setActive(false);
                Factories.createSchoolYear().add(model);
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
    @OptionsItem(R.id.action_delete)
    void actionDelete(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Record can't be recover!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        Factories.createSchoolYear().delete(schoolYearID);
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
}
