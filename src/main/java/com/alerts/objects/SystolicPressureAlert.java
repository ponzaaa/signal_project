package com.alerts.objects;

public class SystolicPressureAlert extends Alert {
    public SystolicPressureAlert(int alertId, int patientId, String condition, long timestamp) {
        super(alertId, patientId, condition, timestamp);
    }
}
