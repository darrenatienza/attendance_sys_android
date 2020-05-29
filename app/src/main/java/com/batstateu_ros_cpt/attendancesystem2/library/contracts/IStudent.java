package com.batstateu_ros_cpt.attendancesystem2.library.contracts;


import com.batstateu_ros_cpt.attendancesystem2.library.models.StudentModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SubjectModel;

import java.util.List;

public interface IStudent extends IBase<StudentModel> {


    List<StudentModel> getAllBySubjectID(int subjectID);

    List<StudentModel> getAllBy(int classSectionID);

    Integer getStudentCount();

    List<StudentModel> getAll(String selClassSection, String selSchoolYear);
}
