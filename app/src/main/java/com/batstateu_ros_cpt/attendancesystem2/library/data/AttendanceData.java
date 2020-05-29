package com.batstateu_ros_cpt.attendancesystem2.library.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.batstateu_ros_cpt.attendancesystem2.activities.attendance.AttendanceSubjectList;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IBase;
import com.batstateu_ros_cpt.attendancesystem2.library.data.base.DatabaseAccess;
import com.batstateu_ros_cpt.attendancesystem2.library.data.base.DatabaseOpenHelper;
import com.batstateu_ros_cpt.attendancesystem2.library.models.AttendanceModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.ClassSectionModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceAbsentListModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceStudentListModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceSubjectListModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceTardinessModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceTodayModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.PerfectAttendanceModel;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttendanceData extends DatabaseAccess implements IBase<AttendanceModel> {

    private static AttendanceData instance;

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     * @param sourceDirectory
     */
    private AttendanceData(Context context, String sourceDirectory) {
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
    public static AttendanceData getInstance(Context context, String sourceDirectory) {
        if (instance == null) {
            instance = new AttendanceData(context, sourceDirectory);
        }
        return instance;
    }


    @Override
    public List<AttendanceModel> getAll() {

        List<AttendanceModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM attendance", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AttendanceModel model = new AttendanceModel();
            model.setId(Integer.parseInt(cursor.getString(0)));
            model.setStudentID(Integer.parseInt(cursor.getString(1)));
            model.setSubjectID(Integer.parseInt(cursor.getString(2)));
            model.setCreateTimeStamp(DateTimeUtils.formatDate(cursor.getString(3)));
            model.setPresent(Boolean.valueOf(cursor.getString(4)));
            model.setSchoolYearID(Integer.parseInt(cursor.getString(5)));
            model.setGradingPeriod(Integer.parseInt(cursor.getString(6)));
            model.setLate(Boolean.valueOf(cursor.getString(7)));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public List<AttendanceModel> getAll(String criteria) {
        criteria = "%" + criteria + "%";
        List<AttendanceModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM attendance WHERE name LIKE ?", new String[]{criteria});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            AttendanceModel model = new AttendanceModel();
            model.setId(Integer.parseInt(cursor.getString(0)));
            model.setStudentID(Integer.parseInt(cursor.getString(1)));
            model.setSubjectID(Integer.parseInt(cursor.getString(2)));
            model.setCreateTimeStamp(DateTimeUtils.formatDate(cursor.getString(3)));
            model.setPresent(Boolean.valueOf(cursor.getString(4)));
            model.setSchoolYearID(Integer.parseInt(cursor.getString(5)));
            model.setGradingPeriod(Integer.parseInt(cursor.getString(6)));
            model.setLate(Boolean.valueOf(cursor.getString(7)));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    @Override
    public AttendanceModel get(Integer id) {
        AttendanceModel model = new AttendanceModel();
        Cursor cursor = database.rawQuery("SELECT * FROM attendance WHERE class_section_id = ?", new String[]{id.toString()});
        if (cursor.moveToFirst()) {
            model.setId(Integer.parseInt(cursor.getString(0)));
            model.setStudentID(Integer.parseInt(cursor.getString(1)));
            model.setSubjectID(Integer.parseInt(cursor.getString(2)));
            model.setCreateTimeStamp(DateTimeUtils.formatDate(cursor.getString(3)));
            model.setPresent(Boolean.valueOf(cursor.getString(4)));
            model.setSchoolYearID(Integer.parseInt(cursor.getString(5)));
            model.setGradingPeriod(Integer.parseInt(cursor.getString(6)));
            model.setLate(Boolean.valueOf(cursor.getString(7)));
        }
        cursor.close();
        return model;
    }

    @Override
    public void add(AttendanceModel model) {
        ContentValues values = new ContentValues();
        values.put("student_id", model.getStudentID());
        values.put("subject_id", model.getSubjectID());
        values.put("create_time_stamp", DateTimeUtils.formatDate(model.getCreateTimeStamp()));
        values.put("is_present", model.isPresent());
        values.put("school_year_id", model.getSchoolYearID());
        values.put("grading_period", model.getGradingPeriod());
        values.put("is_late", model.isLate());
        database.insert("attendance", null, values);
    }

    @Override
    public void edit(Integer id, AttendanceModel model) {
        ContentValues values = new ContentValues();
        values.put("student_id", model.getStudentID());
        values.put("subject_id", model.getSubjectID());
        values.put("create_time_stamp", DateTimeUtils.formatDate(model.getCreateTimeStamp()));
        values.put("is_present", model.isPresent());
        values.put("school_year_id", model.getSchoolYearID());
        values.put("grading_period", model.getGradingPeriod());
        values.put("is_late", model.isLate());
        database.update("attendance", values, "attendance_id=?", new String[]{id.toString()});
    }

    @Override
    public void delete(Integer id) {
        database.delete("attendance", "attendance_id=?", new String[]{id.toString()});
    }

    public List<AttendanceSubjectListModel> getAllAttendanceSubjects(int schoolYearID, int gradingPeriod, Date fromDate, Date toDate) {
        List<AttendanceSubjectListModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(
                "SELECT " +
                            "subject_id, " +
                            "class_section_id," +
                            "class_section_name," +
                            "subject_name, " +
                            "present," +
                            "absent, " +
                            "create_time_stamp, " +
                            "late " +
                        "FROM " +
                            "view_attendance_subjects " +
                        "WHERE " +
                            "school_year_id = ? AND " +
                            "grading_period = ? AND " +
                            "create_time_stamp >= date(?) AND " +
                            "create_time_stamp <  date(?, '+1 day')"
                ,new String[]{ String.valueOf(schoolYearID),String.valueOf(gradingPeriod),DateTimeUtils.formatDate(fromDate), DateTimeUtils.formatDate(toDate)}
                );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Todo set object properties
            AttendanceSubjectListModel model = new AttendanceSubjectListModel();
            model.setSubjectID(Integer.valueOf(cursor.getString(0)));
            model.setClassSectionID(Integer.parseInt(cursor.getString(1)));
            model.setClassSectionName(cursor.getString(2));
            model.setSubjectName(cursor.getString(3));
            model.setPresentCount(Integer.valueOf(cursor.getString(4)));
            model.setAbsentCount(Integer.parseInt(cursor.getString(5)));
            model.setDate(DateTimeUtils.formatDate(cursor.getString(6)));
            model.setLateCount(cursor.getInt(7));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<AttendanceStudentListModel> getAttendanceStudents(int classSectionID, int subjectID, Date date) {
        List<AttendanceStudentListModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(
                "SELECT " +
                        "student_id, " +
                        "full_name, " +
                        "is_present, " +
                        "attendance_id, "+
                        "is_late " +
                        "FROM " +
                        "view_attendance_students " +
                        "WHERE " +
                        "class_section_id = ? AND " +
                        "subject_id = ? AND " +
                        "create_time_stamp >= date(?) AND " +
                        "create_time_stamp <  date(?, '+1 day')",
                new String[]{String.valueOf(classSectionID),String.valueOf(subjectID),DateTimeUtils.formatDate(date),DateTimeUtils.formatDate(date)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Todo set object properties
            AttendanceStudentListModel model = new AttendanceStudentListModel();
            model.setStudentID(Integer.valueOf(cursor.getString(0)));
            model.setFullName(cursor.getString(1));
            model.setPresent((cursor.getInt(cursor.getColumnIndex("is_present")) == 1));

            model.setAttendanceID(cursor.getInt(cursor.getColumnIndex("attendance_id")));
            model.setLate((cursor.getInt(4) == 1));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public void deleteBy(int subjectID, Integer studentID, Date date) {
        database.delete("attendance", "subject_id=? AND student_id=? AND " +
                "create_time_stamp >= date(?) " +
                "AND create_time_stamp <  date(?, '+1 day')", new String[]{String.valueOf(subjectID),String.valueOf(studentID),DateTimeUtils.formatDate(date),DateTimeUtils.formatDate(date)});
    }

    public void updateAttendancePresentStatus(int attendanceID, boolean isPresent) {
        ContentValues values = new ContentValues();
        values.put("is_present", isPresent);
        database.update("attendance", values, "attendance_id=?", new String[]{String.valueOf(attendanceID)});
    }


    public List<AttendanceAbsentListModel> getAttendanceAbsents(int schoolYearID, int gradingPeriod) {
        List<AttendanceAbsentListModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(
                "SELECT " +
                        "student_id, " +
                        "full_name, " +
                        "subject_title, " +
                        "absents, "+
                        "contact_person_number, "+
                        "contact_person "+
                        "FROM " +
                        "view_attendance_absent_count " +
                        "WHERE " +
                        "school_year_id = ? AND " +
                        "grading_period = ?;"
                ,
                new String[]{
                        String.valueOf(schoolYearID),
                        String.valueOf(gradingPeriod)
                }
                );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Todo set object properties
            AttendanceAbsentListModel model = new AttendanceAbsentListModel();
            model.setStudentID(Integer.valueOf(cursor.getString(0)));
            model.setFullName(cursor.getString(1));
            model.setSubjectTitle(cursor.getString(2));
            model.setAbsents(cursor.getInt(3));
            model.setNumber(cursor.getString(4));
            model.setRecipient(cursor.getString(5));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<AttendanceStudentListModel> getAttendanceAbsents(Date date) {
        String dateStr =  DateTimeUtils.formatDate(date).substring(0,10);

        List<AttendanceStudentListModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(
                "SELECT " +
                        "attendance_id, " +
                        "student_id, " +
                        "full_name, " +
                        "is_present, "+
                        "create_time_stamp "+
                        "FROM " +
                        "view_attendance_students " +
                        "WHERE " +
                        "is_present = 0 AND " +
                        "create_time_stamp >= date(?) AND " +
                        "create_time_stamp <  date(?, '+1 day')"
                ,
                new String[]{
                        dateStr,
                        dateStr
                }
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Todo set object properties
            AttendanceStudentListModel model = new AttendanceStudentListModel();
            model.setAttendanceID(cursor.getInt(0));
            model.setStudentID(cursor.getInt(1));
            model.setFullName(cursor.getString(1));
            model.setPresent(Boolean.valueOf(cursor.getString(3)));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
    public List<AttendanceStudentListModel> getAttendanceAbsents(Date dateFrom, Date dateTo){
        String dateFromStr =  DateTimeUtils.formatDate(dateFrom).substring(0,10);
        String dateToStr =  DateTimeUtils.formatDate(dateTo).substring(0,10);
        List<AttendanceStudentListModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(
                "SELECT " +
                        "attendance_id, " +
                        "student_id, " +
                        "full_name, " +
                        "is_present, "+
                        "create_time_stamp "+
                        "FROM " +
                        "view_attendance_students " +
                        "WHERE " +
                        "is_present = 0 AND " +
                        "create_time_stamp >= date(?) AND " +
                        "create_time_stamp <  date(?, '+1 day')"
                ,
                new String[]{
                        dateFromStr,
                        dateToStr
                }
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Todo set object properties
            AttendanceStudentListModel model = new AttendanceStudentListModel();
            model.setAttendanceID(cursor.getInt(0));
            model.setStudentID(cursor.getInt(1));
            model.setFullName(cursor.getString(1));
            model.setPresent(Boolean.valueOf(cursor.getString(3)));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public void updateAttendanceLateStatus(int attendanceID, boolean isLate) {
        ContentValues values = new ContentValues();
        values.put("is_late", isLate);
        database.update("attendance", values, "attendance_id=?", new String[]{String.valueOf(attendanceID)});
    }

    public List<PerfectAttendanceModel> getAllPerfectAttendance(int schoolYearID, Integer gradingPeriod, int classSectionID) {
        List<PerfectAttendanceModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(
                "SELECT " +
                        "full_name " +
                        "FROM " +
                        "view_perfect_attendance " +
                        "WHERE " +
                        "school_year_id = ? AND " +
                        "grading_period = ? AND " +
                        "class_section_id = ? AND " +
                        "is_present = 1"
                ,new String[]{ String.valueOf(schoolYearID),String.valueOf(gradingPeriod),String.valueOf(classSectionID)}
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Todo set object properties
            PerfectAttendanceModel model = new PerfectAttendanceModel();
            model.setFullName(cursor.getString(0));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<AttendanceTardinessModel> getAllAttendanceTardiness(Integer schoolYearID, Integer gradingPeriod) {
        List<AttendanceTardinessModel> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(
                "SELECT " +
                        "student_id, " +
                        "full_name, " +
                        "class_section_name, " +
                        "absent, " +
                        "late, " +
                        "class_section_id "+
                        "FROM " +
                        "view_attendance_tardiness " +
                        "WHERE " +
                        "school_year_id = ? AND " +
                        "grading_period = ? "
                ,new String[]{ String.valueOf(schoolYearID),String.valueOf(gradingPeriod)}
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Todo set object properties
            AttendanceTardinessModel model = new AttendanceTardinessModel();
            model.setStudentID(cursor.getInt(0));
            model.setStudentName(cursor.getString(1));
            model.setClassSectionName(cursor.getString(2));
            model.setAbsentCount(cursor.getInt(3));
            model.setLateCount(cursor.getInt(4));
            model.setClassSectionID(cursor.getInt(5));
            list.add(model);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public AttendanceTodayModel getAttendanceToday(Integer studentID, Date dateToday) {

        String mDateToday =  DateTimeUtils.formatDate(dateToday).substring(0,10);
        AttendanceTodayModel model = new AttendanceTodayModel();
        Cursor cursor = database.rawQuery(
                "SELECT " +
                        "present, " +
                        "late " +
                        "FROM " +
                        "view_attendance_today " +
                        "WHERE " +

                        "student_id = ? AND " +
                        "create_time_stamp >= date(?) AND " +
                        "create_time_stamp <  date(?, '+1 day')"
                ,
                new String[]{

                        studentID.toString(),
                        mDateToday,
                        mDateToday
                }
        );
        if (cursor.moveToFirst()) {
            model.setPresentCount(cursor.getInt(0));
            model.setLateCount(cursor.getInt(1));

        }
        cursor.close();
        return model;
    }

    public Integer getTotalSubjectToday(Integer classSectionID, Date dateToday) {
        int total = 0;
        String dateTodayStr =  DateTimeUtils.formatDate(dateToday).substring(0,10);
        Cursor cursor = database
                .rawQuery(
                        "SELECT count(class_section_id) FROM " +
                                "view_attendance_subjects " +
                                "WHERE class_section_id = ? AND " +
                                "create_time_stamp >= date(?) AND " +
                                "create_time_stamp <  date(?, '+1 day')",
                        new String[]{classSectionID.toString(),dateTodayStr,dateTodayStr});
        if (cursor.moveToFirst()) {
            total = cursor.getInt(0);

        }
        cursor.close();
        return total;
    }

    public AttendanceTardinessModel getAttendanceTardiness(int studentID, int schoolYearID, int gradingPeriod) {

        Cursor cursor = database.rawQuery(
                "SELECT " +
                        "student_id, " +
                        "full_name, " +
                        "class_section_name, " +
                        "absent, " +
                        "late, " +
                        "class_section_id "+
                        "FROM " +
                        "view_attendance_tardiness " +
                        "WHERE " +
                        "student_id = ? AND " +
                        "school_year_id = ? AND " +
                        "grading_period = ? "
                ,new String[]{ String.valueOf(studentID),String.valueOf(schoolYearID),String.valueOf(gradingPeriod)}
        );
        AttendanceTardinessModel model = new AttendanceTardinessModel();
        if(cursor.moveToFirst()) {
            //Todo set object properties
            model.setStudentID(cursor.getInt(0));
            model.setStudentName(cursor.getString(1));
            model.setClassSectionName(cursor.getString(2));
            model.setAbsentCount(cursor.getInt(3));
            model.setLateCount(cursor.getInt(4));
            model.setClassSectionID(cursor.getInt(5));
        }
        cursor.close();
        return model;
    }


    public List<Integer> getAllAttendanceStudentID(int schoolYearID, Integer gradingPeriod, Integer classSectionID) {
        List<Integer> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(
                "SELECT " +
                        "student_id " +
                        "FROM " +
                        "view_attendance_student_id " +
                        "WHERE " +
                        "school_year_id = ? AND " +
                        "grading_period = ? AND " +
                        "class_section_id = ? "
                ,new String[]{ String.valueOf(schoolYearID),String.valueOf(gradingPeriod), classSectionID.toString()}
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //Todo set object properties
            list.add(cursor.getInt(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}


