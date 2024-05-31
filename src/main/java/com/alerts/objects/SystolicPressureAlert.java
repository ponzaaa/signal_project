package com.alerts.objects;

public class SystolicPressureAlert implements Alert {

    private final int alertId;
    private final int patientId;
    private final String condition;
    private final long timestamp;

    public SystolicPressureAlert(int alertId, int patientId, String condition, long timestamp) {
        this.alertId = alertId;
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    @Override
    public int getAlertId() {
        return alertId;
    }

    @Override
    public int getPatientId() {
        return patientId;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String getCondition() {
        return condition;
    }
}
