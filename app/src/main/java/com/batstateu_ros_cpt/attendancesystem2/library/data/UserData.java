package com.batstateu_ros_cpt.attendancesystem2.library.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IBase;
import com.batstateu_ros_cpt.attendancesystem2.library.data.base.DatabaseAccess;
import com.batstateu_ros_cpt.attendancesystem2.library.data.base.DatabaseOpenHelper;
import com.batstateu_ros_cpt.attendancesystem2.library.models.AttendanceModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceAbsentListModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceStudentListModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceSubjectListModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.user.UserModel;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserData extends DatabaseAccess implements IBase<UserModel> {

    private static UserData instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     * @param sourceDirectory
     */
    private UserData(Context context, String sourceDirectory) {
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
    public static UserData getInstance(Context context, String sourceDirectory) {
        if (instance == null) {
            instance = new UserData(context, sourceDirectory);
        }
        return instance;
    }


    @Override
    public List<UserModel> getAll() {

        List<UserModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM users", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserModel model = new UserModel();
            model.setId(Integer.parseInt(cursor.getString(0)));
            model.setUsername(cursor.getString(1));
            model.setPassword(cursor.getString(2));
            model.setFullName(cursor.getString(3));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public List<UserModel> getAll(String criteria) {
        criteria = "%" + criteria + "%";
        List<UserModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM users WHERE name LIKE ?", new String[]{criteria});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserModel model = new UserModel();
            model.setId(Integer.parseInt(cursor.getString(0)));
            model.setUsername(cursor.getString(1));
            model.setPassword(cursor.getString(2));
            model.setFullName(cursor.getString(3));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public UserModel get(Integer id) {
        UserModel model = new UserModel();
        Cursor cursor = database.rawQuery("SELECT * FROM users WHERE user_id = ?", new String[]{id.toString()});
        if (cursor.moveToFirst()) {
            model.setId(Integer.parseInt(cursor.getString(0)));
            model.setUsername(cursor.getString(1));
            model.setPassword(cursor.getString(2));
            model.setFullName(cursor.getString(3));
        }
        cursor.close();
        return model;
    }

    @Override
    public void add(UserModel model) {
        ContentValues values = new ContentValues();
        values.put("username", model.getUsername());
        values.put("password", model.getPassword());
        values.put("full_name", model.getFullName());

        database.insert("users", null, values);
    }

    @Override
    public void edit(Integer id, UserModel model) {
        ContentValues values = new ContentValues();
        values.put("username", model.getUsername());
        values.put("password", model.getPassword());
        values.put("full_name", model.getFullName());
        database.update("users", values, "user_id=?", new String[]{id.toString()});
    }

    @Override
    public void delete(Integer id) {
        database.delete("users", "user_id=?", new String[]{id.toString()});
    }


    public UserModel get(String userName, String password) {
        UserModel model = new UserModel();
        Cursor cursor = database.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{userName,password});
        if (cursor.moveToFirst()) {
            model.setId(Integer.parseInt(cursor.getString(0)));
            model.setUsername(cursor.getString(1));
            model.setPassword(cursor.getString(2));
            model.setFullName(cursor.getString(3));
        }
        cursor.close();
        return model;
    }
}


