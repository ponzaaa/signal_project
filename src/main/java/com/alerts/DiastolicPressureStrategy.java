package com.alerts;

import com.alerts.creators.DiastolicPressureAlertCreator;
import com.data_management.records.Patient;

import java.util.Map;

public class DiastolicPressureStrategy extends BloodPressureStrategy {
    @Override
    public void checkAlert(int patientId, double data, long timestamp, Map<Integer, Patient> patientMap) {
        DiastolicPressureAlertCreator alertCreator = new DiastolicPressureAlertCreator();

        // check values are in range
        if (data > 120) {
            alertCreator.creatAlert(patientId, "Diastolic Pressure above 120 mmHg", timestamp);
        } else if (data < 60) {
            alertCreator.creatAlert(patientId, "Diastolic Pressure below 60 mmHg", timestamp);
        }
    }
}
