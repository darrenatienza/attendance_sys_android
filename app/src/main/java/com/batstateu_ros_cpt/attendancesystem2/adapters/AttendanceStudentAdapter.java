package com.batstateu_ros_cpt.attendancesystem2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.github.thunder413.datetimeutils.DateTimeStyle;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.mikepenz.fastadapter.items.AbstractItem;


public class AttendanceStudentAdapter extends AbstractItem<AttendanceStudentAdapter, AttendanceStudentAdapter.ViewHolder>  {
    String attendanceID;
    String studentID;
    boolean isPresent;
    boolean isLate;
    String studentName;

    public boolean isLate() {
        return isLate;
    }

    public void setLate(boolean late) {
        isLate = late;
    }

    public String getAttendanceID() {
        return attendanceID;
    }

    public void setAttendanceID(String attendanceID) {
        this.attendanceID = attendanceID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public void setPresent(boolean present) {
        isPresent = present;
    }

    // Fast Adapter methods
    @Override
    public int getType() { return R.id.item_attendance_student; }

    @Override
    public int getLayoutRes() { return R.layout.item_attendance_student; }

    @Override
    public void bindView(ViewHolder holder) {
        super.bindView(holder);
        holder.tvStudentName.setText(studentName);
        holder.chkIsPresent.setChecked(isPresent());
        holder.chkIsLate.setChecked(isLate());
    }



    // Manually create the ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName;
        public CheckBox chkIsPresent;
        public CheckBox chkIsLate;
        //TODO: Declare your UI widgets here

        public ViewHolder(final View itemView) {
            super(itemView);
            //TODO: init UI
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            chkIsPresent = itemView.findViewById(R.id.chkIsPresent);
            chkIsLate = itemView.findViewById(R.id.chkIsLate);

        }
    }
}



