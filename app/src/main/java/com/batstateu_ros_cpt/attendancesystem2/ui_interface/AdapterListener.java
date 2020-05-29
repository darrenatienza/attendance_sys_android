package com.batstateu_ros_cpt.attendancesystem2.ui_interface;

import com.batstateu_ros_cpt.attendancesystem2.library.models.StudentModel;

public interface AdapterListener<T> {
    void onStudentSelected(T model);

}
