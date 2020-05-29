package com.batstateu_ros_cpt.attendancesystem2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.mikepenz.fastadapter.items.AbstractItem;


public class AttendanceTardinessAdapter extends AbstractItem<AttendanceTardinessAdapter, AttendanceTardinessAdapter.ViewHolder>  {

    int studentID;
    String studentName;
    String sectionName;
    int absentCount;
    int lateCount;
    int presentTodayCount;
    int lateTodayCount;
    private int subjectCount;

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(int absentCount) {
        this.absentCount = absentCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public void setLateCount(int lateCount) {
        this.lateCount = lateCount;
    }

    public int getPresentTodayCount() {
        return presentTodayCount;
    }

    public void setPresentTodayCount(int presentTodayCount) {
        this.presentTodayCount = presentTodayCount;
    }

    public int getLateTodayCount() {
        return lateTodayCount;
    }

    public void setLateTodayCount(int lateTodayCount) {
        this.lateTodayCount = lateTodayCount;
    }

    public int getSubjectCount() {
        return subjectCount;
    }

    public void setSubjectCount(int subjectCount) {
        this.subjectCount = subjectCount;
    }

    // Fast Adapter methods
    @Override
    public int getType() { return R.id.item_attendance_tardiness; }

    @Override
    public int getLayoutRes() { return R.layout.item_attendance_tardiness; }

    @Override
    public void bindView(ViewHolder holder) {
        super.bindView(holder);
        holder.tvStudentName.setText(studentName);
        holder.tvAbsentCount.setText("Total Absent: " + String.valueOf(absentCount));
        holder.tvLateCount.setText("Total Late: " + String.valueOf(lateCount));
        holder.tvClassSectionName.setText(sectionName);
        holder.tvLateCountToday.setText("Late on " +String.valueOf(lateCount) + " out of " + String.valueOf(subjectCount) + " Subjects");
        holder.tvPresentCountToday.setText("Present on " + String.valueOf(presentTodayCount) + " out of " + String.valueOf(subjectCount) + " Subjects");
    }



    // Manually create the ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName;
        TextView tvAbsentCount;
        TextView tvLateCount;
        TextView tvClassSectionName;
        TextView tvPresentCountToday;
        TextView tvLateCountToday;
        //TODO: Declare your UI widgets here

        public ViewHolder(final View itemView) {
            super(itemView);
            //TODO: init UI
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvAbsentCount = itemView.findViewById(R.id.tvAbsentCount);
            tvLateCount = itemView.findViewById(R.id.tvLateCount);
            tvClassSectionName = itemView.findViewById(R.id.tvClassSectionName);
            tvPresentCountToday = itemView.findViewById(R.id.tvPresentTodayCount);
            tvLateCountToday = itemView.findViewById(R.id.tvLateTodayCount);
        }
    }
}



