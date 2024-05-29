package com.alerts;

import com.data_management.records.Patient;
import java.util.Map;

public class BloodPressureStrategy implements AlertStrategy {
    @Override
    public void checkAlert(int patientId, double data, long timestamp, Map<Integer, Patient> patientMap) {
    }
}
