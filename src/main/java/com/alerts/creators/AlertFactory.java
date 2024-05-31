package com.alerts.creators;

import com.alerts.objects.Alert;

public class AlertFactory {
    public Alert creatAlert(int patientID, String condition, long timestamp){

        // Blood Pressure (Diastolic)
        if (condition.equalsIgnoreCase("Diastolic Pressure above 120 mmHg")||
                condition.equalsIgnoreCase("Diastolic Pressure below 60 mmHg")){
            DiastolicPressureAlertCreator creator = new DiastolicPressureAlertCreator();
            return creator.creatAlert(patientID, condition, timestamp);

        } // Blood Pressure Systolic
        else if (condition.equalsIgnoreCase("Systolic Pressure exceeding 180 mmHg")||
        condition.equalsIgnoreCase("Systolic Pressure below 90 mmHg")) {
            SystolicPressureAlertCreator creator = new SystolicPressureAlertCreator();
            return creator.creatAlert(patientID, condition, timestamp);

        } // Blood Saturation
        else if (condition.equalsIgnoreCase("Blood Saturation below 92%")){
            SaturationAlertCreator creator = new SaturationAlertCreator();
            return creator.creatAlert(patientID, condition, timestamp);

        } // Hypotensive Hypoxemia
        else if (condition.equalsIgnoreCase("Blood Saturation below 92% and Systolic " +
                "Pressure under 90 mmHg")) {
            HypotensiveHypoxemiaAlertCreator creator = new HypotensiveHypoxemiaAlertCreator();
            return creator.creatAlert(patientID, condition, timestamp);

        } // Heart Rate
        else if (condition.equalsIgnoreCase("Heart Rate above 100 bpm")||
        condition.equalsIgnoreCase("Heart Rate below 50 bpm")) {
            ECGAlertCreator creator = new ECGAlertCreator();
            return creator.creatAlert(patientID, condition, timestamp);
        } else {
            throw new IllegalArgumentException("Invalid condition: " + condition);
        }
    }
}
