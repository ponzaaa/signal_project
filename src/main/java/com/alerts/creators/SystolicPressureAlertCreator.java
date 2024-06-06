package com.alerts.creators;

import com.alerts.objects.Alert;
import com.alerts.objects.SystolicPressureAlert;

import java.util.Random;

/**
 * Subclass of the {@link AlertFactory} which returns the specific alert for Systolic
 * Pressure being too high or too low
 */
public class SystolicPressureAlertCreator extends AlertFactory {

    /**
     * Method that creates the specific alert containing the following parameters
     * @param patientID unique identifier of the patient
     * @param condition problem that the alert wants to signal
     * @param timestamp time of the recorded dangerous value
     * @return {@link Alert} object specific to the condition
     */
    @Override
    public Alert creatAlert(int patientID, String condition, long timestamp) {
        Random rand = new Random();
        System.out.println("ALERT for PatientID: " + patientID+ " at " + timestamp + ": " + condition);
        return new SystolicPressureAlert(rand.nextInt(500000000), patientID, condition, timestamp);
    }
}
