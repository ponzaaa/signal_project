package com.alerts.objects;

import java.util.Collection;

public interface Alert {

    int getAlertId();

    int getPatientId();

    long getTimestamp();

    String getCondition();
}
