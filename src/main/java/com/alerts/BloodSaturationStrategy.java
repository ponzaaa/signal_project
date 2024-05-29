package com.alerts;

import com.alerts.creators.HypotensiveHypoxemiaAlertCreator;
import com.alerts.creators.SaturationAlertCreator;
import com.data_management.records.Patient;

import java.util.Map;

public class BloodSaturationStrategy implements AlertStrategy {
    @Override
    public void checkAlert(int patientId, double data, long timestamp, Map<Integer, Patient> patientMap) {

        SaturationAlertCreator alertCreator = new SaturationAlertCreator();
        HypotensiveHypoxemiaAlertCreator alertCreator2 = new HypotensiveHypoxemiaAlertCreator();
        // check low values
        if (data<92){
            alertCreator.creatAlert(patientId, "Blood Saturation below 92%",timestamp);
            // check hypotensive hypoxemia
            if (patientMap.get(patientId).getRecordAtTime("Systolic Pressure", timestamp)
                    .getMeasurementValue()<90){
                alertCreator2.creatAlert(patientId, "Blood Saturation below 92% and Systolic " +
                        "Pressure under 90 mmHg",timestamp);
            }
        }

        // check rapid drop (drops by 5% from one measurement to the other)
        double previousMeasurement = patientMap.get(patientId).getPatientRecords().get("Saturation")
                .get(patientMap.get(patientId).getPatientRecords().get("Saturation").size()-1).getMeasurementValue();

        if (previousMeasurement-data>previousMeasurement*0.05){
            alertCreator.creatAlert(patientId, "Blood Saturation dropped by more than 5%",timestamp);
        }

    }
}

