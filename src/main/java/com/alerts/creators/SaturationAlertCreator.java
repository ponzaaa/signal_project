package com.alerts.creators;

public class SaturationAlertCreator extends AlertFactory {
    @Override
    public void creatAlert(int patientID, String condition, long timestamp) {
        System.out.println("ALERT for " + patientID+ " at " + timestamp + ": " + condition);
    }
}
