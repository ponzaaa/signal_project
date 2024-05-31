package com.alerts.objects;

public class AlertDecorator implements Alert {
    Alert wrapped;

    @Override
    public int getAlertId() {
        return wrapped.getAlertId();
    }

    @Override
    public int getPatientId() {
        return wrapped.getPatientId();
    }

    @Override
    public long getTimestamp() {
        return wrapped.getTimestamp();
    }

    @Override
    public String getCondition() {
        return wrapped.getCondition();
    }
}
