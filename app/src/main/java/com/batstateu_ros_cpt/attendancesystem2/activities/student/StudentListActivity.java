package com.batstateu_ros_cpt.attendancesystem2.activities.student;

import android.app.SearchManager;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.batstateu_ros_cpt.attendancesystem2.Misc.MyPrefs_;
import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.adapters.StudentAdapter;

import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.models.ClassSectionModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SchoolYearModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.StudentModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SubjectModel;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItemAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_studentlist)
//@OptionsMenu(R.menu.main)
public class StudentListActivity extends AppCompatActivity {

    static final int LOAD_LIST = 1;
    @ViewById
    Toolbar toolbar;
    @ViewById
    FloatingActionButton fab;
    private SearchView searchView;

    FastItemAdapter<StudentAdapter> fastAdapter;

    @ViewById(R.id.my_recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private ArrayList<StudentAdapter> adapters;

    private ArrayList<String> schoolYearList;
    @ViewById(R.id.school_year)
    SmartMaterialSpinner spSchoolYear;
    @ViewById(R.id.class_section)
    SmartMaterialSpinner spClassSections;
    private int schoolYearID;

    @Pref
    MyPrefs_ myPrefs;
    private ArrayList<String> classSectionList;
    private int classSectionID;
    private String _selClassSection = "";
    private String _selSchoolYear = "";

    @AfterViews
    void Afterview(){
        setSupportActionBar(toolbar);
        fastAdapter = new FastItemAdapter<>();
        adapters = new ArrayList<>();
        initRecylerView();
        loadSchoolYearList();
        loadClassSections();
        fastAdapter.add(adapters);
        fastAdapter.withSelectable(true);
        fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<StudentAdapter>() {
            @Override
            public boolean onClick(View v, IAdapter<StudentAdapter> adapter, StudentAdapter item, int position) {
                update(item.getItemID());
                return false;
            }
        });
        recyclerView.setNestedScrollingEnabled(false);

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
                _selSchoolYear = getSchoolYearName(position);
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
    private String getSchoolYearName(int position){
        List<SchoolYearModel> models = Factories.createSchoolYear().getAll();
        return models.get(position).getName();

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
    private void loadClassSections() {
        classSectionList = new ArrayList<>();
        List<ClassSectionModel> models = Factories.createClassSection().getAll();
        for (ClassSectionModel model : models
                ) {

            classSectionList.add(model.getName());
        }

        spClassSections.setItem(classSectionList);
        spClassSections.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //classSectionID = getClassSectionID(position);
                _selClassSection = getClassSectionName(position);
                loadList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    private String getClassSectionName(int position) {
        List<ClassSectionModel> models = Factories.createClassSection().getAll();
        return models.get(position).getName();
    }

    private void loadList() {
        adapters.clear();
        for (StudentModel model: Factories.createStudent().getAll(_selClassSection,_selSchoolYear)
                ) {
            StudentAdapter studentAdapter = new StudentAdapter();
            studentAdapter.setItemID(model.getId());
            studentAdapter.setFullName(model.getFullName());
            studentAdapter.setSchoolDetail(model.getAddress());
            adapters.add(studentAdapter);

        }
        fastAdapter.setNewList(adapters);

    }

    private void initRecylerView() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fastAdapter);
        /** Add line below the list */
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
    }

    private void update(Integer itemID) {
        /**if(!searchView.isIconified()){
            searchView.setIconified(true);
        }*/
        StudentUpdateActivity_.intent(this).studentID(itemID).startForResult(LOAD_LIST);
    }
    @OnActivityResult(LOAD_LIST)
    void onResult(int resultCode) {
        if(resultCode == RESULT_OK){

            loadList();
            /** Set new list to the record */
            fastAdapter.setNewList(adapters);
            fastAdapter.notifyDataSetChanged();
            /** */


        }

    }
    @Override
    public void onBackPressed() {
        finish();

    }
    @Click
    void fab(){

        StudentUpdateActivity_.intent(this).studentID(0).startForResult(LOAD_LIST);
    }
    void filterRecord(CharSequence charSequence){
        fastAdapter.filter(charSequence);
        fastAdapter.withFilterPredicate(new IItemAdapter.Predicate<StudentAdapter>() {
            @Override
            public boolean filter(StudentAdapter item, CharSequence constraint) {
                return !item.getFullName().toLowerCase().contains(String.valueOf(constraint).toLowerCase());
            }
        });
    }
    /*@InjectMenu
    void setMenu(Menu mMenu){
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) mMenu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showBackButton(true);
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                //showBackButton(false);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                //filterRecord(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when query submitted
                //filterRecord(query);
                return false;
            }
        });
    }*/

}
