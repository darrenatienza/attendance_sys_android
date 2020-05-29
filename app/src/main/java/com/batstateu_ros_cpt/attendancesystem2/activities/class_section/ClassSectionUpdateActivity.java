package com.batstateu_ros_cpt.attendancesystem2.activities.class_section;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.adapters.SpinnerAdapter;
import com.batstateu_ros_cpt.attendancesystem2.adapters.models.SpinnerModel;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.models.ClassSectionModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SchoolYearModel;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.jaredrummler.materialspinner.MaterialSpinner;



import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;


import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

@EActivity(R.layout.activity_class_section_update)
@OptionsMenu(R.menu.menu_update_basic)
public class ClassSectionUpdateActivity extends AppCompatActivity  {

    @Extra
    int id;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.tvGrade)
    EditText tvGrade;

    @ViewById(R.id.tvName)
    EditText tvName;

    @ViewById(R.id.spSchoolYear)
    SmartMaterialSpinner spSchoolYear;

    private ArrayList<String> schoolYearList;
    private int schoolYearID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @AfterViews
   void AfterView(){

        loadSchoolYearList();
       setData();

   }
    private void setData() {
        if(id != 0){
            ClassSectionModel model = Factories.createClassSection().get(id);
            tvGrade.setText(String.valueOf(model.getGrade()));
            tvName.setText(model.getName());
            spSchoolYear.setSelection(getSchoolYearPosition(model.getSchoolYearID()));
        }
    }

    @Click(R.id.btnSave)
    void save() {
        try {
            if (!validate()) {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Submit")
                        .setContentText("All fields are required!")
                        .show();
                return;
            }
            ClassSectionModel model = new ClassSectionModel();
            model.setName(tvName.getText().toString());
            model.setGrade(Integer.parseInt(tvGrade.getText().toString()));
            model.setSchoolYearID(schoolYearID);
            if (id > 0) {

                Factories.createClassSection().edit(id, model);
            } else {
                Factories.createClassSection().add(model);
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
        } catch (Exception e) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText(e.getMessage())
                    .setContentText("Something went wrong!")
                    .show();
        }

    }

    private boolean validate() {
        if(tvName.getText().length() == 0){
            return  false;
        }
        if (tvGrade.getText().length()== 0){
            return  false;
        }
        if(schoolYearID == 0){
            return false;
        }
        return true;
    }

    @OptionsItem(R.id.action_delete)
    void delete(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Record can't be recover!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Factories.createClassSection().delete(id);
                        showDeletedMessage();
                        setResult(RESULT_OK);


                    }
                })
                .show();
    }
    void showDeletedMessage(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Deleted!")
                .setContentText("Record has been deleted!")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        finish();
                    }
                })
                .show();
    }
    private void loadSchoolYearList() {
        schoolYearList = new ArrayList<>();
        List<SchoolYearModel> models = Factories.createSchoolYear().getAll();
        for (SchoolYearModel model : models
                ) {

            schoolYearList.add(model.getName());
        }

        spSchoolYear.setItem(schoolYearList);
        //spSchoolYear.setSelection(getSchoolYearPosition(myPrefs.schoolYearID().get()));
        spSchoolYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                schoolYearID = getSchoolYearID(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    private int getSchoolYearID(int position){
        List<SchoolYearModel> models = Factories.createSchoolYear().getAll();
        return models.get(position).getId();

    }
    private int getSchoolYearPosition(int id){
        List<SchoolYearModel> models = Factories.createSchoolYear().getAll();
        int position = -1;
        for (int i = 0; i < models.size(); i++) {
            if (models.get(i).getId() == id) {
                position = i;
                // break;  // uncomment to get the first instance
            }
        }
        return position;
    }




}
