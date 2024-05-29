package com.alerts.creators;

public class HypotensiveHypoxemiaAlertCreator extends AlertFactory {

    @Override
    public void creatAlert(int patientID, String condition, long timestamp) {
        System.out.println("EMERGENCY ALERT for " + patientID+ " at " + timestamp + ": " + condition);
    }
}
