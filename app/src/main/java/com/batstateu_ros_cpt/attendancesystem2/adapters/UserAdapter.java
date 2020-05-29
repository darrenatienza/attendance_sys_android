package com.batstateu_ros_cpt.attendancesystem2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.mikepenz.fastadapter.items.AbstractItem;


public class UserAdapter extends AbstractItem<UserAdapter, UserAdapter.ViewHolder>  {

   int userID;
    String userName;
    String fullName;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    // Your Getter Setters here

    // Fast Adapter methods
    @Override
    public int getType() { return R.id.item_users; }

    @Override
    public int getLayoutRes() { return R.layout.item_users; }

    @Override
    public void bindView(ViewHolder holder) {
        super.bindView(holder);
        holder.fullName.setText(fullName);
        holder.userName.setText(userName);
    }



    // Manually create the ViewHolder class
    protected static class ViewHolder extends RecyclerView.ViewHolder {

        TextView fullName;
        TextView userName;
        //TODO: Declare your UI widgets here

        public ViewHolder(View itemView) {
            super(itemView);
            //TODO: init UI
            fullName = itemView.findViewById(R.id.tvFullName);
            userName = itemView.findViewById(R.id.tvUserName);

        }
    }
}



