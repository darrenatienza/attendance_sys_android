package com.batstateu_ros_cpt.attendancesystem2.library.contracts;


import com.batstateu_ros_cpt.attendancesystem2.library.models.AttendanceModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.ClassSectionModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SubjectModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceAbsentListModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceStudentListModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceSubjectListModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceTardinessModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.AttendanceTodayModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.attendance.PerfectAttendanceModel;

import java.util.Date;
import java.util.List;

public interface IAttendance extends IBase<AttendanceModel> {


    List<AttendanceSubjectListModel> getAttendanceSubjects(int schoolYearID, int grade, Date fromDate, Date toDate);

    List<AttendanceStudentListModel> getAttendanceStudents(int classSectionID, int subjectID, Date date);

    void addNewAttendaceList(int classSectionID, int subjectID, Date date, int gradingPeriod, int schoolYearID) throws Exception;

    void updateAttendancePresentStatus(int attendanceID, boolean isPresent);

    void updateAllAttendanceStatus(int classSectionID, int subjectID, Date date, boolean isPresent);

    void generateTardinessSMS(int schoolYearID, int gradingPeriod, int minAbsent, int minLate);

    void delete(int classSectionID, int subjectID, Date date);

    Integer getTodayAbsentCount();

    Integer getWeekAbsentCount();

    Integer getMonthAbsentCount();

    void updateAttendanceLateStatus(int attendanceID, boolean isLate);

    List<PerfectAttendanceModel> getAllPerfectAttendance(int schoolYearID, Integer gradingPeriod, Integer classSectionID);

    List<AttendanceTardinessModel> getAllAttendanceTardiness(Integer schoolYearID, Integer gradingPeriod);

    AttendanceTodayModel getAttendanceToday(Integer studentID, Date dateToday);
    Integer getTotalSubjectToday(Integer classSectionID, Date dateToday);

    AttendanceTardinessModel getAttendanceTardiness(int studentID, int schoolYearID, int gradingPeriod);
}
