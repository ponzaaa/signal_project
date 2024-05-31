package com.alerts;

import com.alerts.creators.AlertFactory;
import com.alerts.objects.Alert;
import com.data_management.records.Patient;
import java.util.Map;

public class HeartRateStrategy implements AlertStrategy {
    @Override
    public Alert checkAlert(int patientId, double data, long timestamp, Map<Integer, Patient> patientMap) {
        Alert alert = null;
        AlertFactory alertCreator = new AlertFactory();
        // check heart rate
        if (data>100){
            alert = alertCreator.creatAlert(patientId, "Heart Rate above 100 bpm", timestamp);
        } else if (data<50){
            alert = alertCreator.creatAlert(patientId, "Heart Rate below 50 bpm", timestamp);
        }
        return alert;
    }

}
