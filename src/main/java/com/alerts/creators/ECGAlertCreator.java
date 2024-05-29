package com.alerts.creators;

public class ECGAlertCreator extends AlertFactory {

    @Override
    public void creatAlert(int patientID, String condition, long timestamp) {
        System.out.println("ALERT for " + patientID+ " at " + timestamp + ": " + condition);
    }
}
