package com.alerts.objects;

public class HypotensiveHypoxemiaAlert extends Alert {
    public HypotensiveHypoxemiaAlert(int alertId, int patientId, String condition, long timestamp) {
        super(alertId, patientId, condition, timestamp);
    }
}
