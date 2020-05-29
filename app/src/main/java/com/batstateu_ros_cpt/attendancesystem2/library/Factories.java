package com.batstateu_ros_cpt.attendancesystem2.library;

import android.content.SharedPreferences;

import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IAttendance;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IClassSection;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.ISchoolYear;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.ISms;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IStudent;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.ISubject;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IUser;
import com.batstateu_ros_cpt.attendancesystem2.library.logic.AttendanceLogic;
import com.batstateu_ros_cpt.attendancesystem2.library.logic.ClassSectionLogic;
import com.batstateu_ros_cpt.attendancesystem2.library.logic.SchoolYearLogic;
import com.batstateu_ros_cpt.attendancesystem2.library.logic.SmsLogic;
import com.batstateu_ros_cpt.attendancesystem2.library.logic.StudentLogic;
import com.batstateu_ros_cpt.attendancesystem2.library.logic.SubjectLogic;
import com.batstateu_ros_cpt.attendancesystem2.library.logic.UserLogic;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.concurrent.RecursiveTask;


public class Factories {

    public static IStudent createStudent(){
        return new StudentLogic();
    }

    public static ISubject createSubject() {
        return new SubjectLogic();
    }

    public static IClassSection createClassSection() {
        return new ClassSectionLogic();
    }

    public static IAttendance createAttendance() {
        return new AttendanceLogic();
    }

    public static ISchoolYear createSchoolYear() {
        return new SchoolYearLogic();
    }
    public static ISms createSms() {
        return new SmsLogic();
    }

    public static IUser createUser() {
        return new UserLogic();
    }
}
