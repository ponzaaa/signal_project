package com.alerts.objects;

public class ECGAlert extends Alert {

    public ECGAlert(int alertId, int patientId, String condition, long timestamp) {
        super(alertId, patientId, condition, timestamp);
    }

}
