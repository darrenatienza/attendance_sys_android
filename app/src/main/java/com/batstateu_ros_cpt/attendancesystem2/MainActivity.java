package com.batstateu_ros_cpt.attendancesystem2;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.batstateu_ros_cpt.attendancesystem2.Misc.MyPrefs_;
import com.batstateu_ros_cpt.attendancesystem2.activities.attendance.AttendanceSubjectList_;
import com.batstateu_ros_cpt.attendancesystem2.activities.attendance.AttendanceTardinessListActivity_;
import com.batstateu_ros_cpt.attendancesystem2.activities.attendance.PerfectAttendanceActivity_;
import com.batstateu_ros_cpt.attendancesystem2.activities.class_section.ClassSectionListActivity_;
import com.batstateu_ros_cpt.attendancesystem2.activities.login.LoginActivity_;
import com.batstateu_ros_cpt.attendancesystem2.activities.school_year.SchoolYearListActivity_;

import com.batstateu_ros_cpt.attendancesystem2.activities.sms.SmsListActivity2_;
import com.batstateu_ros_cpt.attendancesystem2.activities.student.StudentListActivity_;
import com.batstateu_ros_cpt.attendancesystem2.activities.subject.SubjectListActivity_;
import com.batstateu_ros_cpt.attendancesystem2.activities.user.UserListActivity_;
import com.batstateu_ros_cpt.attendancesystem2.library.Factories;
import com.batstateu_ros_cpt.attendancesystem2.library.models.StudentModel;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

import java.util.List;

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.main2)
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    final static int DEFAULT_SELECTION = 2;

    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;
    @ViewById(R.id.nav_view)
    NavigationView navigationView;

    @ViewById(R.id.content_main)
    LinearLayout content_main;
    @ViewById(R.id.student_count)
    TextView studentCount;
    @ViewById(R.id.class_section_count)
    TextView classSectionCount;

    @ViewById(R.id.subject_count)
    TextView subjectCount;

    @ViewById(R.id.absent_today_count)
    TextView tvAbsentTodayCount;

    @ViewById(R.id.absent_week_count)
    TextView tvAbsentWeekCount;

    @ViewById(R.id.absent_month_count)
    TextView tvAbsentMonthCount;

    @ViewById(R.id.unsent_sms)
    TextView tvUnsentSMS;

    @Pref
    MyPrefs_ myPrefs;


    //@ViewById(R.id.my_recycler_view)
    //RecyclerView recyclerView;
    private List<StudentModel> studentModels;

    private ActionBarDrawerToggle toggle;
    private boolean mToolBarNavigationListenerIsRegistered = false;

    @AfterViews
    void AfterViews(){
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Dashboard");
        int currSchoolYearID = myPrefs.schoolYearID().get();
        int currGradingPeriod = myPrefs.gradingPeriod().get();
        int minAbsentCount  = myPrefs.minAbsentCount().get();
        int minLateCount = myPrefs.minLateCount().get();
        Factories.createAttendance().generateTardinessSMS(currSchoolYearID,currGradingPeriod,minAbsentCount,minLateCount);
        loadDashboardData();
    }
    void loadDashboardData(){
        Integer studCount = Factories.createStudent().getAll().size();
        Integer classSecCount = Factories.createClassSection().getAll().size();
        Integer subjCount = Factories.createSubject().getAll().size();
        Integer absentTodayCount = Factories.createAttendance().getTodayAbsentCount();

        Integer absentWeekCount = Factories.createAttendance().getWeekAbsentCount();
        Integer absentMonthCount = Factories.createAttendance().getMonthAbsentCount();
        Integer unsentSMSCount = Factories.createSms().getSentSMSCount();
        studentCount.setText(studCount.toString());
        classSectionCount.setText(classSecCount.toString());
        subjectCount.setText(subjCount.toString());
        tvAbsentTodayCount.setText(absentTodayCount.toString());
        tvAbsentWeekCount.setText(absentWeekCount.toString());
        tvAbsentMonthCount.setText(absentMonthCount.toString());
        tvUnsentSMS.setText(unsentSMSCount.toString());
    }

    @OnActivityResult(DEFAULT_SELECTION)
    void getResult(int resultCode){
        navigationView.setCheckedItem(R.id.nav_dashboard);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else{
            finish();

            super.onBackPressed();

        }

    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {

        } else if (id == R.id.nav_students) {

            StudentListActivity_.intent(this).startForResult(DEFAULT_SELECTION);

        } else if (id == R.id.nav_class_section) {
            ClassSectionListActivity_.intent(this).startForResult(DEFAULT_SELECTION);

        } else if (id == R.id.nav_subject) {
            SubjectListActivity_.intent(this).startForResult(DEFAULT_SELECTION);
        } else if (id == R.id.nav_attendance) {
            AttendanceSubjectList_.intent(this).startForResult(DEFAULT_SELECTION);
        } else if (id == R.id.nav_sms) {
            SmsListActivity2_.intent(this).startForResult(DEFAULT_SELECTION);
        } else if (id == R.id.nav_schoolYear) {
            SchoolYearListActivity_.intent(this).startForResult(DEFAULT_SELECTION);
        }
        else if (id == R.id.nav_users) {
            UserListActivity_.intent(this).startForResult(DEFAULT_SELECTION);
        }
        else if (id == R.id.nav_perfect_attendance) {
            PerfectAttendanceActivity_.intent(this).startForResult(DEFAULT_SELECTION);
        } else if (id == R.id.nav_attendance_tardiness) {
            AttendanceTardinessListActivity_.intent(this).startForResult(DEFAULT_SELECTION);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @OnActivityResult(DEFAULT_SELECTION)
    void onResult(int resultCode) {
            loadDashboardData();


    }
    @OptionsItem(R.id.action_logout)
    void logOut(){
        LoginActivity_.intent(this).start();
    }

}
