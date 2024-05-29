package com.alerts.objects;

public class SaturationAlert extends Alert {
    public SaturationAlert(int alertId, int patientId, String condition, long timestamp) {
        super(alertId, patientId, condition, timestamp);
    }
}
