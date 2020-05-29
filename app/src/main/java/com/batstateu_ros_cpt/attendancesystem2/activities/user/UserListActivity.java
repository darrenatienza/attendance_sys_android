package com.batstateu_ros_cpt.attendancesystem2.activities.user;

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
import android.widget.Adapter;
import android.widget.LinearLayout;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.activities.class_section.ClassSectionUpdateActivity_;
import com.batstateu_ros_cpt.attendancesystem2.adapters.ClassSectionAdapter;
import com.batstateu_ros_cpt.attendancesystem2.adapters.UserAdapter;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.models.ClassSectionModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.user.UserModel;
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
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Date;

@EActivity(R.layout.activity_user_list)
@OptionsMenu(R.menu.main)
public class UserListActivity extends AppCompatActivity {

    final int LOAD_LIST = 1;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    private SearchView searchView;

    @ViewById(R.id.my_recycler_view)
    RecyclerView recyclerView;

    FastItemAdapter<UserAdapter> fastAdapter;
    private LinearLayoutManager layoutManager;
    private ArrayList<UserAdapter> adapterList;

    @AfterViews
    void AfterView(){

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

        fastAdapter.withSelectable(true);
        fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<UserAdapter>() {
            @Override
            public boolean onClick(View v, IAdapter<UserAdapter> adapter, UserAdapter item, int position) {
                if(!searchView.isIconified()){
                    searchView.setIconified(true);
                }
                update(item.getUserID());
                return false;
            }
        });

    }

    private void update(int userID) {
        UserUpdateActivity_.intent(this).userID(userID).startForResult(LOAD_LIST);
    }

    @Click(R.id.fab)
    void clickFab(){
        UserUpdateActivity_.intent(this).startForResult(LOAD_LIST);
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



        }

    }
    void loadList(){

        adapterList.clear();
        for (UserModel model: Factories.createUser().getAll()
                ) {
            UserAdapter adapter = new UserAdapter();
            adapter.setUserID(model.getId());
            adapter.setFullName(model.getFullName());
            adapter.setUserName(model.getUsername());
            adapterList.add(adapter);
        }
        fastAdapter.setNewList(adapterList);


    }
    void filterRecord(CharSequence charSequence){
        fastAdapter.filter(charSequence);
        fastAdapter.withFilterPredicate(new IItemAdapter.Predicate<UserAdapter>() {
            @Override
            public boolean filter(UserAdapter item, CharSequence constraint) {
                return !item.getFullName().toLowerCase().contains(String.valueOf(constraint).toLowerCase());
            }
        });
    }


}
