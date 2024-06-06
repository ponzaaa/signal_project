package com.alerts;

import com.alerts.objects.Alert;
import com.data_management.records.Patient;
import java.util.Map;

/**
 * Abstract class extended by {@link SystolicPressureStrategy} and {@link DiastolicPressureStrategy}
 * functioning as a blueprint for the behaviour of the two alerts systems that treat
 * blood pressure-related issues
 */
public abstract class BloodPressureStrategy implements AlertStrategy {
    @Override
    public Alert checkAlert(int patientId, double data, long timestamp, Map<Integer, Patient> patientMap) {
        return null;
    }
}
