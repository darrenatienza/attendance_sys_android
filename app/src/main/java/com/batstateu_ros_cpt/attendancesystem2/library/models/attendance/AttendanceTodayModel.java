package com.batstateu_ros_cpt.attendancesystem2.library.models.attendance;

public class AttendanceTodayModel {

    int presentCount;
    int lateCount;

    public int getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(int presentCount) {
        this.presentCount = presentCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public void setLateCount(int lateCount) {
        this.lateCount = lateCount;
    }
}
