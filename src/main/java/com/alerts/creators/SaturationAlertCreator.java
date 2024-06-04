package com.alerts.creators;

import com.alerts.objects.Alert;
import com.alerts.objects.SaturationAlert;

import java.util.Random;

public class SaturationAlertCreator extends AlertFactory {
    @Override
    public Alert creatAlert(int patientID, String condition, long timestamp) {
        Random rand = new Random();
        System.out.println("ALERT for PatientID: " + patientID+ " at " + timestamp + ": " + condition);
        return new SaturationAlert(rand.nextInt(500000000), patientID, condition, timestamp);
    }
}
