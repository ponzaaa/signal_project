package com.alerts;

import com.alerts.creators.AlertFactory;
import com.alerts.objects.Alert;
import com.data_management.records.Patient;

import java.util.Map;

/**
 * Strategy implementation for the alert system that checks for systolic blood pressure
 */
public class SystolicPressureStrategy extends BloodPressureStrategy {

    /**
     * Guidelines for alert system that checks blood systolic pressure as well as
     * a combination of that and low saturation, which would then trigger the Hypotensive
     * Hypoxemia alert
     * @param patientId unique identifier of each patient
     * @param data value of the record at given timestamp for blood saturation
     * @param timestamp time component of the recorded value
     * @param patientMap database containing previous data for the same patient used
     *                   to check for increasing or decreasing patterns, irregularity as
     *                   well as rapid drops
     * @return {@link Alert} object through the factory
     */
    @Override
    public Alert checkAlert(int patientId, double data, long timestamp, Map<Integer, Patient> patientMap) {
        AlertFactory alertCreator = new AlertFactory();
        Alert alert =  null;
        // check values are in range
        if (data>180){
            alert = alertCreator.creatAlert(patientId, "Systolic Pressure exceeding 180 mmHg", timestamp);
        } else if (data<90) {
            // check for hypotensive hypoxemia
            if (patientMap.get(patientId).getRecordAtTime("Saturation", timestamp)
                    .getMeasurementValue()!=0&&patientMap.get(patientId).getRecordAtTime("Saturation", timestamp)
                    .getMeasurementValue()<92){
                alert = alertCreator.creatAlert(patientId, "Blood Saturation below 92% and Systolic " +
                        "Pressure under 90 mmHg",timestamp);
            }
        } return alert;
    }
}
