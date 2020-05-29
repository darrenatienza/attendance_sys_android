package com.batstateu_ros_cpt.attendancesystem2.activities.sms;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.batstateu_ros_cpt.attendancesystem2.Misc.MyPrefs_;
import com.batstateu_ros_cpt.attendancesystem2.Misc.Utils;
import com.batstateu_ros_cpt.attendancesystem2.R;
import com.batstateu_ros_cpt.attendancesystem2.adapters.SmsAdapter;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SchoolYearModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.sms.SmsListModel;
import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.IAdapter;
import com.mikepenz.fastadapter.IItemAdapter;
import com.mikepenz.fastadapter.adapters.FastItemAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.InjectMenu;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.ArrayList;
import java.util.List;
@EActivity(R.layout.activity_sms_list2)
public class SmsListActivity2 extends AppCompatActivity {
    static final int LOAD_LIST = 1;

    @Pref
    MyPrefs_ myPrefs;

    @ViewById(R.id.spSchoolYear)
    SmartMaterialSpinner spSchoolYear;
    @ViewById(R.id.spGradingPeriod)
    SmartMaterialSpinner spGradingPeriod;

    FastItemAdapter<SmsAdapter> fastAdapter;

    @ViewById(R.id.my_recycler_view)
    RecyclerView recyclerView;


    private LinearLayoutManager layoutManager;
    private ArrayList<SmsAdapter> adapters;
    private ArrayList<String> schoolYearList;
    private int schoolYearID;
    private ArrayList<String> gradingPeriodList;
    private int gradingPeriod;

    @AfterViews
    void Afterview(){

        fastAdapter = new FastItemAdapter<>();
        adapters = new ArrayList<>();
        initRecylerView();
        loadSchoolYearList();
        loadGradingPeriodList();
        loadList();
        fastAdapter.withSelectable(true);
        fastAdapter.withOnClickListener(new FastAdapter.OnClickListener<SmsAdapter>() {
            @Override
            public boolean onClick(View v, IAdapter<SmsAdapter> adapter, SmsAdapter item, int position) {
                viewDetails(item.getItemID());
                return false;
            }
        });

    }

    private void viewDetails(int id) {
        SmsDetailActivity_.intent(this).id(id).startForResult(LOAD_LIST);
    }

    private void loadGradingPeriodList() {
        gradingPeriodList = new ArrayList<>();
        gradingPeriodList.add("1");
        gradingPeriodList.add("2");
        gradingPeriodList.add("3");
        gradingPeriodList.add("4");
        spGradingPeriod.setItem(gradingPeriodList);
        //set default value
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
    private void loadList() {
        adapters.clear();

        for (SmsListModel model: Factories.createSms().getSmsList(myPrefs.schoolYearID().get(),myPrefs.gradingPeriod().get())
                ) {
            SmsAdapter adapter = new SmsAdapter();
            adapter.setItemID(model.getId());
            adapter.setDate(model.getDate());
            adapter.setRecipient(model.getRecipient());
            adapter.setSend(model.isSend());
            adapters.add(adapter);
        }
        fastAdapter.setNewList(adapters);

    }
    private void loadSchoolYearList() {
        schoolYearList = new ArrayList<>();
        List<SchoolYearModel> models = Factories.createSchoolYear().getAll();
        for (SchoolYearModel model : models
                ) {

            schoolYearList.add(model.getName());
        }

        spSchoolYear.setItem(schoolYearList);
        //set default
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

    private void initRecylerView() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fastAdapter);
        /** Add line below the list */
        //recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
    }


    @OnActivityResult(LOAD_LIST)
    void onResult(int resultCode) {
        if(resultCode == RESULT_OK){

            loadList();

        }

    }
    @Override
    public void onBackPressed() {
        finish();

    }


}
