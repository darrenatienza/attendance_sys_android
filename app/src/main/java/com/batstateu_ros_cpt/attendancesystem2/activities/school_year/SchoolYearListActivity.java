package com.batstateu_ros_cpt.attendancesystem2.activities.school_year;

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
import android.widget.LinearLayout;

import com.batstateu_ros_cpt.attendancesystem2.Misc.MyPrefs_;
import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.activities.sms.SmsDetailActivity_;
import com.batstateu_ros_cpt.attendancesystem2.activities.student.StudentUpdateActivity_;
import com.batstateu_ros_cpt.attendancesystem2.adapters.SchoolYearAdapter;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SchoolYearModel;
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

@EActivity(R.layout.activity_school_year_list)
@OptionsMenu(R.menu.main)
public class SchoolYearListActivity extends AppCompatActivity {

    static final int LOAD_LIST = 1;
    @ViewById
    Toolbar toolbar;
    @Pref
    MyPrefs_ myPrefs;
    @ViewById
    FloatingActionButton fab;
    private SearchView searchView;
    @ViewById(R.id.spSchoolYear)
    SmartMaterialSpinner spSchoolYear;
    @ViewById(R.id.spGradingPeriod)
    SmartMaterialSpinner spGradingPeriod;

    FastItemAdapter<SchoolYearAdapter> fastAdapter;

    @ViewById(R.id.my_recycler_view)
    RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<SchoolYearAdapter> adapters;
    @AfterViews
    void Afterview(){
        setSupportActionBar(toolbar);
        initViews();
        loadList();
    }

    private void viewDetails(int id) {
        SchoolYearUpdateActivity_.intent(this).schoolYearID(id).startForResult(LOAD_LIST);
    }
    private void loadList() {
        adapters.clear();

        for (SchoolYearModel model: Factories.createSchoolYear().getAll()
                ) {
            SchoolYearAdapter adapter = new SchoolYearAdapter();
            adapter.setSchoolYearID(model.getId());
            adapter.setName(model.getName());
            adapter.setActive(model.isActive());
            adapters.add(adapter);
        }
        fastAdapter.setNewList(adapters);

    }
    private void initViews() {
        fastAdapter = new FastItemAdapter<>();
        adapters = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fastAdapter);
        /** Add line below the list */
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        fastAdapter.withSelectable(true);
        fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<SchoolYearAdapter>() {
            @Override
            public boolean onClick(View v, IAdapter<SchoolYearAdapter> adapter, SchoolYearAdapter item, int position) {
                viewDetails(item.getSchoolYearID());
                return false;
            }
        });
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

        SchoolYearUpdateActivity_.intent(this).schoolYearID(0).startForResult(LOAD_LIST);
    }
    void filterRecord(CharSequence charSequence){
        fastAdapter.filter(charSequence);
        fastAdapter.withFilterPredicate(new IItemAdapter.Predicate<SchoolYearAdapter>() {
            @Override
            public boolean filter(SchoolYearAdapter item, CharSequence constraint) {
                return !item.getName().toLowerCase().contains(String.valueOf(constraint).toLowerCase());
            }
        });
    }
    @InjectMenu
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
                filterRecord(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when query submitted
                filterRecord(query);
                return false;
            }
        });
    }

}
