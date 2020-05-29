package com.batstateu_ros_cpt.attendancesystem2.library.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.batstateu_ros_cpt.attendancesystem2.Misc.Utils;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IBase;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IStudent;
import com.batstateu_ros_cpt.attendancesystem2.library.data.base.DatabaseAccess;
import com.batstateu_ros_cpt.attendancesystem2.library.data.base.DatabaseOpenHelper;
import com.batstateu_ros_cpt.attendancesystem2.library.models.StudentModel;

import java.util.ArrayList;
import java.util.List;

public class StudentData  extends DatabaseAccess implements IBase<StudentModel> {

    private static StudentData instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     * @param sourceDirectory
     */
    private StudentData(Context context, String sourceDirectory) {
        if (sourceDirectory == null) {
            this.openHelper = new DatabaseOpenHelper(context);
        } else {
            this.openHelper = new DatabaseOpenHelper(context, sourceDirectory);
        }
    }
    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context         the Context
     * @param sourceDirectory optional external directory
     * @return the instance of DabaseAccess
     */
    public static StudentData getInstance(Context context, String sourceDirectory) {
        if (instance == null) {
            instance = new StudentData(context, sourceDirectory);
        }
        return instance;
    }


    @Override
    public List<StudentModel> getAll() {

        List<StudentModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM students", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            StudentModel studentModel = new StudentModel();
            studentModel.setId(Integer.parseInt(cursor.getString(0)));
            studentModel.setFullName(cursor.getString(1));
            studentModel.setAddress(cursor.getString(2));
            studentModel.setContact_person(cursor.getString(3));
            studentModel.setContact_person_number(cursor.getString(4));
            studentModel.setStudent_code(cursor.getString(5));

            studentModel.setClass_section_id(Utils.convertToInt(cursor.getString(6)));
            list.add(studentModel);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public List<StudentModel> getAll(String criteria) {
        criteria = "%" + criteria + "%";
        List<StudentModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM students WHERE full_name LIKE ?", new String[]{criteria});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            StudentModel studentModel = new StudentModel();
            studentModel.setId(Integer.parseInt(cursor.getString(0)));
            studentModel.setFullName(cursor.getString(1));
            studentModel.setAddress(cursor.getString(2));
            studentModel.setContact_person(cursor.getString(3));
            studentModel.setContact_person_number(cursor.getString(4));
            studentModel.setStudent_code(cursor.getString(5));
            studentModel.setClass_section_id(Utils.convertToInt(cursor.getString(6)));
            list.add(studentModel);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public StudentModel get(Integer id) {
       StudentModel studentModel = new StudentModel();
        Cursor cursor = database.rawQuery("SELECT * FROM students WHERE student_id = ?", new String[]{id.toString()});
        if(cursor.moveToFirst()){
            studentModel.setId(Integer.parseInt(cursor.getString(0)));
            studentModel.setFullName(cursor.getString(1));
            studentModel.setAddress(cursor.getString(2));
            studentModel.setContact_person(cursor.getString(3));
            studentModel.setContact_person_number(cursor.getString(4));
            studentModel.setStudent_code(cursor.getString(5));
            studentModel.setClass_section_id(Utils.convertToInt(cursor.getString(6)));
        }
        cursor.close();
        return studentModel;
    }

    @Override
    public void add(StudentModel model) {
        ContentValues values = new ContentValues();
        values.put("full_name", model.getFullName());
        values.put("address", model.getAddress());
        values.put("contact_person", model.getContact_person());
        values.put("contact_person_number", model.getContact_person_number());
        values.put("student_code", model.getStudent_code());
        values.put("class_section_id", model.getClass_section_id());
        database.insert("students",null,values);
    }

    @Override
    public void edit(Integer id, StudentModel model) {
        ContentValues values = new ContentValues();
        values.put("full_name", model.getFullName());
        values.put("address", model.getAddress());
        values.put("contact_person", model.getContact_person());
        values.put("contact_person_number", model.getContact_person_number());
        values.put("student_code", model.getStudent_code());
        values.put("class_section_id", model.getClass_section_id());
        database.update("students",values,"student_id=?",new String[]{id.toString()});
    }

    @Override
    public void delete(Integer id) {
        database.delete("students","student_id=?",new String[]{id.toString()});
    }


    public List<StudentModel> getAllBySubjectID(int subjectID) {
        List<StudentModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(
                "SELECT " +
                        "students.student_id,full_name,address, contact_person,contact_person_number " +
                "FROM subject_enrolles " +
                "INNER JOIN students ON (subject_enrolles.student_id = students.student_id) " +
                "WHERE subject_enrolles.subject_id = ?", new String[]{String.valueOf(subjectID)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            StudentModel studentModel = new StudentModel();
            studentModel.setId(Integer.parseInt(cursor.getString(0)));
            studentModel.setFullName(cursor.getString(1));
            studentModel.setAddress(cursor.getString(2));
            studentModel.setContact_person(cursor.getString(3));
            studentModel.setContact_person_number(cursor.getString(4));
            list.add(studentModel);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<StudentModel> getAllBy(int classSectionID) {
        List<StudentModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM students WHERE class_section_id LIKE ?", new String[]{String.valueOf(classSectionID)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            StudentModel studentModel = new StudentModel();
            studentModel.setId(Integer.parseInt(cursor.getString(0)));
            studentModel.setFullName(cursor.getString(1));
            studentModel.setAddress(cursor.getString(2));
            studentModel.setContact_person(cursor.getString(3));
            studentModel.setContact_person_number(cursor.getString(4));
            studentModel.setStudent_code(cursor.getString(5));
            studentModel.setClass_section_id(Utils.convertToInt(cursor.getString(6)));
            list.add(studentModel);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<StudentModel> getAll(String selClassSection, String selSchoolYear) {
        selClassSection = "%" + selClassSection + "%";
        selSchoolYear = "%" + selSchoolYear + "%";

        List<StudentModel> list = new ArrayList<>();
        /*Cursor cursor = database.rawQuery("SELECT " +
                "student_id," +
                "full_name," +
                "address " +
                "FROM " +
                "view_student_class_sections " +
                "WHERE class_section_name LIKE ? AND school_year LIKE ?", new String[]{selClassSection,selSchoolYear});*/
        Cursor cursor = database.rawQuery("SELECT " +
                "student_id," +
                "full_name," +
                "address " +
                "FROM " +
                "view_student_class_sections " +
                "WHERE class_section_name LIKE ? AND school_year LIKE ?",new String[]{selClassSection,selSchoolYear});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            StudentModel studentModel = new StudentModel();
            studentModel.setId(Integer.parseInt(cursor.getString(0)));
            studentModel.setFullName(cursor.getString(1));
            studentModel.setAddress(cursor.getString(2));
            /*studentModel.setContact_person(cursor.getString(3));
            studentModel.setContact_person_number(cursor.getString(4));
            studentModel.setStudent_code(cursor.getString(5));
            studentModel.setClass_section_id(Utils.convertToInt(cursor.getString(6)));*/
            list.add(studentModel);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
