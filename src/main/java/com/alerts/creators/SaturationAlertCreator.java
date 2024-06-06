package com.alerts.creators;

import com.alerts.objects.Alert;
import com.alerts.objects.SaturationAlert;

import java.util.Random;

/**
 * Subclass of the {@link AlertFactory} which returns the specific alert for
 * Blood Saturation data being too low (under 92%)
 */
public class SaturationAlertCreator extends AlertFactory {

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
        return new SaturationAlert(rand.nextInt(500000000), patientID, condition, timestamp);
    }
}
