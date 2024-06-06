package com.alerts;

import com.alerts.objects.Alert;
import com.data_management.records.Patient;
import java.util.Map;

/**
 * Strategy Design Pattern implementation for alert system: the process
 * that goes from their detection to their creation and control
 */
public interface AlertStrategy {
    Alert checkAlert(int patientId, double data, long timestamp, Map<Integer, Patient> patientMap);
}

