package com.batstateu_ros_cpt.attendancesystem2.activities.student;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.batstateu_ros_cpt.attendancesystem2.Misc.TextValidator;
import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.adapters.models.SpinnerModel;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.models.ClassSectionModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.StudentModel;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

@EActivity(R.layout.activity_student_update)
@OptionsMenu(R.menu.menu_update_basic)
public class StudentUpdateActivity extends AppCompatActivity {


    @Extra
    Integer studentID;
    @ViewById(R.id.studentCode)
    EditText studentCode;
    @ViewById(R.id.fullName)
    EditText fullName;
    @ViewById(R.id.address)
    EditText address;
    @ViewById(R.id.contactPerson)
    EditText contactPerson;
    @ViewById(R.id.contactPersonNumber)
    EditText contactPersonNumber;

    private int classSectionID = 0;
    private ArrayList<SpinnerModel> spinnerModels;
    @ViewById(R.id.class_section)
    SmartMaterialSpinner spClassSection;
    @OptionsMenuItem(R.id.action_delete)
    MenuItem actionDelete;

    private SmartMaterialSpinner spEmptyItem;
    private List<String> classSections;
    private int classectionID;

    @AfterViews
    void afterView(){

        getSupportActionBar().setTitle("Student Details");
        loadClassSections();
        setData();



    }

    @InjectMenu
    void setMenu(Menu mMenu){
        MenuItem actionMenu = mMenu.findItem(R.id.action_delete);
        if(studentID > 0)
            actionMenu.setVisible(true);
        else actionMenu.setVisible(false);
    }
    private void setData() {
        if(studentID != 0){
            StudentModel model = Factories.createStudent().get(studentID);
            studentCode.setText(model.getStudent_code());
            fullName.setText(model.getFullName());
            address.setText(model.getAddress());
            contactPerson.setText(model.getContact_person());
            contactPersonNumber.setText(model.getContact_person_number());
            spClassSection.setSelection(getClassSectionPosition(model.getClass_section_id()));



        }
    }
    private boolean validate() {
        if(studentCode.getText().length() == 0){
            return  false;
        }
        if (fullName.getText().length()== 0){
            return  false;
        }
        if (address.getText().length()== 0){
            return  false;
        }
        if (contactPerson.getText().length()== 0){
            return  false;
        }if (contactPersonNumber.getText().length()== 0){
            return  false;
        }
        if(classSectionID == 0){
            return false;
        }
        return true;
    }
    private int getSpinnerModelIndex(Integer class_section_id) {
        int id = 3;
        int position = -1;
        for (int i = 0; i < spinnerModels.size(); i++) {
            if (spinnerModels.get(i).getId() == String.valueOf(class_section_id)) {
                position = i;
                 break;  // uncomment to get the first instance
            }
        }
        return position;
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
            if(studentID > 0){
                StudentModel model = new StudentModel();
                model.setStudent_code(studentCode.getText().toString());
                model.setFullName(fullName.getText().toString());
                model.setAddress(address.getText().toString());
                model.setContact_person(contactPerson.getText().toString());
                model.setContact_person_number(contactPersonNumber.getText().toString());
                model.setClass_section_id(classSectionID);
                Factories.createStudent().edit(studentID,model);
            }else{
                StudentModel model = new StudentModel();
                model.setStudent_code(studentCode.getText().toString());
                model.setFullName(fullName.getText().toString());
                model.setAddress(address.getText().toString());
                model.setContact_person(contactPerson.getText().toString());
                model.setContact_person_number(contactPersonNumber.getText().toString());
                model.setClass_section_id(classSectionID);
                Factories.createStudent().add(model);
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
                    .setTitleText("Error")
                    .setContentText(e.getMessage())
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
                        Factories.createStudent().delete(studentID);
                        sDialog.dismiss();
                        showDeleteMessage();
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .show();
    }
    void showDeleteMessage(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Deleted!")
                .setContentText("Record has been deleted!")
                .setConfirmText("OK")

                .show();
    }
    void loadClassSections(){
        classSections = new ArrayList<>();
        List<ClassSectionModel> classSectionModels = Factories.createClassSection().getAll();
        for (ClassSectionModel classSectionModel : classSectionModels
                ) {

            classSections.add("Grade " + classSectionModel.getGrade() + " - "+ classSectionModel.getName());
        }

        spClassSection.setItem(classSections);
        spClassSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
               classSectionID = getClassSectionID(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    private int getClassSectionID(int position){
        List<ClassSectionModel> classSectionModels = Factories.createClassSection().getAll();
        return classSectionModels.get(position).getID();

    }
    private int getClassSectionPosition(int id){
        List<ClassSectionModel> classSectionModels = Factories.createClassSection().getAll();
        int position = -1;
        for (int i = 0; i < classSectionModels.size(); i++) {
            if (classSectionModels.get(i).getID() == id) {
                position = i;
                // break;  // uncomment to get the first instance
            }
        }
        return position;
    }
}
