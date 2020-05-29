package com.batstateu_ros_cpt.attendancesystem2.adapters;

import android.content.Context;

import com.batstateu_ros_cpt.attendancesystem2.adapters.models.SpinnerModel;
import com.jaredrummler.materialspinner.MaterialSpinnerAdapter;

import java.util.List;


public class SpinnerAdapter extends MaterialSpinnerAdapter<SpinnerModel> {


    public SpinnerAdapter(Context context, List<SpinnerModel> items) {
        super(context, items);
    }



}
