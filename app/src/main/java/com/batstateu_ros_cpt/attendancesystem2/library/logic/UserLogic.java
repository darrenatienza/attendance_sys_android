package com.batstateu_ros_cpt.attendancesystem2.library.logic;

import android.content.Context;

import com.batstateu_ros_cpt.attendancesystem2.library.GlobalApplication;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IBase;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.ISubject;
import com.batstateu_ros_cpt.attendancesystem2.library.contracts.IUser;
import com.batstateu_ros_cpt.attendancesystem2.library.data.SubjectData;
import com.batstateu_ros_cpt.attendancesystem2.library.data.UserData;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SubjectModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.user.UserModel;

import java.util.List;

public class UserLogic implements IBase<UserModel>, IUser {

    @Override
    public List<UserModel> getAll() {
        Context context = GlobalApplication.getAppContext();
        UserData data = UserData.getInstance(context,null);
        data.open();
        List<UserModel> list = data.getAll();
        data.close();
        return  list;
    }

    @Override
    public List<UserModel> getAll(String criteria) {
        Context context = GlobalApplication.getAppContext();
        UserData data = UserData.getInstance(context,null);
        data.open();
        List<UserModel> list = data.getAll(criteria);
        data.close();
        return  list;
    }

    @Override
    public UserModel get(Integer id) {
        Context context = GlobalApplication.getAppContext();
        UserData data = UserData.getInstance(context,null);
        data.open();
        UserModel model = data.get(id);
        data.close();
        return  model;
    }


    @Override
    public void add(UserModel model) {
        Context context = GlobalApplication.getAppContext();
        UserData data = UserData.getInstance(context,null);
        data.open();
        data.add(model);
        data.close();
    }

    @Override
    public void edit(Integer id, UserModel model) {
        Context context = GlobalApplication.getAppContext();
        UserData data = UserData.getInstance(context,null);
        data.open();
        data.edit(id,model);
        data.close();
    }

    @Override
    public void delete(Integer id) {
        Context context = GlobalApplication.getAppContext();
        UserData data = UserData.getInstance(context,null);
        data.open();
        data.delete(id);
        data.close();
    }


    @Override
    public UserModel get(String userName, String password) {
        Context context = GlobalApplication.getAppContext();
        UserData data = UserData.getInstance(context,null);
        data.open();
        UserModel model = data.get(userName,password);
        data.close();
        return model;
    }
}
