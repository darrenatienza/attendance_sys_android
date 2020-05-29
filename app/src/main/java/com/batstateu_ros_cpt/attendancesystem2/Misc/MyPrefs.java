package com.batstateu_ros_cpt.attendancesystem2.Misc;

import com.batstateu_ros_cpt.attendancesystem2.R;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(value = SharedPref.Scope.UNIQUE)
public interface MyPrefs {

    @DefaultInt(1)
    int schoolYearID();

    @DefaultInt(1)
    int gradingPeriod();

    @DefaultInt(3)
    int minAbsentCount();

    @DefaultInt(3)
    int minLateCount();

    @DefaultInt(0)
    int classSectionID();

    @DefaultInt(0)
    int subjectID();

    String userFullName();

}
