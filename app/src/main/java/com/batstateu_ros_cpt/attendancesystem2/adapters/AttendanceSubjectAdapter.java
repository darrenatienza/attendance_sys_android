package com.batstateu_ros_cpt.attendancesystem2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.github.thunder413.datetimeutils.DateTimeStyle;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.mikepenz.fastadapter.items.AbstractItem;


public class AttendanceSubjectAdapter extends AbstractItem<AttendanceSubjectAdapter, AttendanceSubjectAdapter.ViewHolder>  {


    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getClassSectionID() {
        return classSectionID;
    }

    public void setClassSectionID(String classSectionID) {
        this.classSectionID = classSectionID;
    }

    public String getClassSectionName() {
        return classSectionName;
    }

    public void setClassSectionName(String classSectionName) {
        this.classSectionName = classSectionName;
    }

    public String getPresentCount() {
        return presentCount;
    }

    public void setPresentCount(String presentCount) {
        this.presentCount = presentCount;
    }

    public String getAbsentCount() {
        return absentCount;
    }

    public void setAbsentCount(String absentCount) {
        this.absentCount = absentCount;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLateCount() {
        return lateCount;
    }

    public void setLateCount(int lateCount) {
        this.lateCount = lateCount;
    }

    String subjectID;
    String classSectionID;
    String classSectionName;
    String presentCount;
    String absentCount;
    String subjectName;
    String date;
    int lateCount;
    // Fast Adapter methods
    @Override
    public int getType() { return R.id.item_attendance_subject; }

    @Override
    public int getLayoutRes() { return R.layout.item_attendance_subject; }

    @Override
    public void bindView(ViewHolder holder) {
        super.bindView(holder);
        holder.tvAbsentCount.setText("Absent: " + absentCount);
        holder.tvClassSectionName.setText(classSectionName);
        holder.tvPresentCount.setText("Present: " +presentCount);
        holder.tvSubject.setText(subjectName);
        holder.tvDate.setText(DateTimeUtils.formatWithStyle(date, DateTimeStyle.SHORT));
        holder.tvLateCount.setText("Late: " +String.valueOf(lateCount));
    }



    // Manually create the ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvClassSectionName;
        TextView tvSubject;
        TextView tvPresentCount;
        TextView tvAbsentCount;
        TextView tvDate;
        TextView tvLateCount;
        //TODO: Declare your UI widgets here

        public ViewHolder(final View itemView) {
            super(itemView);
            //TODO: init UI
            tvClassSectionName = itemView.findViewById(R.id.tvClassSectionName);
            tvSubject = itemView.findViewById(R.id.tvSubject);
            tvPresentCount = itemView.findViewById(R.id.tvPresentCount);
            tvAbsentCount = itemView.findViewById(R.id.tvAbsentCount);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvLateCount = itemView.findViewById(R.id.tvLateCount);

        }
    }
}



