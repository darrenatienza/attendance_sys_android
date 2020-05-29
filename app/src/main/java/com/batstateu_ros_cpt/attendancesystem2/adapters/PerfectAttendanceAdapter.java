package com.batstateu_ros_cpt.attendancesystem2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.mikepenz.fastadapter.items.AbstractItem;


public class PerfectAttendanceAdapter extends AbstractItem<PerfectAttendanceAdapter, PerfectAttendanceAdapter.ViewHolder>  {


    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Fast Adapter methods
    @Override
    public int getType() { return R.id.item_perfect_attendance; }

    @Override
    public int getLayoutRes() { return R.layout.item_perfect_attendance; }

    @Override
    public void bindView(ViewHolder holder) {
        super.bindView(holder);
        holder.tvName.setText(name);
    }



    // Manually create the ViewHolder class
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        //TODO: Declare your UI widgets here

        public ViewHolder(final View itemView) {
            super(itemView);
            //TODO: init UI
            tvName = itemView.findViewById(R.id.tvName);


        }
    }
}



