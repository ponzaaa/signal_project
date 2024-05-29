package com.alerts;

import com.alerts.creators.ECGAlertCreator;
import com.data_management.records.Patient;
import com.data_management.records.PatientRecord;

import java.util.ArrayList;
import java.util.Map;

public class HeartRateStrategy implements AlertStrategy {
    @Override
    public void checkAlert(int patientId, double data, long timestamp, Map<Integer, Patient> patientMap) {

        ECGAlertCreator ecgAlertCreator = new ECGAlertCreator();
        // check heart rate
        if (data>100){
            ecgAlertCreator.creatAlert(patientId, "Heart Rate above 100 bpm", timestamp);
        } else if (data<50){
            ecgAlertCreator.creatAlert(patientId, "Heart Rate below 50 bpm", timestamp);
        }

        // TODO check irregular heart beat

    }

}
