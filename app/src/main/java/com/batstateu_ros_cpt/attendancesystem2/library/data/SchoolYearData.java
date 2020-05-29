package com.batstateu_ros_cpt.attendancesystem2.library.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IBase;
import com.batstateu_ros_cpt.attendancesystem2.library.data.base.DatabaseAccess;
import com.batstateu_ros_cpt.attendancesystem2.library.data.base.DatabaseOpenHelper;
import com.batstateu_ros_cpt.attendancesystem2.library.models.AttendanceModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SchoolYearModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceSubjectListModel;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SchoolYearData extends DatabaseAccess implements IBase<SchoolYearModel> {

    private static SchoolYearData instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     * @param sourceDirectory
     */
    private SchoolYearData(Context context, String sourceDirectory) {
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
    public static SchoolYearData getInstance(Context context, String sourceDirectory) {
        if (instance == null) {
            instance = new SchoolYearData(context, sourceDirectory);
        }
        return instance;
    }


    @Override
    public List<SchoolYearModel> getAll() {

        List<SchoolYearModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM school_year", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SchoolYearModel model = new SchoolYearModel();
            model.setId(Integer.parseInt(cursor.getString(0)));
            model.setName(cursor.getString(1));
            model.setActive(Boolean.valueOf(cursor.getString(2)));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public List<SchoolYearModel> getAll(String criteria) {
        criteria = "%" + criteria + "%";
        List<SchoolYearModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM attendance WHERE name LIKE ?", new String[]{criteria});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SchoolYearModel model = new SchoolYearModel();
            model.setId(Integer.parseInt(cursor.getString(0)));
            model.setName(cursor.getString(1));
            model.setActive(Boolean.valueOf(cursor.getString(2)));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public SchoolYearModel get(Integer id) {
        SchoolYearModel model = new SchoolYearModel();
        Cursor cursor = database.rawQuery("SELECT * FROM school_year WHERE school_year_id = ?", new String[]{id.toString()});
        if (cursor.moveToFirst()) {
            model.setId(Integer.parseInt(cursor.getString(0)));
            model.setName(cursor.getString(1));
            model.setActive(Boolean.valueOf(cursor.getString(2)));
        }
        cursor.close();
        return model;
    }

    @Override
    public void add(SchoolYearModel model) {
        ContentValues values = new ContentValues();
        values.put("name", model.getName());
        values.put("is_active", String.valueOf(model.isActive()));
        database.insert("school_year", null, values);
    }

    @Override
    public void edit(Integer id, SchoolYearModel model) {
        ContentValues values = new ContentValues();
        values.put("name", model.getName());
        values.put("is_active", String.valueOf(model.isActive()));
        database.update("school_year", values, "school_year_id=?", new String[]{id.toString()});
    }

    @Override
    public void delete(Integer id) {
        database.delete("school_year", "school_year_id=?", new String[]{id.toString()});
    }


}


