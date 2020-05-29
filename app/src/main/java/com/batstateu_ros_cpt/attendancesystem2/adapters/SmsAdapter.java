package com.batstateu_ros_cpt.attendancesystem2.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.batstateu_ros_cpt.attendancesystem2.R;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.Date;


public class SmsAdapter extends AbstractItem<SmsAdapter, SmsAdapter.ViewHolder>  {
    int itemID;
    String recipient;
    Date date;
    boolean isSend;

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }


    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }
// Your Getter Setters here

    // Fast Adapter methods
    @Override
    public int getType() { return R.id.item_sms; }

    @Override
    public int getLayoutRes() { return R.layout.item_sms; }

    @Override
    public void bindView(ViewHolder holder) {
        super.bindView(holder);
        holder.tvDate.setText(DateTimeUtils.formatDate(date).substring(0,10));
        holder.tvRecipient.setText("To: " + recipient);
        holder.chkIsSend.setChecked(isSend);
    }



    // Manually create the ViewHolder class
    protected static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate;
        TextView tvRecipient;
        CheckBox chkIsSend;
        //TODO: Declare your UI widgets here

        public ViewHolder(View itemView) {
            super(itemView);
            //TODO: init UI
            tvDate = itemView.findViewById(R.id.tvDate);
            tvRecipient = itemView.findViewById(R.id.tvRecipient);
            chkIsSend = itemView.findViewById(R.id.chkIsSend);
        }
    }
}



