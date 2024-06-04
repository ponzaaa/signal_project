package com.alerts.objects.decorators;

import java.util.Comparator;
import java.util.List;

public class RepeatedAlertSorter implements Comparator<RepeatedAlertDecorator> {

    @Override
    public int compare(RepeatedAlertDecorator a1, RepeatedAlertDecorator a2) {
        if (a1.getTimeToCheck() > a2.getTimeToCheck()) {
            return 1;
        } else if (a1.getTimeToCheck() < a2.getTimeToCheck()) {
            return -1;
        } else {
            return 0;
        }
    }

    public void sort(List<RepeatedAlertDecorator> alerts) {
        alerts.sort(this);
    }

}
