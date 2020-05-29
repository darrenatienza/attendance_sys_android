package com.batstateu_ros_cpt.attendancesystem2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.mikepenz.fastadapter.items.AbstractItem;


public class SubjectAdapter extends AbstractItem<SubjectAdapter, SubjectAdapter.ViewHolder>  {

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    String itemID;
   String title;
   String grade;




    // Your Getter Setters here

    // Fast Adapter methods
    @Override
    public int getType() { return R.id.subject_item; }

    @Override
    public int getLayoutRes() { return R.layout.subject_item; }

    @Override
    public void bindView(ViewHolder holder) {
        super.bindView(holder);
        holder.title.setText(title);
        holder.grade.setText("Grade: " + grade);
        holder.itemID.setText(itemID);
    }



    // Manually create the ViewHolder class
    protected static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView grade;
        TextView itemID;
        //TODO: Declare your UI widgets here

        public ViewHolder(View itemView) {
            super(itemView);
            //TODO: init UI
            title = itemView.findViewById(R.id.title);
            grade = itemView.findViewById(R.id.grade);
            itemID = itemView.findViewById(R.id.itemID);

        }
    }
}



