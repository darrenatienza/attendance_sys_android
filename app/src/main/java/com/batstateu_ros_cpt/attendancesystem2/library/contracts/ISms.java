package com.batstateu_ros_cpt.attendancesystem2.library.contracts;


import com.batstateu_ros_cpt.attendancesystem2.library.models.SmsModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.SubjectModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.sms.SmsDetailModel;
import com.batstateu_ros_cpt.attendancesystem2.library.models.sms.SmsListModel;

import java.util.List;

public interface ISms extends IBase<SmsModel> {


    List<SmsListModel> getSmsList(int schoolYearID, int gradingPeriod);

    SmsDetailModel getSmsDetail(int id);

    Integer getUnsentSMSCount();

    void SendSMS(int id);

    Integer getSentSMSCount();
}
