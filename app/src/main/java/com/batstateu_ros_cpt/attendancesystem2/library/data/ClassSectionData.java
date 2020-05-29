package com.batstateu_ros_cpt.attendancesystem2.library.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IBase;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IClassSection;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.ISubject;
import com.batstateu_ros_cpt.attendancesystem2.library.data.base.DatabaseAccess;
import com.batstateu_ros_cpt.attendancesystem2.library.data.base.DatabaseOpenHelper;
import com.batstateu_ros_cpt.attendancesystem2.library.models.ClassSectionModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SubjectModel;

import java.util.ArrayList;
import java.util.List;

public class ClassSectionData extends DatabaseAccess implements IBase<ClassSectionModel> {

    private static ClassSectionData instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     * @param sourceDirectory
     */
    private ClassSectionData(Context context, String sourceDirectory) {
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
    public static ClassSectionData getInstance(Context context, String sourceDirectory) {
        if (instance == null) {
            instance = new ClassSectionData(context, sourceDirectory);
        }
        return instance;
    }


    @Override
    public List<ClassSectionModel> getAll() {

        List<ClassSectionModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT class_section_id,class_section_name,grade,school_year FROM view_class_sections", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ClassSectionModel model = new ClassSectionModel();
            model.setID(Integer.parseInt(cursor.getString(0)));
            model.setName(cursor.getString(1));
            model.setGrade(Integer.parseInt(cursor.getString(2)));
            model.setSchoolYear(cursor.getString(3));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public List<ClassSectionModel> getAll(String criteria) {
        criteria = "%" + criteria + "%";
        List<ClassSectionModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT class_section_id,class_section_name,grade,school_year FROM view_class_sections WHERE class_section_name LIKE ?", new String[]{criteria});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ClassSectionModel model = new ClassSectionModel();
            model.setID(Integer.parseInt(cursor.getString(0)));
            model.setName(cursor.getString(1));
            model.setGrade(Integer.parseInt(cursor.getString(2)));
            model.setSchoolYear(cursor.getString(3));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public ClassSectionModel get(Integer id) {
        ClassSectionModel model = new ClassSectionModel();
        Cursor cursor = database.rawQuery("SELECT class_section_id,name,grade,school_year_id FROM class_sections WHERE class_section_id = ?", new String[]{id.toString()});
        if(cursor.moveToFirst()){
            model.setID(Integer.parseInt(cursor.getString(0)));
            model.setName(cursor.getString(1));
            model.setGrade(Integer.parseInt(cursor.getString(2)));
            model.setSchoolYearID(cursor.getInt(3));
        }
        cursor.close();
        return model;
    }

    @Override
    public void add(ClassSectionModel model) {
        ContentValues values = new ContentValues();
        values.put("name", model.getName());
        values.put("grade", model.getGrade());
        values.put("school_year_id", model.getSchoolYearID());
        database.insert("class_sections",null,values);
    }

    @Override
    public void edit(Integer id, ClassSectionModel model) {
        ContentValues values = new ContentValues();
        values.put("name", model.getName());
        values.put("grade", model.getGrade());
        values.put("school_year_id", model.getSchoolYearID());
        database.update("class_sections",values,"class_section_id=?",new String[]{id.toString()});
    }

    @Override
    public void delete(Integer id) {
        database.delete("class_sections","class_section_id=?",new String[]{id.toString()});
    }

    public List<ClassSectionModel> getItemNeeded() {
        return new ArrayList<>();
    }
}
