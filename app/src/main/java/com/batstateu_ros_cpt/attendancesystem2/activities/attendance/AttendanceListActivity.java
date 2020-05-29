package com.batstateu_ros_cpt.attendancesystem2.activities.attendance;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.batstateu_ros_cpt.attendancesystem2.Misc.MyPrefs_;
import com.batstateu_ros_cpt.attendancesystem2.Misc.Utils;
import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.adapters.AttendanceStudentAdapter;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IBase;
import com.batstateu_ros_cpt.attendancesystem2.library.models.ClassSectionModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SubjectModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceStudentListModel;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.helpers.ClickListenerHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import info.hoang8f.widget.FButton;

@EActivity(R.layout.activity_attendance_list)
@OptionsMenu(R.menu.menu_update_basic)
public class AttendanceListActivity extends AppCompatActivity {

    @Extra
    int classSectionID;
    @Extra
    int subjectID;
    @Extra
    Date date;
    @Extra
    int schoolYearID;
    @Extra
    int gradingPeriod;
    @Pref
    MyPrefs_ myPrefs;
    final int LOAD_LIST = 1;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.my_recycler_view)
    RecyclerView recyclerView;

    @ViewById(R.id.spClassSections)
    SmartMaterialSpinner spClassSections;
    @ViewById(R.id.spSubjects)
    SmartMaterialSpinner spSubjects;
    @ViewById(R.id.etDate)
    EditText etDate;
    @ViewById(R.id.btnGenerate)
    FButton btnGenerate;
    private ClickListenerHelper<AttendanceStudentAdapter> mClickListenerHelper;
    FastItemAdapter<AttendanceStudentAdapter> fastAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<AttendanceStudentAdapter> adapterList;
    private ArrayList<String> classSectionNameList;
    private ArrayList<String> subjectNameList;
    private DatePickerDialog picker;
    private ArrayList<Integer> classSectionIDList;
    private ArrayList<Integer> subjectIDList;

    @AfterViews
    void AfterView(){
        try{
            setSupportActionBar(toolbar);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayout.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            //recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
            fastAdapter = new FastItemAdapter<>();
            adapterList = new ArrayList<>();
            mClickListenerHelper = new ClickListenerHelper<>(fastAdapter);
            recyclerView.setAdapter(fastAdapter);
            loadClassSections();
            loadSubjects();
            setData();
            loadList();
            fastAdapter.withSelectable(false);
            fastAdapter.withOnCreateViewHolderListener(new FastAdapter.OnCreateViewHolderListener() {
                @Override
                public RecyclerView.ViewHolder onPreCreateViewHolder(ViewGroup parent, int viewType) {
                    return fastAdapter.getTypeInstance(viewType).getViewHolder(parent);
                }

                @Override
                public RecyclerView.ViewHolder onPostCreateViewHolder(RecyclerView.ViewHolder viewHolder) {
                    if (viewHolder instanceof AttendanceStudentAdapter.ViewHolder) {
                        mClickListenerHelper.listen(viewHolder, ((AttendanceStudentAdapter.ViewHolder) viewHolder).chkIsPresent, new ClickListenerHelper.OnClickListener<AttendanceStudentAdapter>() {
                            @Override
                            public void onClick(View v, int position, AttendanceStudentAdapter item) {
                                updateStudentAttendancePresentStatus(Integer.valueOf(item.getAttendanceID()),!item.isPresent());


                            }


                        });
                        mClickListenerHelper.listen(viewHolder, ((AttendanceStudentAdapter.ViewHolder) viewHolder).chkIsLate, new ClickListenerHelper.OnClickListener<AttendanceStudentAdapter>() {
                            @Override
                            public void onClick(View v, int position, AttendanceStudentAdapter item) {
                                updateStudentAttendanceLateStatus(Integer.valueOf(item.getAttendanceID()),!item.isLate());


                            }


                        });
                    }

                    return viewHolder;
                }
            });

        }catch(Exception ex){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("ERROR")
                    .setContentText(ex.getMessage())
                    .show();

        }


    }

    private void updateStudentAttendancePresentStatus(int attendanceID, boolean isPresent) {
        try{
        Factories.createAttendance().updateAttendancePresentStatus(attendanceID,isPresent);
    }catch(Exception ex){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERROR")
                .setContentText(ex.getMessage())
                .show();

    }
    }
    private void updateStudentAttendanceLateStatus(int attendanceID, boolean isLate) {
    try{
        Factories.createAttendance().updateAttendanceLateStatus(attendanceID,isLate);
    }catch(Exception ex){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
        .setTitleText("ERROR")
        .setContentText(ex.getMessage())
        .show();

        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        finish();
    }

    private void setData() {
        try{
            etDate.setText(DateTimeUtils.formatDate(date).substring(0,10));
            if(subjectID != 0 && classSectionID != 0){
                spSubjects.setSelection(Utils.find(subjectIDList,subjectID));
                spClassSections.setSelection(Utils.find(classSectionIDList,classSectionID));
                disableInputs(true);
                btnGenerate.setText("Regenerate");
            }
        }catch(Exception ex){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("ERROR")
                    .setContentText(ex.getMessage())
                    .show();

        }
    }

    private void disableInputs(boolean isDisable) {
        spSubjects.setEnabled(!isDisable);
        spClassSections.setEnabled(!isDisable);
        etDate.setEnabled(!isDisable);
    }


    /** Menu Click **/
    @OptionsItem(R.id.action_delete)
    void deleteAttendance(){
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Record can't be recover!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        deleteOnBackground();
                        showDeletedMessage();
                        sDialog.dismiss();
                        setResult(RESULT_OK);
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
    @Background
    void deleteOnBackground(){
        Factories.createAttendance().delete(classSectionID,subjectID,date);

    }
    @Click(R.id.fabAllPresent)
    void fabAllPresent(){

        updateAllAttendanceStatus(true);
    }
    @Background
    void updateAllAttendanceStatus(boolean isPresent) {
        try{
        Factories.createAttendance().updateAllAttendanceStatus(classSectionID,subjectID,date,isPresent);
        reInitUI();
        }catch(Exception ex){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("ERROR")
                    .setContentText(ex.getMessage())
                    .show();

        }
    }

    @Click(R.id.fabAllAbsent)
    void fabAllAbsent(){
        updateAllAttendanceStatus(false);
    }

    @OnActivityResult(LOAD_LIST)
    void onResult(int resultCode) {
        if(resultCode == RESULT_OK){

            loadList();



        }

    }
    @Click(R.id.etDate)
    void etDate(){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        picker = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
        picker.show();
    }
    void loadList(){
        try{
        adapterList.clear();
        for (AttendanceStudentListModel model: Factories.createAttendance().getAttendanceStudents(classSectionID,subjectID,date)
                ) {
            AttendanceStudentAdapter adapter = new AttendanceStudentAdapter();
            adapter.setAttendanceID(String.valueOf(model.getAttendanceID()));
            adapter.setPresent(model.isPresent());
            adapter.setLate(model.isLate());
            adapter.setStudentID(String.valueOf(model.getStudentID()));
            adapter.setStudentName(model.getFullName());
            adapterList.add(adapter);
        }
        fastAdapter.setNewList(adapterList);

    }catch(Exception ex){
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("ERROR")
                .setContentText(ex.getMessage())
                .show();

    }
    }
    @Click(R.id.btnGenerate)
    void btnGenerate_Click(){
        btnGenerate.setText("Regenerating...");
        generateAttendanceInBackground();
    }
    @Background(delay = 3000)
    void generateAttendanceInBackground(){
        try{
            date = DateTimeUtils.formatDate(etDate.getText().toString());
            Factories.createAttendance().addNewAttendaceList(classSectionID,subjectID,date,gradingPeriod,schoolYearID);
            reInitUI();
        }catch (Exception ex){
            showException(ex.getMessage());
        }


    }
    @UiThread
    void showException(String message) {
        btnGenerate.setText("Regenerate");
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error")
                .setContentText(message)
                .show();
    }
    @UiThread
    void reInitUI() {
        btnGenerate.setText("Regenerate");
        loadList();
        disableInputs(true);
    }

    private void loadClassSections() {
        classSectionNameList = new ArrayList<>();
        classSectionIDList = new ArrayList<>();
        List<ClassSectionModel> models = Factories.createClassSection().getAll();
        for (ClassSectionModel model : models
                ) {

            classSectionNameList.add(model.getName());

            classSectionIDList.add(model.getID());

        }

        spClassSections.setItem(classSectionNameList);
        spClassSections.setSelection(Utils.find(classSectionIDList,myPrefs.classSectionID().get()));
        spClassSections.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                classSectionID = classSectionIDList.get(position);
                myPrefs.classSectionID().put(classSectionID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void loadSubjects() {
        subjectNameList = new ArrayList<>();
        subjectIDList = new ArrayList<>();
        List<SubjectModel> models = Factories.createSubject().getAll();
        for (SubjectModel model : models
                ) {

            subjectNameList.add(model.getTitle());
            subjectIDList.add(model.getId());
        }

        spSubjects.setItem(subjectNameList);
        spSubjects.setSelection(Utils.find(subjectIDList,myPrefs.subjectID().get()));
        spSubjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                subjectID = subjectIDList.get(position);
                myPrefs.subjectID().put(subjectID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

}
