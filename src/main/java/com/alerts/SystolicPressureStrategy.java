package com.alerts;

import com.alerts.creators.HypotensiveHypoxemiaAlertCreator;
import com.alerts.creators.SystolicPressureAlertCreator;
import com.alerts.objects.Alert;
import com.data_management.records.Patient;

import java.util.Map;

public class SystolicPressureStrategy extends BloodPressureStrategy {
    @Override
    public Alert checkAlert(int patientId, double data, long timestamp, Map<Integer, Patient> patientMap) {
        SystolicPressureAlertCreator alertCreator = new SystolicPressureAlertCreator();
        HypotensiveHypoxemiaAlertCreator alertCreator2 = new HypotensiveHypoxemiaAlertCreator();
        Alert alert =  null;
        // check values are in range
        if (data>180){
            alert = alertCreator.creatAlert(patientId, "Systolic Pressure exceeding 180 mmHg", timestamp);
        } else if (data<90) {
            // check for hypotensive hypoxemia
            if (patientMap.get(patientId).getRecordAtTime("Saturation", timestamp)
                    .getMeasurementValue()<92){
                alert = alertCreator2.creatAlert(patientId, "Blood Saturation below 92% and Systolic " +
                        "Pressure under 90 mmHg",timestamp);
            }
        } return alert;
    }
}
