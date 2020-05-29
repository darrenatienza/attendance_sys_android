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
import com.batstateu_ros_cpt.attendancesystem2.adapters.ClassSectionAdapter;
import com.batstateu_ros_cpt.attendancesystem2.adapters.PerfectAttendanceAdapter;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IBase;
import com.batstateu_ros_cpt.attendancesystem2.library.models.ClassSectionModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SchoolYearModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.PerfectAttendanceModel;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@EActivity(R.layout.activity_perfect_attendance)
public class PerfectAttendanceActivity extends AppCompatActivity {
    private ArrayList<String> schoolYearNameList;

    @ViewById(R.id.spSchoolYear)
    SmartMaterialSpinner spSchoolYear;
    @ViewById(R.id.spGradingPeriod)
    SmartMaterialSpinner spGradingPeriod;
    @ViewById(R.id.spClassSection)
    SmartMaterialSpinner spClassSection;
    @ViewById(R.id.my_recycler_view)
    RecyclerView recyclerView;
    @Pref
    MyPrefs_ myPrefs;
    private int schoolYearID;
    private ArrayList<String> classSectionNameList;
    private ArrayList<Integer> schoolYearIDList;
    private ArrayList<Integer> classSectionIDList;
    private ArrayList<Integer> gradingPeriodList;
    private int classSectionID;
    private Integer gradingPeriod;
    private LinearLayoutManager layoutManager;
    private FastItemAdapter<PerfectAttendanceAdapter> fastAdapter;
    private ArrayList<PerfectAttendanceAdapter> adapterList;

    @AfterViews()
    void afterView(){
        initViews();
        initActions();
        loadSchoolYearList();
        loadClassSections();
        loadGradingPeriodList();

        loadList();
    }

    private void initActions() {
        fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<PerfectAttendanceAdapter>() {
            @Override
            public boolean onClick(View v, IAdapter<PerfectAttendanceAdapter> adapter, PerfectAttendanceAdapter item, int position) {

                return false;
            }
        });
    }

    void initViews(){
        initRecyclerView();
    }

    void initRecyclerView(){
        fastAdapter = new FastItemAdapter<>();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        recyclerView.setAdapter(fastAdapter);
        fastAdapter.withSelectable(true);
    }
    private void loadSchoolYearList() {
        schoolYearNameList = new ArrayList<>();
        schoolYearIDList = new ArrayList<>();
        List<SchoolYearModel> models = Factories.createSchoolYear().getAll();
        int index=0;
        for (SchoolYearModel model : models
                ) {
            index++;
            schoolYearNameList.add(model.getName());
            schoolYearIDList.add(model.getId());
        }

        spSchoolYear.setItem(schoolYearNameList);

        spSchoolYear.setSelection(find(schoolYearIDList,myPrefs.schoolYearID().get()));
        spSchoolYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                schoolYearID = schoolYearIDList.get(position);
                myPrefs.schoolYearID().put(schoolYearID);
                loadList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
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

        spClassSection.setItem(classSectionNameList);
        spClassSection.setSelection(find(classSectionIDList,myPrefs.classSectionID().get()));
        spClassSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                classSectionID = classSectionIDList.get(position);
                myPrefs.classSectionID().put(classSectionID);
                loadList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private void loadGradingPeriodList() {
        gradingPeriodList = new ArrayList<>();
        gradingPeriodList.add(1);
        gradingPeriodList.add(2);
        gradingPeriodList.add(3);
        gradingPeriodList.add(4);
        spGradingPeriod.setItem(gradingPeriodList);
        spGradingPeriod.setSelection(find(gradingPeriodList,myPrefs.gradingPeriod().get()));
        spGradingPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                gradingPeriod = gradingPeriodList.get(position);
                myPrefs.gradingPeriod().put(gradingPeriod);
                loadList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }
    private  void loadList(){

        adapterList = new ArrayList<>();
        for (PerfectAttendanceModel model: Factories.createAttendance().getAllPerfectAttendance(schoolYearID,gradingPeriod,classSectionID)
                ) {
            PerfectAttendanceAdapter adapter = new PerfectAttendanceAdapter();
            adapter.setName(model.getFullName());
            adapterList.add(adapter);
        }
        fastAdapter.setNewList(adapterList);
    }

    // Function to find the index of an element in a primitive array in Java
    public static int find(List<Integer> list , Integer target) {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i) == target)
                return i;

        return -1;
    }
}
