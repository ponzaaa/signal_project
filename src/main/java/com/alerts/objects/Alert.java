package com.alerts.objects;

/**
 * Interface representing an Alert object and its getters
 */
public interface Alert {

    int getAlertId();

    int getPatientId();

    long getTimestamp();

    String getCondition();
}
