package com.batstateu_ros_cpt.attendancesystem2.library.logic;

import android.content.Context;

import com.batstateu_ros_cpt.attendancesystem2.library.GlobalApplication;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IAttendance;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.ISchoolYear;
import com.batstateu_ros_cpt.attendancesystem2.library.data.AttendanceData;
import com.batstateu_ros_cpt.attendancesystem2.library.data.ClassSectionData;
import com.batstateu_ros_cpt.attendancesystem2.library.data.SchoolYearData;
import com.batstateu_ros_cpt.attendancesystem2.library.models.AttendanceModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SchoolYearModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceSubjectListModel;

import java.util.Date;
import java.util.List;

public class SchoolYearLogic implements ISchoolYear {

    @Override
    public List<SchoolYearModel> getAll() {
        Context context = GlobalApplication.getAppContext();
        SchoolYearData data = SchoolYearData.getInstance(context,null);
        data.open();
        List<SchoolYearModel> list = data.getAll();
        data.close();
        return  list;
    }

    @Override
    public List<SchoolYearModel> getAll(String criteria) {
        Context context = GlobalApplication.getAppContext();
        SchoolYearData data = SchoolYearData.getInstance(context,null);
        data.open();
        List<SchoolYearModel> list = data.getAll(criteria);
        data.close();
        return  list;
    }

    @Override
    public SchoolYearModel get(Integer id) {
        Context context = GlobalApplication.getAppContext();
        SchoolYearData data = SchoolYearData.getInstance(context,null);
        data.open();
        SchoolYearModel model = data.get(id);
        data.close();
        return  model;
    }


    @Override
    public void add(SchoolYearModel model) {
        Context context = GlobalApplication.getAppContext();
        SchoolYearData data = SchoolYearData.getInstance(context,null);
        data.open();
        data.add(model);
        data.close();
    }

    @Override
    public void edit(Integer id, SchoolYearModel model) {
        Context context = GlobalApplication.getAppContext();
        SchoolYearData data = SchoolYearData.getInstance(context,null);
        data.open();
        data.edit(id,model);
        data.close();
    }

    @Override
    public void delete(Integer id) {
        Context context = GlobalApplication.getAppContext();
        SchoolYearData data = SchoolYearData.getInstance(context,null);
        data.open();
        data.delete(id);
        data.close();
    }
}
