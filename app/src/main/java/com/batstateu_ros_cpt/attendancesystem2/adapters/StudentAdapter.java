package com.batstateu_ros_cpt.attendancesystem2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.batstateu_ros_cpt.attendancesystem2.R;

import com.mikepenz.fastadapter.items.AbstractItem;



public class StudentAdapter extends AbstractItem<StudentAdapter, StudentAdapter.ViewHolder>  {

    public Integer getItemID() {
        return itemID;
    }

    public void setItemID(Integer itemID) {
        this.itemID = itemID;
    }

    private Integer itemID;

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    private String fullName;

    public void setSchoolDetail(String schoolDetail) {
        this.schoolDetail = schoolDetail;
    }

    public String getSchoolDetail() {
        return schoolDetail;
    }

    private String schoolDetail;




    // Your Getter Setters here

    // Fast Adapter methods
    @Override
    public int getType() { return R.id.student_item; }

    @Override
    public int getLayoutRes() { return R.layout.student_item; }

    @Override
    public void bindView(ViewHolder holder) {
        super.bindView(holder);
        holder.fullName.setText(fullName);
        holder.schoolDetail.setText(schoolDetail);
        holder.itemID.setText(itemID.toString());
    }



    // Manually create the ViewHolder class
    protected static class ViewHolder extends RecyclerView.ViewHolder {

        TextView fullName;
        TextView schoolDetail;
        TextView itemID;
        //TODO: Declare your UI widgets here

        public ViewHolder(View itemView) {
            super(itemView);
            //TODO: init UI
            fullName = itemView.findViewById(R.id.fullName);
            schoolDetail = itemView.findViewById(R.id.schoolDetail);
            itemID = itemView.findViewById(R.id.itemID);

        }
    }
}



