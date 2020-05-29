package com.batstateu_ros_cpt.attendancesystem2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.mikepenz.fastadapter.items.AbstractItem;

import org.w3c.dom.Text;


public class ClassSectionAdapter extends AbstractItem<ClassSectionAdapter, ClassSectionAdapter.ViewHolder>  {

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    String itemID;
    String name;
    String grade;
    String schoolYear;
    // Fast Adapter methods
    @Override
    public int getType() { return R.id.item_class_section; }

    @Override
    public int getLayoutRes() { return R.layout.item_class_section; }

    @Override
    public void bindView(ViewHolder holder) {
        super.bindView(holder);
        holder.itemID.setText(itemID);
        holder.name.setText(name);
        holder.grade.setText(grade);
        holder.schoolYear.setText(schoolYear);
    }



    // Manually create the ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemID;
        TextView name;
        TextView schoolYear;
        TextView grade;
        //TODO: Declare your UI widgets here

        public ViewHolder(final View itemView) {
            super(itemView);
            //TODO: init UI
            name = itemView.findViewById(R.id.name);
            schoolYear = itemView.findViewById(R.id.tvSchoolYear);
            grade = itemView.findViewById(R.id.grade);
            itemID = itemView.findViewById(R.id.itemID);

        }
    }
}



