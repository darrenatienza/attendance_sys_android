package com.batstateu_ros_cpt.attendancesystem2.library.contracts;


import com.batstateu_ros_cpt.attendancesystem2.library.models.SubjectModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.user.UserModel;

public interface IUser extends IBase<UserModel> {


    UserModel get(String userName, String password);
}
