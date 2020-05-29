package com.batstateu_ros_cpt.attendancesystem2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.mikepenz.fastadapter.items.AbstractItem;


public class SubjectEnrolleList extends AbstractItem<SubjectEnrolleList, SubjectEnrolleList.ViewHolder>  {

    String itemID;
    String fullName;
    boolean isEnrolled;
    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }





    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isEnrolled() {
        return isEnrolled;
    }

    public void setEnrolled(boolean enrolled) {
        isEnrolled = enrolled;
    }


    // Your Getter Setters here

    // Fast Adapter methods
    @Override
    public int getType() { return R.id.subject_enrolle_item; }

    @Override
    public int getLayoutRes() { return R.layout.subject_enrolle_item; }

    @Override
    public void bindView(ViewHolder holder) {
        super.bindView(holder);
        holder.fullName.setText(fullName);
        holder.chkEnroll.setChecked(isEnrolled);
        holder.itemID.setText(itemID);
    }



    // Manually create the ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView fullName;
        public CheckBox chkEnroll;
        TextView itemID;
        //TODO: Declare your UI widgets here

        public ViewHolder(final View itemView) {
            super(itemView);
            //TODO: init UI
            fullName = itemView.findViewById(R.id.fullName);
            chkEnroll = itemView.findViewById(R.id.chkEnroll);
            itemID = itemView.findViewById(R.id.itemID);

        }
    }
}



