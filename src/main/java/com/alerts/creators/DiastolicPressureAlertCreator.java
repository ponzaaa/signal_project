package com.alerts.creators;

import com.alerts.objects.Alert;
import com.alerts.objects.DiastolicPressureAlert;

import java.util.Random;

public class DiastolicPressureAlertCreator extends AlertFactory {
    @Override
    public Alert creatAlert(int patientID, String condition, long timestamp) {
        Random random = new Random();
        System.out.println("ALERT for " + patientID+ " at " + timestamp + ": " + condition);
        return new DiastolicPressureAlert(random.nextInt(500000000), patientID, condition, timestamp);
    }
}
