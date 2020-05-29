package com.batstateu_ros_cpt.attendancesystem2.library.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.batstateu_ros_cpt.attendancesystem2.Misc.Utils;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IBase;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.ISubject;
import com.batstateu_ros_cpt.attendancesystem2.library.data.base.DatabaseAccess;
import com.batstateu_ros_cpt.attendancesystem2.library.data.base.DatabaseOpenHelper;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SmsModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SubjectModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.sms.SmsDetailModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.sms.SmsListModel;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import java.util.ArrayList;
import java.util.List;

public class SmsData extends DatabaseAccess implements IBase<SmsModel> {

    private static SmsData instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     * @param sourceDirectory
     */
    private SmsData(Context context, String sourceDirectory) {
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
    public static SmsData getInstance(Context context, String sourceDirectory) {
        if (instance == null) {
            instance = new SmsData(context, sourceDirectory);
        }
        return instance;
    }


    @Override
    public List<SmsModel> getAll() {

        List<SmsModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM sms", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SmsModel model = new SmsModel();
            model.setId(cursor.getInt(0));
            model.setMessage(cursor.getString(1));
            model.setSend((cursor.getInt(2) == 1));
            model.setDate(DateTimeUtils.formatDate(cursor.getString(3)));
            model.setStudentID(cursor.getInt(4));
            model.setSchoolYearID(cursor.getInt(5));
            model.setGradingPeriod(cursor.getInt(6));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public List<SmsModel> getAll(String criteria) {
        criteria = "%" + criteria + "%";
        List<SmsModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM sms WHERE message LIKE ?", new String[]{criteria});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SmsModel model = new SmsModel();
            model.setId(cursor.getInt(0));
            model.setMessage(cursor.getString(1));
            model.setSend((cursor.getInt(2) == 1));
            model.setDate(DateTimeUtils.formatDate(cursor.getString(3)));
            model.setStudentID(cursor.getInt(4));
            model.setSchoolYearID(cursor.getInt(5));
            model.setGradingPeriod(cursor.getInt(6));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public SmsModel get(Integer id) {
        SmsModel model = new SmsModel();
        Cursor cursor = database.rawQuery("SELECT * FROM sms WHERE sms_id = ?", new String[]{id.toString()});
        if(cursor.moveToFirst()){
            model.setId(cursor.getInt(0));
            model.setMessage(cursor.getString(1));
            model.setSend((cursor.getInt(2) == 1));
            model.setDate(DateTimeUtils.formatDate(cursor.getString(3)));
            model.setStudentID(cursor.getInt(4));
            model.setSchoolYearID(cursor.getInt(5));
            model.setGradingPeriod(cursor.getInt(6));
        }
        cursor.close();
        return model;
    }

    @Override
    public void add(SmsModel model) {
        ContentValues values = new ContentValues();
        values.put("message", model.getMessage());
        values.put("is_send", model.isSend());
        values.put("create_time_stamp", DateTimeUtils.formatDate(model.getDate()));
        values.put("student_id", model.getStudentID());
        values.put("school_year_id", model.getSchoolYearID());
        values.put("grading_period", model.getGradingPeriod());
        database.insert("sms",null,values);
    }

    @Override
    public void edit(Integer id, SmsModel model) {
        ContentValues values = new ContentValues();
        values.put("message", model.getMessage());
        values.put("is_send", model.isSend());
        values.put("create_time_stamp", DateTimeUtils.formatDate(model.getDate()));
        values.put("student_id", model.getStudentID());
        values.put("school_year_id", model.getSchoolYearID());
        values.put("grading_period", model.getGradingPeriod());
        database.update("sms",values,"sms_id=?",new String[]{id.toString()});
    }

    @Override
    public void delete(Integer id) {
        database.delete("sms","sms_id=?",new String[]{id.toString()});
    }

    public List<SmsListModel> getSmsList(int schoolYearID, int gradingPeriod) {
        List<SmsListModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT sms_id,date,contact_person, is_send FROM view_sms WHERE school_year_id = ? AND grading_period = ?",  new String[]{String.valueOf(schoolYearID),String.valueOf(gradingPeriod)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SmsListModel model = new SmsListModel();
            model.setId(cursor.getInt(0));
            model.setDate(DateTimeUtils.formatDate(cursor.getString(1)));
            model.setRecipient(cursor.getString(2));
            model.setSend(cursor.getInt(3) == 1);

            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public int getSmsCount(int schoolYearID, int gradingPeriod, int studentID) {

        List<SmsModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT count(*) as student_count FROM sms WHERE school_year_id = ? AND grading_period =? AND student_id = ?",
                new String[]{String.valueOf(schoolYearID),String.valueOf(gradingPeriod),String.valueOf(studentID)});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count;
    }

    public SmsDetailModel getSmsDetail(int id) {
        SmsDetailModel model = new SmsDetailModel();
        Cursor cursor = database.rawQuery(
                "SELECT  " +
                        "sms_id, " +
                        "contact_person," +
                        "contact_person_number," +
                        "message " +
                        "FROM view_sms_detail " +
                        "WHERE sms_id = ?;", new String[]{String.valueOf(id)});
        if(cursor.moveToFirst()){
            model.setId(cursor.getInt(0));
            model.setContactPerson(cursor.getString(1));
            model.setContactPersonNumber(cursor.getString(2));
            model.setMessage(cursor.getString(3));
        }
        cursor.close();
        return model;
    }

    public int getUnsentSMSCount() {
        int count = 0;
        Cursor cursor = database.rawQuery(
                "SELECT  " +
                        "COUNT(sms_id) as sms_count " +
                        "FROM sms " +
                        "WHERE is_send = ?;", new String[]{String.valueOf(0)});
        if(cursor.moveToFirst()){
            count = cursor.getInt(0);

        }
        cursor.close();
        return count;
    }

    public void setSmsSend(int id) {
        ContentValues values = new ContentValues();
        values.put("is_send", 1);
        database.update("sms",values,"sms_id=?",new String[]{String.valueOf(id)});
    }

    public int getSentSMSCount() {
        int count = 0;
        Cursor cursor = database.rawQuery(
                "SELECT  " +
                        "COUNT(sms_id) as sms_count " +
                        "FROM sms " +
                        "WHERE is_send = ?;", new String[]{String.valueOf(1)});
        if(cursor.moveToFirst()){
            count = cursor.getInt(0);

        }
        cursor.close();
        return count;
    }
}
