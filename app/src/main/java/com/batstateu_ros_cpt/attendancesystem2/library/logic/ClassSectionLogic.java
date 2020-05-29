package com.batstateu_ros_cpt.attendancesystem2.library.logic;

import android.content.Context;

import com.batstateu_ros_cpt.attendancesystem2.library.GlobalApplication;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IClassSection;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IStudent;
import com.batstateu_ros_cpt.attendancesystem2.library.data.ClassSectionData;
import com.batstateu_ros_cpt.attendancesystem2.library.data.StudentData;
import com.batstateu_ros_cpt.attendancesystem2.library.models.ClassSectionModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.StudentModel;

import java.util.List;

public class ClassSectionLogic implements IClassSection {

    @Override
    public List<ClassSectionModel> getAll() {
        Context context = GlobalApplication.getAppContext();
        ClassSectionData data = ClassSectionData.getInstance(context,null);
        data.open();
        List<ClassSectionModel> list = data.getAll();
        data.close();
        return  list;
    }

    @Override
    public List<ClassSectionModel> getAll(String criteria) {
        Context context = GlobalApplication.getAppContext();
        ClassSectionData data = ClassSectionData.getInstance(context,null);
        data.open();
        List<ClassSectionModel> list = data.getAll(criteria);
        data.close();
        return  list;
    }

    @Override
    public ClassSectionModel get(Integer id) {
        Context context = GlobalApplication.getAppContext();
        ClassSectionData data = ClassSectionData.getInstance(context,null);
        data.open();
        ClassSectionModel model = data.get(id);
        data.close();
        return  model;
    }


    @Override
    public void add(ClassSectionModel model) {
        Context context = GlobalApplication.getAppContext();
        ClassSectionData data = ClassSectionData.getInstance(context,null);
        data.open();
        data.add(model);
        data.close();
    }

    @Override
    public void edit(Integer id, ClassSectionModel model) {
        Context context = GlobalApplication.getAppContext();
        ClassSectionData data = ClassSectionData.getInstance(context,null);
        data.open();
        data.edit(id,model);
        data.close();
    }

    @Override
    public void delete(Integer id) {
        Context context = GlobalApplication.getAppContext();
        ClassSectionData data = ClassSectionData.getInstance(context,null);
        data.open();
        data.delete(id);
        data.close();
    }


    @Override
    public int calculateTotalStudent() {
        Context context = GlobalApplication.getAppContext();
        ClassSectionData data = ClassSectionData.getInstance(context,null);
        data.open();
        List<ClassSectionModel> list = data.getItemNeeded();

        data.close();
        return  0;
    }
}
