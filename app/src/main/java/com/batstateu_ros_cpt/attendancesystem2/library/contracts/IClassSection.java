package com.batstateu_ros_cpt.attendancesystem2.library.contracts;

import com.batstateu_ros_cpt.attendancesystem2.library.models.ClassSectionModel;

import java.util.List;

public interface IClassSection extends IBase<ClassSectionModel> {

    int calculateTotalStudent();
}
