package com.batstateu_ros_cpt.attendancesystem2.library.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.batstateu_ros_cpt.attendancesystem2.Misc.Utils;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IStudent;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.ISubject;
import com.batstateu_ros_cpt.attendancesystem2.library.data.base.DatabaseAccess;
import com.batstateu_ros_cpt.attendancesystem2.library.data.base.DatabaseOpenHelper;
import com.batstateu_ros_cpt.attendancesystem2.library.models.StudentModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SubjectModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SubjectData extends DatabaseAccess implements ISubject {

    private static SubjectData instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     * @param sourceDirectory
     */
    private SubjectData(Context context, String sourceDirectory) {
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
    public static SubjectData getInstance(Context context, String sourceDirectory) {
        if (instance == null) {
            instance = new SubjectData(context, sourceDirectory);
        }
        return instance;
    }


    @Override
    public List<SubjectModel> getAll() {

        List<SubjectModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM subjects", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SubjectModel model = new SubjectModel();
            model.setId(Integer.parseInt(cursor.getString(0)));
            model.setTitle(cursor.getString(1));
            model.setGrade(Utils.convertToInt(cursor.getString(2)));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public List<SubjectModel> getAll(String criteria) {
        criteria = "%" + criteria + "%";
        List<SubjectModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM students WHERE title LIKE ?", new String[]{criteria});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SubjectModel model = new SubjectModel();
            model.setId(Integer.parseInt(cursor.getString(0)));
            model.setTitle(cursor.getString(1));
            model.setGrade(Utils.convertToInt(cursor.getString(2)));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public SubjectModel get(Integer id) {
       SubjectModel model = new SubjectModel();
        Cursor cursor = database.rawQuery("SELECT * FROM subjects WHERE subject_id = ?", new String[]{id.toString()});
        if(cursor.moveToFirst()){
            model.setId(Integer.parseInt(cursor.getString(0)));
            model.setTitle(cursor.getString(1));
            model.setGrade(Utils.convertToInt(cursor.getString(2)));
        }
        cursor.close();
        return model;
    }

    @Override
    public void add(SubjectModel model) {
        ContentValues values = new ContentValues();
        values.put("title", model.getTitle());
        values.put("grade", String.valueOf(model.getGrade()));
        database.insert("subjects",null,values);
    }

    @Override
    public void edit(Integer id, SubjectModel model) {
        ContentValues values = new ContentValues();
        values.put("title", model.getTitle());
        values.put("grade", String.valueOf(model.getGrade()));
        database.update("subjects",values,"subject_id=?",new String[]{id.toString()});
    }

    @Override
    public void delete(Integer id) {
        database.delete("subjects","subject_id=?",new String[]{id.toString()});
    }

}
