package com.alerts.objects.decorators;

import com.alerts.objects.Alert;

public class RepeatedAlertDecorator extends AlertDecorator {

    private Alert wrapped;
    private int timeToCheck;

    public RepeatedAlertDecorator(Alert wrapped, int timeToCheck) {
        super(wrapped);
        this.timeToCheck=timeToCheck;
    }

    public void setTimeToCheck(int timeToCheck) {
        this.timeToCheck=timeToCheck;
    }

    public int getTimeToCheck() {
        return timeToCheck;
    }
}
