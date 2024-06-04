package com.alerts.creators;

import com.alerts.objects.Alert;
import com.alerts.objects.ECGAlert;

import java.util.Random;

public class ECGAlertCreator extends AlertFactory {

    @Override
    public Alert creatAlert(int patientID, String condition, long timestamp) {
        Random rand = new Random();
        System.out.println("ALERT for Patient ID: " + patientID+ " at " + timestamp + ": " + condition);
        return new ECGAlert(rand.nextInt(500000000), patientID, condition, timestamp);
    }
}
