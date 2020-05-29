package com.batstateu_ros_cpt.attendancesystem2.library.logic;

import android.content.Context;

import com.batstateu_ros_cpt.attendancesystem2.library.GlobalApplication;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IAttendance;
import com.batstateu_ros_cpt.attendancesystem2.library.data.AttendanceData;
import com.batstateu_ros_cpt.attendancesystem2.library.data.ClassSectionData;
import com.batstateu_ros_cpt.attendancesystem2.library.data.SmsData;
import com.batstateu_ros_cpt.attendancesystem2.library.data.StudentData;
import com.batstateu_ros_cpt.attendancesystem2.library.models.AttendanceModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SmsModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.StudentModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceAbsentListModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceStudentListModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceSubjectListModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceTardinessModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceTodayModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.PerfectAttendanceModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.IntegerRes;


public class AttendanceLogic implements IAttendance {

    @Override
    public List<AttendanceModel> getAll() {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        List<AttendanceModel> list = data.getAll();
        data.close();
        return  list;
    }

    @Override
    public List<AttendanceModel> getAll(String criteria) {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        List<AttendanceModel> list = data.getAll(criteria);
        data.close();
        return  list;
    }

    @Override
    public AttendanceModel get(Integer id) {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        AttendanceModel model = data.get(id);
        data.close();
        return  model;
    }


    @Override
    public void add(AttendanceModel model) {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        data.add(model);
        data.close();
    }

    @Override
    public void edit(Integer id, AttendanceModel model) {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
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
    public List<AttendanceSubjectListModel> getAttendanceSubjects(int schoolYearID, int grade, Date fromDate,Date toDate) {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        List<AttendanceSubjectListModel> list = data.getAllAttendanceSubjects(schoolYearID,grade,fromDate,toDate);
        data.close();
        return  list;
    }

    @Override
    public List<AttendanceStudentListModel> getAttendanceStudents(int classSectionID, int subjectID, Date date) {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        List<AttendanceStudentListModel> list = data.getAttendanceStudents(classSectionID,subjectID,date);
        data.close();
        return  list;
    }

    @Override
    public void addNewAttendaceList(int classSectionID, int subjectID, Date date,int gradingPeriod, int schoolYearID) throws  Exception {

        if(classSectionID ==0){
            throw new Exception("Class section is required");
        }
        if(subjectID ==0){
            throw new Exception("Subject is required");
        }
        if(gradingPeriod ==0){
            throw new Exception("Grading Period is required");
        }
        if(schoolYearID ==0){
            throw new Exception("School Year is required");
        }


        Context context = GlobalApplication.getAppContext();
        AttendanceData attendanceData = AttendanceData.getInstance(context,null);
        StudentData studentData = StudentData.getInstance(context, null);
        studentData.open();
        attendanceData.open();

        List<StudentModel> studentModels = studentData.getAllBy(classSectionID);
        for (StudentModel studentModel: studentModels
             ) {
            attendanceData.deleteBy(subjectID,studentModel.getId(),date);
            AttendanceModel attendanceModel = new AttendanceModel();
            attendanceModel.setCreateTimeStamp(date);
            attendanceModel.setGradingPeriod(gradingPeriod);
            attendanceModel.setPresent(true);
            attendanceModel.setSchoolYearID(schoolYearID);
            attendanceModel.setStudentID(studentModel.getId());
            attendanceModel.setSubjectID(subjectID);
            attendanceData.add(attendanceModel);

        }
        List<AttendanceStudentListModel> list = attendanceData.getAttendanceStudents(classSectionID,subjectID,date);
        attendanceData.close();
        studentData.close();

    }

    @Override
    public void updateAttendancePresentStatus(int attendanceID, boolean isPresent) {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        data.updateAttendancePresentStatus(attendanceID,isPresent);
        data.close();
    }

    @Override
    public void updateAllAttendanceStatus(int classSectionID, int subjectID, Date date, boolean isPresent) {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        List<AttendanceStudentListModel> list = data.getAttendanceStudents(classSectionID,subjectID,date);
        for (AttendanceStudentListModel model : list
                ) {
            data.updateAttendancePresentStatus(model.getAttendanceID(),isPresent);
        }
        data.close();
    }

    @Override
    public void generateTardinessSMS(int schoolYearID, int gradingPeriod, int minAbsents, int minLate) {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        SmsData smsData = SmsData.getInstance(context,null);
        data.open();
        smsData.open();
        List<AttendanceAbsentListModel> list = data.getAttendanceAbsents(schoolYearID,gradingPeriod);
        for (AttendanceAbsentListModel model: list
             ) {
            /** divide the number of absents to minimum absents required
             * if result is whole number, means that absents reach the minimum absents required then
             * create new notif message to contact person of student*/
            int eSmsCount = model.getAbsents()/minAbsents; // expected sms count
            if(eSmsCount > 0){
                /** get sms that has been sent to the contact person */
                int smsSentCount = smsData.getSmsCount(schoolYearID,gradingPeriod,model.getStudentID());
                /**if expected sms count greater than retrieved sms sent count, send new one */
                if(eSmsCount > smsSentCount){
                    SmsModel smsModel = new SmsModel();
                    smsModel.setDate(new Date());
                    smsModel.setSend(false);
                    smsModel.setGradingPeriod(gradingPeriod);
                    smsModel.setSchoolYearID(schoolYearID);
                    smsModel.setMessage("You need to report your  \n" + model.getSubjectTitle() + " for having \n " + minAbsents + " absences.");
                    smsModel.setStudentID(model.getStudentID());
                    smsData.add(smsModel);
                }
            }
        }
        data.close();
        smsData.close();
    }

    @Override
    public void delete(int classSectionID, int subjectID, Date date) {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        List<AttendanceStudentListModel> list = data.getAttendanceStudents(classSectionID,subjectID,date);
        for (AttendanceStudentListModel model : list
                ) {
            data.delete(model.getAttendanceID());
        }
        data.close();
    }

    @Override
    public Integer getTodayAbsentCount() {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        List<AttendanceStudentListModel> list = data.getAttendanceAbsents(new Date());
        data.close();
        return list.size();
    }

    @Override
    public Integer getWeekAbsentCount() {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,  Calendar.MONDAY-calendar.get(Calendar.DAY_OF_WEEK));

        int totalCount = 0;
        for (int i = 0; i < 5; i++) {
            if (i > 0){
                calendar.add(Calendar.DATE,1);
            }
            Date d = calendar.getTime();
            totalCount = totalCount + data.getAttendanceAbsents(d).size();

        }
        data.close();
        return totalCount;
    }

    @Override
    public Integer getMonthAbsentCount() {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        Date dateFrom = getFirstDateOfCurrentMonth();
        Date dateTo = getLastDateOfCurrentMonth();
        List<AttendanceStudentListModel> list = data.getAttendanceAbsents(dateFrom,dateTo);
        data.close();
        return list.size();
    }

    @Override
    public void updateAttendanceLateStatus(int attendanceID, boolean isLate) {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        data.updateAttendanceLateStatus(attendanceID,isLate);
        data.close();
    }

    @Override
    public List<PerfectAttendanceModel> getAllPerfectAttendance(int schoolYearID, Integer gradingPeriod, Integer classSectionID) {
        Context context = GlobalApplication.getAppContext();
        AttendanceData attendanceData = AttendanceData.getInstance(context,null);
        StudentData studentData = StudentData.getInstance(context,null);
        attendanceData.open();
        studentData.open();

        List<Integer> list = attendanceData.getAllAttendanceStudentID(schoolYearID,gradingPeriod,classSectionID);
        ArrayList<PerfectAttendanceModel> perfectAttendanceModels = new ArrayList<>();
        for (Integer studentID:list
             ) {
            AttendanceTardinessModel attendanceTardinessModel = attendanceData.getAttendanceTardiness(studentID,schoolYearID,gradingPeriod);
            /** Check student if has late */
            if(attendanceTardinessModel.getAbsentCount() == 0){
                StudentModel studentModel = studentData.get(studentID);
                PerfectAttendanceModel perfectAttendanceModel = new PerfectAttendanceModel();
                perfectAttendanceModel.setFullName(studentModel.getFullName());
                perfectAttendanceModels.add(perfectAttendanceModel);
            }
        }
        attendanceData.close();
        studentData.close();
        return  perfectAttendanceModels;
    }

    @Override
    public List<AttendanceTardinessModel> getAllAttendanceTardiness(Integer schoolYearID, Integer gradingPeriod) {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        List<AttendanceTardinessModel> list = data.getAllAttendanceTardiness(schoolYearID,gradingPeriod);
        data.close();
        return  list;
    }

    @Override
    public AttendanceTodayModel getAttendanceToday(Integer studentID, Date dateToday) {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        AttendanceTodayModel model = data.getAttendanceToday(studentID,dateToday);
        data.close();
        return  model;
    }

    @Override
    public Integer getTotalSubjectToday(Integer classSectionID, Date dateToday){
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        Integer totalSubject = data.getTotalSubjectToday(classSectionID,dateToday);
        data.close();
        return  totalSubject;
    }

    @Override
    public AttendanceTardinessModel getAttendanceTardiness(int studentID, int schoolYearID, int gradingPeriod) {
        Context context = GlobalApplication.getAppContext();
        AttendanceData data = AttendanceData.getInstance(context,null);
        data.open();
        AttendanceTardinessModel model = data.getAttendanceTardiness(studentID,schoolYearID,gradingPeriod);
        data.close();
        return  model;
    }

    private Date getFirstDateOfCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
    private Date getLastDateOfCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }


}
