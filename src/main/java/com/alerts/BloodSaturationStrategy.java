package com.alerts;

import com.alerts.creators.AlertFactory;
import com.alerts.objects.Alert;
import com.data_management.records.Patient;

import java.util.Map;

/**
 * Strategy implementation for the alert system that checks for blood saturation levels
 * in the bloodstream
 */
public class BloodSaturationStrategy implements AlertStrategy {

    /**
     * Guidelines for alert system that checks blood saturation levels in the bloodstream
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
        Alert alert = null;
        AlertFactory alertCreator = new AlertFactory();
        // check low values
        if (data<92){
            alert = alertCreator.creatAlert(patientId, "Blood Saturation below 92%",timestamp);
            // check hypotensive hypoxemia
            try {
                if (patientMap.get(patientId).getRecordAtTime("Systolic Pressure", timestamp)
                        .getMeasurementValue() < 90) {
                    alert = alertCreator.creatAlert(patientId, "Blood Saturation below 92% and Systolic " +
                            "Pressure under 90 mmHg", timestamp);
                }
            } catch (Exception ignored) {
            }
        }

        // check rapid drop (drops by 5% from one measurement to the other)
        double previousMeasurement = patientMap.get(patientId).getPatientRecords().get("Saturation")
                .get(patientMap.get(patientId).getPatientRecords().get("Saturation").size()-1).getMeasurementValue();

        if (previousMeasurement-data>previousMeasurement*0.05){
            alertCreator.creatAlert(patientId, "Blood Saturation dropped by more than 5%",timestamp);
        }
    return alert;
    }
}

