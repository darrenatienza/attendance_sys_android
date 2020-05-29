package com.batstateu_ros_cpt.attendancesystem2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.mikepenz.fastadapter.items.AbstractItem;


public class SchoolYearAdapter extends AbstractItem<SchoolYearAdapter, SchoolYearAdapter.ViewHolder>  {

   int schoolYearID;
    String name;
    boolean isActive;

    public int getSchoolYearID() {
        return schoolYearID;
    }

    public void setSchoolYearID(int schoolYearID) {
        this.schoolYearID = schoolYearID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // Your Getter Setters here

    // Fast Adapter methods
    @Override
    public int getType() { return R.id.item_school_year; }

    @Override
    public int getLayoutRes() { return R.layout.item_school_year; }

    @Override
    public void bindView(ViewHolder holder) {
        super.bindView(holder);
        holder.tvName.setText(name);
    }



    // Manually create the ViewHolder class
    protected static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        //TODO: Declare your UI widgets here

        public ViewHolder(View itemView) {
            super(itemView);
            //TODO: init UI
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}



