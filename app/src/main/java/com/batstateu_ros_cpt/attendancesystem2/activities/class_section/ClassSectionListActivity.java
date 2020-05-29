package com.batstateu_ros_cpt.attendancesystem2.activities.class_section;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

import com.batstateu_ros_cpt.attendancesystem2.R;

import com.batstateu_ros_cpt.attendancesystem2.adapters.ClassSectionAdapter;

import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.models.ClassSectionModel;

import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItemAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EActivity(R.layout.activity_class_section_list)
@OptionsMenu(R.menu.main)
public class ClassSectionListActivity extends AppCompatActivity {

    final int LOAD_LIST = 1;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    private SearchView searchView;

    @ViewById(R.id.my_recycler_view)
    RecyclerView recyclerView;

    FastItemAdapter<ClassSectionAdapter> fastAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<ClassSectionAdapter> adapterList;

    @AfterViews
    void AfterView(){
        String date = DateTimeUtils.formatDate(new Date());
        setSupportActionBar(toolbar);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        fastAdapter = new FastItemAdapter<>();
        adapterList = new ArrayList<>();
        recyclerView.setAdapter(fastAdapter);
        loadList();
        fastAdapter.add(adapterList);
        fastAdapter.withSelectable(true);
        fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<ClassSectionAdapter>() {
            @Override
            public boolean onClick(View v, IAdapter<ClassSectionAdapter> adapter, ClassSectionAdapter item, int position) {
                if(!searchView.isIconified()){
                    searchView.setIconified(true);
                }
                update(item.getItemID());
                return false;
            }
        });

    }

    private void update(String itemID) {
        ClassSectionUpdateActivity_.intent(this).id(Integer.parseInt(itemID)).startForResult(LOAD_LIST);
    }

    @Click(R.id.fab)
    void clickFab(){
        ClassSectionUpdateActivity_.intent(this).startForResult(LOAD_LIST);
    }
    @InjectMenu
    void setMenu(Menu myMenu){
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView)myMenu.findItem(R.id.action_search)
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
    @OnActivityResult(LOAD_LIST)
    void onResult(int resultCode) {
        if(resultCode == RESULT_OK){

            loadList();
            /** Set new list to the record */
            fastAdapter.setNewList(adapterList);
            fastAdapter.notifyDataSetChanged();
            /** */


        }

    }
    void loadList(){

        adapterList.clear();
        for (ClassSectionModel model: Factories.createClassSection().getAll()
                ) {
            ClassSectionAdapter adapter = new ClassSectionAdapter();
            adapter.setItemID(String.valueOf(model.getID()));
            adapter.setGrade("Grade: " + String.valueOf(model.getGrade()));
            adapter.setName(model.getName());
            adapter.setSchoolYear("School Year: " + model.getSchoolYear());
            adapterList.add(adapter);
        }


    }
    void filterRecord(CharSequence charSequence){
        fastAdapter.filter(charSequence);
        fastAdapter.withFilterPredicate(new IItemAdapter.Predicate<ClassSectionAdapter>() {
            @Override
            public boolean filter(ClassSectionAdapter item, CharSequence constraint) {
                return !item.getName().toLowerCase().contains(String.valueOf(constraint).toLowerCase());
            }
        });
    }

}
