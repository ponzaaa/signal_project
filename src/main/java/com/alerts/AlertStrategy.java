package com.alerts;

import com.data_management.records.Patient;
import com.data_management.records.PatientRecord;

import java.util.ArrayList;
import java.util.Map;

public interface AlertStrategy {
    void checkAlert(int patientId, double data, long timestamp, Map<Integer, Patient> patientMap);
}

