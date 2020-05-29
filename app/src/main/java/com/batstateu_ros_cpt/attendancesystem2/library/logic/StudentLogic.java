package com.batstateu_ros_cpt.attendancesystem2.library.logic;

import android.app.ApplicationErrorReport;
import android.content.Context;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;

import com.batstateu_ros_cpt.attendancesystem2.MainActivity;
import com.batstateu_ros_cpt.attendancesystem2.library.GlobalApplication;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IBase;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IStudent;
import com.batstateu_ros_cpt.attendancesystem2.library.data.StudentData;
import com.batstateu_ros_cpt.attendancesystem2.library.models.StudentModel;

import java.util.List;

public class StudentLogic implements IBase<StudentModel>,IStudent {

    @Override
    public List<StudentModel> getAll() {
        Context context = GlobalApplication.getAppContext();
        StudentData data = StudentData.getInstance(context,null);
        data.open();
        List<StudentModel> list = data.getAll();
        data.close();
        return  list;
    }

    @Override
    public List<StudentModel> getAll(String criteria) {
        Context context = GlobalApplication.getAppContext();
        StudentData data = StudentData.getInstance(context,null);
        data.open();
        List<StudentModel> list = data.getAll(criteria);
        data.close();
        return  list;
    }

    @Override
    public StudentModel get(Integer id) {
        Context context = GlobalApplication.getAppContext();
        StudentData data = StudentData.getInstance(context,null);
        data.open();
        StudentModel studentModel = data.get(id);
        data.close();
        return  studentModel;
    }


    @Override
    public void add(StudentModel model) throws Exception {
        validate(model);
        Context context = GlobalApplication.getAppContext();
        StudentData data = StudentData.getInstance(context,null);
        data.open();
        data.add(model);
        data.close();
    }

    @Override
    public void edit(Integer id, StudentModel model) throws Exception {
        validate(model);
        Context context = GlobalApplication.getAppContext();
        StudentData data = StudentData.getInstance(context,null);
        data.open();
        data.edit(id,model);
        data.close();
    }

    @Override
    public void delete(Integer id) {
        Context context = GlobalApplication.getAppContext();
        StudentData data = StudentData.getInstance(context,null);
        data.open();
        data.delete(id);
        data.close();
    }


    @Override
    public List<StudentModel> getAllBySubjectID(int subjectID) {
        Context context = GlobalApplication.getAppContext();
        StudentData data = StudentData.getInstance(context,null);
        data.open();
        List<StudentModel> list = data.getAllBySubjectID(subjectID);
        data.close();
        return  list;
    }

    @Override
    public List<StudentModel> getAllBy(int classSectionID) {
        return null;
    }

    @Override
    public Integer getStudentCount() {
        Context context = GlobalApplication.getAppContext();
        StudentData data = StudentData.getInstance(context,null);
        data.open();
        List<StudentModel> list = data.getAll("");
        data.close();
        return  list.size();
    }

    @Override
    public List<StudentModel> getAll(String selClassSection, String selSchoolYear) {
        Context context = GlobalApplication.getAppContext();
        StudentData data = StudentData.getInstance(context,null);
        data.open();
        List<StudentModel> list = data.getAll(selClassSection,selSchoolYear);
        data.close();
        return  list;
    }
    void validate(StudentModel model) throws Exception {
        if (model.getContact_person_number()  == ""
                || model.getContact_person() == ""
                || model.getClass_section_id() == 0
                || model.getFullName() == ""
                || model.getAddress() == ""
                || model.getStudent_code() == ""){
            throw new Exception("All fields are required!");
        }
    }
}
