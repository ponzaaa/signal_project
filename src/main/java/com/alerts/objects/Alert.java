package com.alerts.objects;

public abstract class Alert {

    private int alertId;
    private int patientId;
    private String condition;
    private long timestamp;

    public Alert(int alertId, int patientId, String condition, long timestamp) {
        this.alertId = alertId;
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    public int getAlertId() {
        return alertId;
    }

    public int getPatientId() {
        return patientId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getCondition() {
        return condition;
    }
}
