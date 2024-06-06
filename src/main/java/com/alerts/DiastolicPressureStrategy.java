package com.alerts;

import com.alerts.creators.AlertFactory;
import com.alerts.objects.Alert;
import com.data_management.records.Patient;

import java.util.Map;

/**
 * Strategy implementation for the alert system that checks for diastolic blood pressure
 */
public class DiastolicPressureStrategy extends BloodPressureStrategy implements AlertStrategy {

    /**
     * Guidelines for alert system that checks blood diastolic pressure as well as
     * a combination of that and low systolic pressure, which would then trigger the Hypotensive
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
