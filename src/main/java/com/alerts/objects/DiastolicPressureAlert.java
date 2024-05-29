package com.alerts.objects;

public class DiastolicPressureAlert extends Alert {
    public DiastolicPressureAlert(int alertId, int patientId, String condition, long timestamp) {
        super(alertId, patientId, condition, timestamp);
    }
}
