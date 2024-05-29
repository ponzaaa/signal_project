package com.alerts.creators;

import com.alerts.objects.Alert;
import com.alerts.objects.SystolicPressureAlert;

import java.util.Random;

public class SystolicPressureAlertCreator extends AlertFactory {
    @Override
    public Alert creatAlert(int patientID, String condition, long timestamp) {
        Random rand = new Random();
        System.out.println("ALERT for " + patientID+ " at " + timestamp + ": " + condition);
        return new SystolicPressureAlert(rand.nextInt(500000000), patientID, condition, timestamp);
    }
}
