package com.alerts.objects;

public interface Alert {

    int getAlertId();

    int getPatientId();

    long getTimestamp();

    String getCondition();
}
