package com.batstateu_ros_cpt.attendancesystem2.activities.subject;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.adapters.SubjectEnrolleList;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.models.StudentModel;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IItemAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;
import com.mikepenz.fastadapter.helpers.ClickListenerHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_subject_enrolles)
@OptionsMenu(R.menu.menu_subject_enrolle)
public class SubjectEnrollesActivity extends AppCompatActivity {

    static final int LOAD_LIST = 1;

    @Extra
    int subjectID;
    @ViewById
    Toolbar toolbar;

    private SearchView searchView;

    FastItemAdapter<SubjectEnrolleList> fastAdapter;

    @ViewById(R.id.my_recycler_view)
    RecyclerView recyclerView;
    @OptionsMenuItem(R.id.action_show_all)
    MenuItem actionShowAll;

    private LinearLayoutManager layoutManager;
    private ArrayList<SubjectEnrolleList> list2Adapters;
    private ClickListenerHelper<SubjectEnrolleList> mClickListenerHelper;
    private boolean isVisibleAllStudents = false;
    @AfterViews
    void Afterview(){
        setSupportActionBar(toolbar);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        fastAdapter = new FastItemAdapter<>();
        mClickListenerHelper = new ClickListenerHelper<>(fastAdapter);
        recyclerView.setAdapter(fastAdapter);
        list2Adapters = new ArrayList<>();
        loadInitialSubjectEnrolleList();
        fastAdapter.add(list2Adapters);
        fastAdapter.withSelectable(false);

        fastAdapter.withOnCreateViewHolderListener(new FastAdapter.OnCreateViewHolderListener() {
            @Override
            public RecyclerView.ViewHolder onPreCreateViewHolder(ViewGroup parent, int viewType) {
                return fastAdapter.getTypeInstance(viewType).getViewHolder(parent);
            }

            @Override
            public RecyclerView.ViewHolder onPostCreateViewHolder(RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof SubjectEnrolleList.ViewHolder) {
                    mClickListenerHelper.listen(viewHolder, ((SubjectEnrolleList.ViewHolder) viewHolder).chkEnroll, new ClickListenerHelper.OnClickListener<SubjectEnrolleList>() {
                        @Override
                        public void onClick(View v, int position, SubjectEnrolleList item) {
                            //removeFromSubject(item.getItemID());
                            if(item.isEnrolled()){
                                //Factories.createSubject().deleteSubjectEnrolle(subjectID, Integer.parseInt(item.getItemID()));
                            }else{
                                //Factories.createSubject().addSubjectEnrolle(subjectID, Integer.parseInt(item.getItemID()));
                            }
                            /** Show only records that included on selected subject */
                            hideNotSubjectEnrolleList();
                            actionShowAll.setIcon(R.drawable.ic_visibility_black_24dp);
                            isVisibleAllStudents =false;

                            if(!searchView.isIconified()){
                                searchView.setIconified(true);
                            }
                        }


                    });
                }

                return viewHolder;
            }
        });
        /**fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<SubjectEnrolleList>() {
            @Override
            public boolean onClick(View v, IAdapter<SubjectEnrolleList> adapter, SubjectEnrolleList item, int position) {
                update(item.getItemID());
                return false;
            }
        });*/
    }

    /** On Click Event Show All Menu */
    @OptionsItem(R.id.action_show_all)
    void actionShowAll(){
        if(!isVisibleAllStudents){
            isVisibleAllStudents = true;
            actionShowAll.setIcon(R.drawable.ic_visibility_off_black_24dp);
            showAllStudents();
        }else{
            isVisibleAllStudents = false;
            actionShowAll.setIcon(R.drawable.ic_visibility_black_24dp);
            hideNotSubjectEnrolleList();
        }
    }
    /*void initSearchMenu(){

    }*/

    @InjectMenu
    void setMenu(Menu myMenu){
        // Associate searchable configuration with the SearchView
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
    /** Load students which found on subject enrolles*/
    private void loadInitialSubjectEnrolleList(){
        list2Adapters.clear();
        for (StudentModel model: Factories.createStudent().getAllBySubjectID(subjectID)
                ) {
            SubjectEnrolleList list = new SubjectEnrolleList();
            list.setItemID(String.valueOf(model.getId()));
            list.setFullName(model.getFullName());
            list.setEnrolled(true);
            list2Adapters.add(list);
        }
    }
    /** Load students which found on subject enrolles*/
    private void hideNotSubjectEnrolleList(){
        list2Adapters.clear();
        for (StudentModel model: Factories.createStudent().getAllBySubjectID(subjectID)
                ) {
            SubjectEnrolleList list = new SubjectEnrolleList();
            list.setItemID(String.valueOf(model.getId()));
            list.setFullName(model.getFullName());
            list.setEnrolled(true);
            list2Adapters.add(list);
        }
        fastAdapter.setNewList(list2Adapters);
        fastAdapter.notifyDataSetChanged();
    }
    /** Load students which found and not found on subject enrolles*/
    private void showAllStudents(){
        list2Adapters.clear();
        for (StudentModel model: Factories.createStudent().getAll()
                ) {
            boolean isEnrolled = true;
            SubjectEnrolleList list = new SubjectEnrolleList();
            list.setItemID(String.valueOf(model.getId()));
            list.setFullName(model.getFullName());
            list.setEnrolled(isEnrolled);
            list2Adapters.add(list);
        }
        fastAdapter.setNewList(list2Adapters);
        fastAdapter.notifyDataSetChanged();
    }
    private void update(String itemID) {
        SubjectUpdateActivity_.intent(this).subjectID(Integer.parseInt(itemID)).startForResult(LOAD_LIST);
    }
    @OnActivityResult(LOAD_LIST)
    void onResult(int resultCode) {
        if(resultCode == RESULT_OK){

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

    void filterRecord(CharSequence charSequence){
        fastAdapter.filter(charSequence);
        fastAdapter.withFilterPredicate(new IItemAdapter.Predicate<SubjectEnrolleList>() {
            @Override
            public boolean filter(SubjectEnrolleList item, CharSequence constraint) {
                return !item.getFullName().toLowerCase().contains(String.valueOf(constraint).toLowerCase());
            }
        });
    }

    /**@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        switch (item.getItemId()) {

            case R.id.action_show_all:



                return true;
            case R.id.action_subject_enrolles:
                SubjectEnrollesActivity_.intent(this).subjectID(this.subjectID).start();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }*/

}
