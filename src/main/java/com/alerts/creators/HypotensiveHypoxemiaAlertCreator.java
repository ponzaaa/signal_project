package com.alerts.creators;

import com.alerts.objects.Alert;
import com.alerts.objects.HypotensiveHypoxemiaAlert;

import java.util.Random;

public class HypotensiveHypoxemiaAlertCreator extends AlertFactory {

    @Override
    public Alert creatAlert(int patientID, String condition, long timestamp) {
        Random rand = new Random();
        System.out.println("EMERGENCY ALERT for " + patientID+ " at " + timestamp + ": " + condition);
        return new HypotensiveHypoxemiaAlert(rand.nextInt(500000000), patientID, condition, timestamp);
    }
}
