package com.alerts.objects.decorators;

import java.util.Comparator;
import java.util.List;

public class PriorityAlertSorter implements Comparator<PriorityAlertDecorator> {

    @Override
    public int compare(PriorityAlertDecorator a1, PriorityAlertDecorator a2) {
        if (a1.getPriority() > a2.getPriority()) {
            return 1;
        } else if (a1.getPriority() < a2.getPriority()) {
            return -1;
        } else {
            return 0;
        }
    }

    public void sort(List<PriorityAlertDecorator> alerts) {
        alerts.sort(this);
    }

}
