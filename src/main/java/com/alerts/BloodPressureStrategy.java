package com.alerts;

import com.alerts.objects.Alert;
import com.data_management.records.Patient;
import java.util.Map;

public abstract class BloodPressureStrategy implements AlertStrategy {
    @Override
    public Alert checkAlert(int patientId, double data, long timestamp, Map<Integer, Patient> patientMap) {
        return null;
    }
}
