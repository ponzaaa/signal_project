package com.alerts;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.stream.Collectors;

import java.util.List;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient, long startTime, long endTime) {
        List<PatientRecord> records = dataStorage.getRecords(patient.getId(), startTime, endTime);


        // Check for Blood Pressure Data Alerts
        checkBloodPressureAlerts(patient, records);

        // Check for Blood Saturation Data Alerts
        checkSaturationAlerts(patient, records);

        // Check for Combined Alerts
        checkCombinedAlerts(patient, records);

        // Check for ECG Data Alerts
        checkECGAlerts(patient, records);
    }

    private void checkBloodPressureAlerts(Patient patient, List<PatientRecord> records) {
        List<PatientRecord> systolicRecords = filterRecordsByLabel(records, "SystolicPressure");
        List<PatientRecord> diastolicRecords = filterRecordsByLabel(records, "DiastolicPressure");

        // Trend Alert
        if (checkIncreasingTrend(systolicRecords) || checkDecreasingTrend(systolicRecords)) {
            triggerAlert(patient.getId(), "Trend Alert for Systolic Pressure");
        }
        if (checkIncreasingTrend(diastolicRecords) || checkDecreasingTrend(diastolicRecords)) {
            triggerAlert(patient.getId(), "Trend Alert for Diastolic Pressure");
        }

        // Critical Threshold Alert
        if (checkCriticalThreshold(systolicRecords, 180, 90)) {
            triggerAlert(patient.getId(), "Critical Threshold Alert for Systolic Pressure");
        }
        if (checkCriticalThreshold(diastolicRecords, 120, 60)) {
            triggerAlert(patient.getId(), "Critical Threshold Alert for Diastolic Pressure");
        }
    }

    private void checkSaturationAlerts(Patient patient, List<PatientRecord> records) {
        List<PatientRecord> saturationRecords = filterRecordsByLabel(records, "Saturation");

        // Low Saturation Alert
        if (checkLowSaturation(saturationRecords)) {
            triggerAlert(patient.getId(), "Low Saturation Alert");
        }

        // Rapid Drop Alert
        if (checkRapidDrop(saturationRecords)) {
            triggerAlert(patient.getId(), "Rapid Drop Alert");
        }
    }

    private void checkCombinedAlerts(Patient patient, List<PatientRecord> records) {
        List<PatientRecord> systolicRecords = filterRecordsByLabel(records, "SystolicPressure");
        List<PatientRecord> saturationRecords = filterRecordsByLabel(records, "Saturation");

        // Hypotensive Hypoxemia Alert
        if (checkHypotensiveHypoxemia(systolicRecords, saturationRecords)) {
            triggerAlert(patient.getId(), "Hypotensive Hypoxemia Alert");
        }
    }

    private void checkECGAlerts(Patient patient, List<PatientRecord> records) {
        List<PatientRecord> heartRateRecords = filterRecordsByLabel(records, "HeartRate");

        // Abnormal Heart Rate Alert
        if (checkAbnormalHeartRate(heartRateRecords)) {
            triggerAlert(patient.getId(), "Abnormal Heart Rate Alert");
        }

        // Irregular Beat Alert
        if (checkIrregularBeat(heartRateRecords)) {
            triggerAlert(patient.getId(), "Irregular Beat Alert");
        }
    }

    private List<PatientRecord> filterRecordsByLabel(List<PatientRecord> records, String label) {
        return records.stream().filter(record -> record.getRecordType().equals(label)).toList();
    }

    private boolean checkIncreasingTrend(List<PatientRecord> records) {
        if (records.size() < 3) return false;
        for (int i = 2; i < records.size(); i++) {
            double first = records.get(i - 2).getMeasurementValue();
            double second = records.get(i - 1).getMeasurementValue();
            double third = records.get(i).getMeasurementValue();
            if (second - first > 10 && third - second > 10) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDecreasingTrend(List<PatientRecord> records) {
        if (records.size() < 3) return false;
        for (int i = 2; i < records.size(); i++) {
            double first = records.get(i - 2).getMeasurementValue();
            double second = records.get(i - 1).getMeasurementValue();
            double third = records.get(i).getMeasurementValue();
            if (first - second > 10 && second - third > 10) {
                return true;
            }
        }
        return false;
    }

    private boolean checkCriticalThreshold(List<PatientRecord> records, double upper, double lower) {
        return records.stream().anyMatch(record -> record.getMeasurementValue() > upper || record.getMeasurementValue() < lower);
    }

    private boolean checkLowSaturation(List<PatientRecord> records) {
        return records.stream().anyMatch(record -> record.getMeasurementValue() < 92);
    }

    private boolean checkRapidDrop(List<PatientRecord> records) {
        if (records.size() < 2) return false;
        for (int i = 1; i < records.size(); i++) {
            double first = records.get(i - 1).getMeasurementValue();
            double second = records.get(i).getMeasurementValue();
            if (first - second >= 5 && (second - first) <= 600000) {
                return true;
            }
        }
        return false;
    }

    private boolean checkHypotensiveHypoxemia(List<PatientRecord> systolicRecords, List<PatientRecord> saturationRecords) {
        boolean lowSystolic = systolicRecords.stream().anyMatch(record -> record.getMeasurementValue() < 90);
        boolean lowSaturation = saturationRecords.stream().anyMatch(record -> record.getMeasurementValue() < 92);
        return lowSystolic && lowSaturation;
    }

    private boolean checkAbnormalHeartRate(List<PatientRecord> records) {
        return records.stream().anyMatch(record -> record.getMeasurementValue() < 50 || record.getMeasurementValue() > 100);
    }

    private boolean checkIrregularBeat(List<PatientRecord> records) {
        // Implement irregular beat pattern detection logic
        return false;
    }

    private void triggerAlert(int patientId, String alertMessage) {
        System.out.println("Alert for Patient ID: " + patientId + " - " + alertMessage);
        // Implement additional logic for handling alerts
    }

}
