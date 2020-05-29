package com.batstateu_ros_cpt.attendancesystem2.activities.subject;

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

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.adapters.SubjectAdapter;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SubjectModel;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItemAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_subject_list)
public class SubjectListActivity extends AppCompatActivity {

    static final int LOAD_LIST = 1;
    @ViewById
    Toolbar toolbar;
    @ViewById
    FloatingActionButton fab;
    private SearchView searchView;

    FastItemAdapter<SubjectAdapter> fastAdapter;

    @ViewById(R.id.my_recycler_view)
    RecyclerView recyclerView;

    private LinearLayoutManager layoutManager;
    private ArrayList<SubjectAdapter> list2Adapters;

    @AfterViews
    void Afterview(){
        setSupportActionBar(toolbar);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        fastAdapter = new FastItemAdapter<>();

        recyclerView.setAdapter(fastAdapter);
        list2Adapters = new ArrayList<>();
        loadList();
        fastAdapter.add(list2Adapters);
        fastAdapter.withSelectable(true);
        fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<SubjectAdapter>() {
            @Override
            public boolean onClick(View v, IAdapter<SubjectAdapter> adapter, SubjectAdapter item, int position) {
                update(item.getItemID());
                return false;
            }
        });
    }
    private void loadList(){
        list2Adapters.clear();
        for (SubjectModel model: Factories.createSubject().getAll()
                ) {
            SubjectAdapter list = new SubjectAdapter();
            list.setItemID(String.valueOf(model.getId()));
            list.setGrade(String.valueOf(model.getGrade()));
            list.setTitle(model.getTitle());
            list2Adapters.add(list);
        }
    }
    private void update(String itemID) {
        SubjectUpdateActivity_.intent(this).subjectID(Integer.parseInt(itemID)).startForResult(LOAD_LIST);
    }
    @OnActivityResult(LOAD_LIST)
    void onResult(int resultCode) {
        if(resultCode == RESULT_OK){
           loadList();
            /** Set new list to the record */
            fastAdapter.setNewList(list2Adapters);
            fastAdapter.notifyDataSetChanged();
            /** */

            if(!searchView.isIconified()){
                searchView.setIconified(true);
            }
        }

    }
    @Override
    public void onBackPressed() {
        finish();

    }
    @Click
    void fab(){

        SubjectUpdateActivity_.intent(this).subjectID(0).startForResult(LOAD_LIST);
    }
    void filterRecord(CharSequence charSequence){
        fastAdapter.filter(charSequence);
        fastAdapter.withFilterPredicate(new IItemAdapter.Predicate<SubjectAdapter>() {
            @Override
            public boolean filter(SubjectAdapter item, CharSequence constraint) {
                return !item.getTitle().toLowerCase().contains(String.valueOf(constraint).toLowerCase());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
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
        return super.onCreateOptionsMenu(menu);
    }
}
