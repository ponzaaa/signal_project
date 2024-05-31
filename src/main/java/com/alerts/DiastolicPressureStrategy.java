package com.alerts;

import com.alerts.creators.AlertFactory;
import com.alerts.objects.Alert;
import com.data_management.records.Patient;

import java.util.Map;

public class DiastolicPressureStrategy extends BloodPressureStrategy {
    @Override
    public Alert checkAlert(int patientId, double data, long timestamp, Map<Integer, Patient> patientMap) {
        AlertFactory alertfactory = new AlertFactory();
        Alert alert = null;
        // check values are in range
        if (data > 120) {
            alert = alertfactory.creatAlert(patientId, "Diastolic Pressure above 120 mmHg", timestamp);
        } else if (data < 60) {
            alert = alertfactory.creatAlert(patientId, "Diastolic Pressure below 60 mmHg", timestamp);
        }
        return alert;
    }
}
