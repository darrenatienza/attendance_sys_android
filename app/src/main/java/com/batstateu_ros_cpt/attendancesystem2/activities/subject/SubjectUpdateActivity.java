package com.batstateu_ros_cpt.attendancesystem2.activities.subject;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.models.StudentModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SubjectModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FocusChange;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import cn.pedant.SweetAlert.SweetAlertDialog;

@EActivity(R.layout.activity_subject_update)
@OptionsMenu(R.menu.menu_update_basic)
public class SubjectUpdateActivity extends AppCompatActivity {
    final static int LOAD_LIST = 2;
    @Extra
    int subjectID;

    @ViewById(R.id.subjectTitle)
    TextInputEditText subjectTitle;

    @ViewById(R.id.grade)
    TextInputEditText grade;

    @AfterViews
    void afterView(){
        setData();
    }

    private void setData() {
        if(subjectID != 0){
            SubjectModel model = Factories.createSubject().get(subjectID);

            subjectTitle.setText(model.getTitle());
            grade.setText(String.valueOf(model.getGrade()));
        }
    }


    @Click(R.id.save)
    void save(){
        try{
            if (!validate()) {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Submit")
                        .setContentText("All fields are required!")
                        .show();
                return;
            }
            if(subjectID > 0){
                SubjectModel model = new SubjectModel();

                model.setGrade(Integer.parseInt(grade.getText().toString()));
                model.setTitle(subjectTitle.getText().toString());
                Factories.createSubject().edit(subjectID,model);
            }else{
                SubjectModel model = new SubjectModel();

                model.setGrade(Integer.parseInt(grade.getText().toString()));
                model.setTitle(subjectTitle.getText().toString());
                Factories.createSubject().add(model);
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
    @InjectMenu
    void setMenu(Menu mMenu){
        MenuItem actionDelete = mMenu.findItem(R.id.action_delete);
        if(subjectID > 0){
            actionDelete.setVisible(true);
        }else{
            actionDelete.setVisible(false);
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
                       Factories.createSubject().delete(subjectID);
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
    private boolean validate() {
        if(subjectTitle.getText().length() == 0){
            return  false;
        }
        if (grade.getText().length()== 0){
            return  false;
        }
        return true;
    }
}
