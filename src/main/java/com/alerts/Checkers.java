package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public class Checkers {

    /**
     * Gets data from that patient's record, filters for Systolic and Diastolic pressure,
     * checks for increasing or decreasing trends, also checks for critical thresholds:
     * that the value lies between 180<x<90 for systolic pressure and 120<x<60 for diastolic pressure
     * @param patient the patient for which we analyze the data
     * @param records that patient's full medical record
     */
    public static void checkBloodPressureAlerts(Patient patient, List<PatientRecord> records, List<Alert> alerts) {
        List<PatientRecord> systolicRecords = filterRecordsByLabel(records, "SystolicPressure");
        List<PatientRecord> diastolicRecords = filterRecordsByLabel(records, "DiastolicPressure");

        // Trend Alert
        Long trendTimestamp = checkIncreasingOrDecreasingTrendTimestamp(systolicRecords);
        if (trendTimestamp != null) {
            triggerAlert(patient.getId(), "Trend Alert for Systolic Pressure", trendTimestamp, alerts);
        }
        trendTimestamp = checkIncreasingOrDecreasingTrendTimestamp(diastolicRecords);
        if (trendTimestamp != null) {
            triggerAlert(patient.getId(), "Trend Alert for Diastolic Pressure", trendTimestamp, alerts);
        }

        // Critical Threshold Alert
        Long thresholdTimestamp = checkCriticalThresholdTimestamp(systolicRecords, 180, 90);
        if (thresholdTimestamp != null) {
            triggerAlert(patient.getId(), "Critical Threshold Alert for Systolic Pressure", thresholdTimestamp, alerts);
        }
        thresholdTimestamp = checkCriticalThresholdTimestamp(diastolicRecords, 120, 60);
        if (thresholdTimestamp != null) {
            triggerAlert(patient.getId(), "Critical Threshold Alert for Diastolic Pressure", thresholdTimestamp, alerts);
        }
    }

    /**
     * Gets data from that patient's record, filters for blood saturation
     * and for critical thresholds: that the value isn't lower than 92 nor
     * that it has a rapid drop
     * @param patient the patient for which we analyze the data
     * @param records that patient's full medical record
     */
    public static void checkSaturationAlerts(Patient patient, List<PatientRecord> records, List<Alert> alerts) {
        List<PatientRecord> saturationRecords = filterRecordsByLabel(records, "Saturation");

        // Low Saturation Alert
        Long lowSaturationTimestamp = checkLowSaturationTimestamp(saturationRecords);
        if (lowSaturationTimestamp != null) {
            triggerAlert(patient.getId(), "Low Saturation Alert", lowSaturationTimestamp, alerts);
        }

        // Rapid Drop Alert
        Long rapidDropTimestamp = checkRapidDropTimestamp(saturationRecords);
        if (rapidDropTimestamp != null) {
            triggerAlert(patient.getId(), "Rapid Drop Alert", rapidDropTimestamp, alerts);
        }
    }

    /**
     * Gets data from that patient's record, filters for Systolic pressure and low saturation,
     * checks that the two things don't happen at once
     * @param patient the patient for which we analyze the data
     * @param records that patient's full medical record
     */
    public static void checkCombinedAlerts(Patient patient, List<PatientRecord> records, List<Alert> alerts) {
        List<PatientRecord> systolicRecords = filterRecordsByLabel(records, "SystolicPressure");
        List<PatientRecord> saturationRecords = filterRecordsByLabel(records, "Saturation");

        // Hypotensive Hypoxemia Alert
        Long hypoHypoxTimestamp = checkHypotensiveHypoxemiaTimestamp(systolicRecords, saturationRecords);
        if (hypoHypoxTimestamp != null) {
            triggerAlert(patient.getId(), "Hypotensive Hypoxemia Alert", hypoHypoxTimestamp, alerts);
        }
    }

    /**
     * Checks the heart rate of a specified patient, looking for abnormal values,
     * as well as irregular heart rate (arrhythmia)
     * @param patient patient for which we want to analyze data
     * @param records that patient's record, to be filtered for the correct one
     */
    public static void checkECGAlerts(Patient patient, List<PatientRecord> records, List<Alert> alerts) {
        List<PatientRecord> heartRateRecords = filterRecordsByLabel(records, "HeartRate");

        // Abnormal Heart Rate Alert
        Long abnormalHeartRateTimestamp = checkAbnormalHeartRateTimestamp(heartRateRecords);
        if (abnormalHeartRateTimestamp != null) {
            triggerAlert(patient.getId(), "Abnormal Heart Rate Alert", abnormalHeartRateTimestamp, alerts);
        }

        // Irregular Beat Alert (commented out if not implemented)
//        if (checkIrregularBeat(heartRateRecords)) {
//            triggerAlert(patient.getId(), "Irregular Beat Alert", getLatestTimestamp(heartRateRecords));
//        }
    }

    /**
     * Takes the records of all data types of a patient and the label of the one
     * we want to get, and returns it
     * @param records all patient's data record
     * @param label the type of data we want the record for
     * @return the record for that label
     */
    private static List<PatientRecord> filterRecordsByLabel(List<PatientRecord> records, String label) {
        return records.stream().filter(record -> record.getRecordType().equals(label)).toList();
    }

    /**
     * Checks for trends analyzing 3 data points at the time
     * @param records the records of all patient's medical data
     * @return the timestamp of the record causing the trend alert if there is an increasing or decreasing trend,
     * null otherwise
     */
    private static Long checkIncreasingOrDecreasingTrendTimestamp(List<PatientRecord> records) {
        if (records.size() < 3) return null;
        for (int i = 2; i < records.size(); i++) {
            double first = records.get(i - 2).getMeasurementValue();
            double second = records.get(i - 1).getMeasurementValue();
            double third = records.get(i).getMeasurementValue();
            if ((second - first > 10 && third - second > 10) || (first - second > 10 && second - third > 10)) {
                return records.get(i).getTimestamp();
            }
        }
        return null;
    }

    /**
     * Checks if any record exceeds the critical threshold.
     * @param records the records of all patient's medical data
     * @param upper the upper threshold
     * @param lower the lower threshold
     * @return the timestamp of the record exceeding the threshold if any,
     * null otherwise
     */
    private static Long checkCriticalThresholdTimestamp(List<PatientRecord> records, double upper, double lower) {
        return records.stream()
                .filter(record -> record.getMeasurementValue() > upper || record.getMeasurementValue() < lower)
                .map(PatientRecord::getTimestamp)
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks if any record has a low saturation level.
     * @param records the records of all patient's medical data
     * @return the timestamp of the record with low saturation if any,
     * null otherwise
     */
    private static Long checkLowSaturationTimestamp(List<PatientRecord> records) {
        return records.stream()
                .filter(record -> record.getMeasurementValue() < 92)
                .map(PatientRecord::getTimestamp)
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks for a rapid drop in saturation levels.
     * @param records the records of all patient's medical data
     * @return the timestamp of the record causing the rapid drop if any,
     * null otherwise
     */
    private static Long checkRapidDropTimestamp(List<PatientRecord> records) {
        if (records.size() < 2) return null;
        for (int i = 1; i < records.size(); i++) {
            double first = records.get(i - 1).getMeasurementValue();
            double second = records.get(i).getMeasurementValue();
            if (first - second >= 5 && (second - first) <= 600000) {
                return records.get(i).getTimestamp();
            }
        }
        return null;
    }

    /**
     * Check for condition occurring when both systolic pressure is low (under 90),
     * and saturation also drops under 92
     * @param systolicRecords patient's record of systolic pressure
     * @param saturationRecords patient's record of blood saturation
     * @return the timestamp of the earlier record causing the alert if both conditions are met,
     * null otherwise
     */
    private static Long checkHypotensiveHypoxemiaTimestamp(List<PatientRecord> systolicRecords, List<PatientRecord> saturationRecords) {
        boolean lowSystolic = systolicRecords.stream().anyMatch(record -> record.getMeasurementValue() < 90);
        boolean lowSaturation = saturationRecords.stream().anyMatch(record -> record.getMeasurementValue() < 92);
        if (lowSystolic && lowSaturation) {
            Long systolicTimestamp = systolicRecords.stream()
                    .filter(record -> record.getMeasurementValue() < 90)
                    .map(PatientRecord::getTimestamp)
                    .findFirst()
                    .orElse(null);
            Long saturationTimestamp = saturationRecords.stream()
                    .filter(record -> record.getMeasurementValue() < 92)
                    .map(PatientRecord::getTimestamp)
                    .findFirst()
                    .orElse(null);
            return systolicTimestamp != null && saturationTimestamp != null
                    ? Math.min(systolicTimestamp, saturationTimestamp)
                    : null;
        }
        return null;
    }

    /**
     * Checks for concerning/emergency values of heart rate, such as
     * above 100 or below 50
     * @param records the full patient's medical data
     * @return the timestamp of the record with concerning heart rate if any,
     * null otherwise
     */
    private static Long checkAbnormalHeartRateTimestamp(List<PatientRecord> records) {
        return records.stream()
                .filter(record -> record.getMeasurementValue() < 50 || record.getMeasurementValue() > 100)
                .map(PatientRecord::getTimestamp)
                .findFirst()
                .orElse(null);
    }

    /**
     * Triggers an alert by creating an Alert object and adding it to the alerts list.
     * @param patientId the patient for which the alert was triggered
     * @param alertMessage the message regarding alert's information
     * @param timestamp the timestamp of the data triggering the alert
     */
    private static void triggerAlert(int patientId, String alertMessage, long timestamp, List<Alert> alerts) {
        System.out.println("Alert for Patient ID: " + patientId + " - " + alertMessage + " at " + timestamp);
        Alert alert = new Alert(String.valueOf(patientId), alertMessage, timestamp);
        alerts.add(alert); // Add the alert to the list
    }

}
