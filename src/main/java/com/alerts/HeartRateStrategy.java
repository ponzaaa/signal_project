package com.alerts;

import com.alerts.creators.AlertFactory;
import com.alerts.objects.Alert;
import com.data_management.records.Patient;
import java.util.Map;

/**
 * Strategy implementation for the alert system that checks for ECG heart rate data
 */
public class HeartRateStrategy implements AlertStrategy {

    /**
     * Guidelines for alert system that checks ECG heart rate data and trends
     * @param patientId unique identifier of each patient
     * @param data value of the record at given timestamp for blood saturation
     * @param timestamp time component of the recorded value
     * @param patientMap database containing previous data for the same patient used
     *                   to check for irregularity as well as rapid drops
     * @return {@link Alert} object through the factory
     */
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
