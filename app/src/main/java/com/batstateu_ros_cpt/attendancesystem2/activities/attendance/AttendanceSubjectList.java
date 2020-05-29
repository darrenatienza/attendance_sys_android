package com.batstateu_ros_cpt.attendancesystem2.activities.attendance;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.batstateu_ros_cpt.attendancesystem2.Misc.MyPrefs_;
import com.batstateu_ros_cpt.attendancesystem2.Misc.Utils;
import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.activities.class_section.ClassSectionUpdateActivity_;
import com.batstateu_ros_cpt.attendancesystem2.adapters.AttendanceSubjectAdapter;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.GlobalApplication;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SchoolYearModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceSubjectListModel;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItemAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

@EActivity(R.layout.activity_attendance_subject_list)
public class AttendanceSubjectList extends AppCompatActivity {

    final int LOAD_LIST = 1;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    private SearchView searchView;

    @ViewById(R.id.my_recycler_view)
    RecyclerView recyclerView;
    @ViewById(R.id.spSchoolYear)
    SmartMaterialSpinner spSchoolYear;

    @ViewById(R.id.spGradingPeriod)
    SmartMaterialSpinner spGradingPeriod;
    @ViewById(R.id.etDateFrom)
    EditText etDateFrom;
    @ViewById(R.id.etDateTo)
    EditText etDateTo;

    @ViewById(R.id.btnClose)
    ImageView btnClose;
    @Pref
    MyPrefs_ myPrefs;
    FastItemAdapter<AttendanceSubjectAdapter> fastAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<AttendanceSubjectAdapter> adapterList;
    private int schoolYearID;
    private int gradingPeriod;
    private String date;
    private Date fromDate;
    private Date toDate;
    private ArrayList<String> schoolYearList;
    private ArrayList<String> gradingPeriodList;
    private DatePickerDialog picker;

    @AfterViews
    void AfterView(){
        setSupportActionBar(toolbar);
        etDateFrom.setText(DateTimeUtils.formatDate(new Date()).substring(0,10));
        etDateTo.setText(DateTimeUtils.formatDate(new Date()).substring(0,10));
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        fastAdapter = new FastItemAdapter<>();
        adapterList = new ArrayList<>();
        recyclerView.setAdapter(fastAdapter);
        loadSchoolYearList();
        loadGradingPeriodList();

        fastAdapter.withSelectable(true);
        fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<AttendanceSubjectAdapter>() {
            @Override
            public boolean onClick(View v, IAdapter<AttendanceSubjectAdapter> adapter, AttendanceSubjectAdapter item, int position) {
               update(item.getSubjectID(),item.getClassSectionID(), item.getDate());
                return false;
            }
        });
        ViewCompat.setNestedScrollingEnabled(recyclerView, false);
    }


    private void loadGradingPeriodList() {
        gradingPeriodList = new ArrayList<>();
        gradingPeriodList.add("1");
        gradingPeriodList.add("2");
        gradingPeriodList.add("3");
        gradingPeriodList.add("4");
        spGradingPeriod.setItem(gradingPeriodList);
        spGradingPeriod.setSelection(getGradingPeriodPosition(myPrefs.gradingPeriod().get()));
        spGradingPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                gradingPeriod = getGradingPeriod(position);
                myPrefs.gradingPeriod().put(gradingPeriod);
                loadList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void loadSchoolYearList() {
        schoolYearList = new ArrayList<>();
        List<SchoolYearModel> models = Factories.createSchoolYear().getAll();
        for (SchoolYearModel model : models
                ) {

            schoolYearList.add(model.getName());
        }

        spSchoolYear.setItem(schoolYearList);
        spSchoolYear.setSelection(getSchoolYearPosition(myPrefs.schoolYearID().get()));
        spSchoolYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                schoolYearID = getSchoolYearID(position);
                myPrefs.schoolYearID().put(schoolYearID);
                loadList();
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
    private int getGradingPeriod(int position){

        return Utils.convertToInt(gradingPeriodList.get(position));

    }
    private int getGradingPeriodPosition(int gradingPeriod){
        int position = -1;
        for (int i = 0; i < gradingPeriodList.size(); i++) {
            if (Utils.convertToInt(gradingPeriodList.get(i)) == gradingPeriod) {
                position = i;
                // break;  // uncomment to get the first instance
            }
        }
        return position;
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


    private void update(String subjectID, String classSectionID, String date) {
        AttendanceListActivity_
                .intent(this)
                .classSectionID(Integer.valueOf(classSectionID))
                .subjectID(Integer.valueOf(subjectID))
                .date(DateTimeUtils.formatDate(date))
                .schoolYearID(schoolYearID)
                .gradingPeriod(gradingPeriod)
                .startForResult(LOAD_LIST);
    }

    @Click(R.id.fab)
    void clickFab(){
        AttendanceListActivity_
                .intent(this)
                .classSectionID(0)
                .subjectID(0)
                .date(new Date())
                .schoolYearID(schoolYearID)
                .gradingPeriod(gradingPeriod)
                .startForResult(LOAD_LIST);
    }

    @OnActivityResult(LOAD_LIST)
    void onResult(int resultCode) {
        if(resultCode == RESULT_OK){
            loadList();
        }

    }
    void loadList(){
        fromDate = DateTimeUtils.formatDate(etDateFrom.getText().toString());
        toDate = DateTimeUtils.formatDate(etDateTo.getText().toString());
        adapterList.clear();
        for (AttendanceSubjectListModel model: Factories.createAttendance().getAttendanceSubjects(schoolYearID, gradingPeriod,fromDate,toDate)
                ) {
            AttendanceSubjectAdapter adapter = new AttendanceSubjectAdapter();
            adapter.setAbsentCount(String.valueOf(model.getAbsentCount()));
            adapter.setClassSectionID(String.valueOf(model.getClassSectionID()));
            adapter.setClassSectionName(model.getClassSectionName());
            adapter.setPresentCount(String.valueOf(model.getPresentCount()));
            adapter.setSubjectID(String.valueOf(model.getSubjectID()));
            adapter.setSubjectName(model.getSubjectName());
            adapter.setDate(DateTimeUtils.formatDate(model.getDate()));
            adapter.setLateCount(model.getLateCount());
            adapterList.add(adapter);
        }
        fastAdapter.setNewList(adapterList);


    }
    void filterRecord(CharSequence charSequence){
        fastAdapter.filter(charSequence);
        fastAdapter.withFilterPredicate(new IItemAdapter.Predicate<AttendanceSubjectAdapter>() {
            @Override
            public boolean filter(AttendanceSubjectAdapter item, CharSequence constraint) {
                return true;
            }
        });
    }

    @Click(R.id.etDateFrom)
    void etDateFrom(){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        picker = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etDateFrom.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        loadList();
                    }
                }, year, month, day);
        picker.show();
    }
    @Click(R.id.etDateTo)
    void etDateTo(){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        picker = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        etDateTo.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        loadList();
                    }
                }, year, month, day);
        picker.show();
    }

}
