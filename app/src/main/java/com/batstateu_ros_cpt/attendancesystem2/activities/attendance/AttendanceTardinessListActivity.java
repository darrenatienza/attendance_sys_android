package com.batstateu_ros_cpt.attendancesystem2.activities.attendance;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.batstateu_ros_cpt.attendancesystem2.Misc.MyPrefs_;
import com.batstateu_ros_cpt.attendancesystem2.Misc.Utils;
import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.adapters.AttendanceSubjectAdapter;
import com.batstateu_ros_cpt.attendancesystem2.adapters.AttendanceTardinessAdapter;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SchoolYearModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceTardinessModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceTodayModel;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.helpers.ClickListenerHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EActivity(R.layout.activity_attendance_tardiness_list)
public class AttendanceTardinessListActivity extends AppCompatActivity {
    @Pref
    MyPrefs_ myPrefs;
    @ViewById
    SmartMaterialSpinner spSchoolYear;
    @ViewById
    SmartMaterialSpinner spGradingPeriod;
    @ViewById(R.id.my_recycler_view)
    RecyclerView recyclerView;
    private ArrayList<Integer> gradingPeriodList;
    private Integer gradingPeriod;
    private ArrayList<String> schoolYearNameList;
    private ArrayList<Integer> schoolYearIDList;
    private Integer schoolYearID;
    private ArrayList<AttendanceTardinessAdapter> adapterList;
    FastItemAdapter<AttendanceTardinessAdapter> fastAdapter;
    private LinearLayoutManager layoutManager;
    private ClickListenerHelper<AttendanceTardinessAdapter> mClickListenerHelper;
    @AfterViews
    void afterViews(){
        initViews();
        initActions();
        loadSchoolYearList();
        loadGradingPeriodList();
    }

    private void initActions() {

        fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<AttendanceTardinessAdapter>() {
            @Override
            public boolean onClick(View v, IAdapter<AttendanceTardinessAdapter> adapter, AttendanceTardinessAdapter item, int position) {
                showDetail(item.getStudentID(),schoolYearID,gradingPeriod);
                return false;
            }
        });
    }

    private void showDetail(int studentID, int schoolYearID, int date) {
        AttendanceTardinessActivity_.intent(this).studentID(studentID).schoolYearID(schoolYearID).gradingPeriod(gradingPeriod).start();
    }

    private void initViews() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        fastAdapter = new FastItemAdapter<>();
        adapterList = new ArrayList<>();
        mClickListenerHelper = new ClickListenerHelper<>(fastAdapter);
        recyclerView.setAdapter(fastAdapter);
        fastAdapter.withSelectable(true);

    }

    private void loadSchoolYearList() {
        schoolYearNameList = new ArrayList<>();
        schoolYearIDList = new ArrayList<>();
        List<SchoolYearModel> models = Factories.createSchoolYear().getAll();
        for (SchoolYearModel model : models
                ) {

            schoolYearNameList.add(model.getName());
            schoolYearIDList.add(model.getId());
        }

        spSchoolYear.setItem(schoolYearNameList);
        spSchoolYear.setSelection(Utils.find(schoolYearIDList,myPrefs.schoolYearID().get()));
        spSchoolYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                schoolYearID = schoolYearIDList.get(position);
                loadList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    void loadList(){

        adapterList.clear();
        for (AttendanceTardinessModel model: Factories.createAttendance().getAllAttendanceTardiness(schoolYearID, gradingPeriod)
                ) {
            AttendanceTardinessAdapter adapter = new AttendanceTardinessAdapter();
            adapter.setAbsentCount(model.getAbsentCount());
            adapter.setLateCount(model.getLateCount());
            adapter.setSectionName(model.getClassSectionName());
            adapter.setStudentID(model.getStudentID());
            adapter.setStudentName(model.getStudentName());
            AttendanceTodayModel attendanceTodayModel = Factories.createAttendance().getAttendanceToday(model.getStudentID(),new Date());
            adapter.setPresentTodayCount(attendanceTodayModel.getPresentCount());
            adapter.setLateTodayCount(attendanceTodayModel.getLateCount());
            adapter.setLateCount(model.getLateCount());
            Integer todaySubjectCount = Factories.createAttendance().getTotalSubjectToday(model.getClassSectionID(),new Date());
            adapter.setSubjectCount(todaySubjectCount);
            adapterList.add(adapter);
        }
        fastAdapter.setNewList(adapterList);


    }

    private void loadGradingPeriodList() {
        gradingPeriodList = new ArrayList<>();
        gradingPeriodList.add(1);
        gradingPeriodList.add(2);
        gradingPeriodList.add(3);
        gradingPeriodList.add(4);
        spGradingPeriod.setItem(gradingPeriodList);
        spGradingPeriod.setSelection(Utils.find(gradingPeriodList,myPrefs.gradingPeriod().get()));
        spGradingPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                gradingPeriod = gradingPeriodList.get(position);
                loadList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

}
