package com.alerts;

public interface AlertStrategy {
    void checkAlert(int patientId, double data, long timestamp);
}

