package com.batstateu_ros_cpt.attendancesystem2.library.logic;

import android.content.Context;

import com.batstateu_ros_cpt.attendancesystem2.library.GlobalApplication;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IStudent;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.ISubject;
import com.batstateu_ros_cpt.attendancesystem2.library.data.StudentData;
import com.batstateu_ros_cpt.attendancesystem2.library.data.SubjectData;
import com.batstateu_ros_cpt.attendancesystem2.library.models.StudentModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SubjectModel;

import java.util.List;

public class SubjectLogic implements ISubject {

    @Override
    public List<SubjectModel> getAll() {
        Context context = GlobalApplication.getAppContext();
        SubjectData data = SubjectData.getInstance(context,null);
        data.open();
        List<SubjectModel> list = data.getAll();
        data.close();
        return  list;
    }

    @Override
    public List<SubjectModel> getAll(String criteria) {
        Context context = GlobalApplication.getAppContext();
        SubjectData data = SubjectData.getInstance(context,null);
        data.open();
        List<SubjectModel> list = data.getAll(criteria);
        data.close();
        return  list;
    }

    @Override
    public SubjectModel get(Integer id) {
        Context context = GlobalApplication.getAppContext();
        SubjectData data = SubjectData.getInstance(context,null);
        data.open();
        SubjectModel model = data.get(id);
        data.close();
        return  model;
    }


    @Override
    public void add(SubjectModel model) {
        Context context = GlobalApplication.getAppContext();
        SubjectData data = SubjectData.getInstance(context,null);
        data.open();
        data.add(model);
        data.close();
    }

    @Override
    public void edit(Integer id, SubjectModel model) {
        Context context = GlobalApplication.getAppContext();
        SubjectData data = SubjectData.getInstance(context,null);
        data.open();
        data.edit(id,model);
        data.close();
    }

    @Override
    public void delete(Integer id) {
        Context context = GlobalApplication.getAppContext();
        SubjectData data = SubjectData.getInstance(context,null);
        data.open();
        data.delete(id);
        data.close();
    }



}
