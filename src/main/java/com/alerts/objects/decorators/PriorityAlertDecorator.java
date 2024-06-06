package com.alerts.objects.decorators;

import com.alerts.objects.Alert;

public class PriorityAlertDecorator extends AlertDecorator {
    private Alert wrappedAlert; // contains one of the other alerts
    private int priority; // level of priority 0 to 3

    public PriorityAlertDecorator(Alert wrapped, int priority) {
        super(wrapped);
        this.priority=priority;
    }

    public PriorityAlertDecorator(Alert wrapped) {
        super(wrapped);
    }

    public void setPriority(int priority) {
        assert priority >= 0 && priority <= 3;
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }

    public Alert getWrappedAlert() {
        return this.wrappedAlert;
    }

}
