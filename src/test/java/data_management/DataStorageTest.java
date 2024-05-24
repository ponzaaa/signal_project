package data_management;

import com.alerts.Alert;
import com.alerts.Checkers;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CheckersTest {

    @Test
    void testCheckBloodPressureAlerts() {
        Patient patient = new Patient(1);
        List<PatientRecord> records = new ArrayList<>();
        List<Alert> alerts = new ArrayList<>();

        // Simulate Systolic Pressure records
        records.add(new PatientRecord(1, 100, "SystolicPressure", 1714376789000L));
        records.add(new PatientRecord(1, 110, "SystolicPressure", 1714376789010L));
        records.add(new PatientRecord(1, 190, "SystolicPressure", 1714376789020L)); // Critical threshold

        // Simulate Diastolic Pressure records
        records.add(new PatientRecord(1, 50, "DiastolicPressure", 1714376789030L)); // Critical threshold

        Checkers.checkBloodPressureAlerts(patient, records, alerts);

        assertEquals(2, alerts.size());
        assertEquals("Alert for Patient ID: 1 - Critical Threshold Alert for Systolic Pressure at 1714376789020", alerts.get(0).toString());
        assertEquals("Alert for Patient ID: 1 - Critical Threshold Alert for Diastolic Pressure at 1714376789030", alerts.get(1).toString());
    }

    @Test
    void testCheckSaturationAlerts() {
        Patient patient = new Patient(1);
        List<PatientRecord> records = new ArrayList<>();
        List<Alert> alerts = new ArrayList<>();

        // Simulate Saturation records
        records.add(new PatientRecord(1, 92, "Saturation", 1714376789000L));
        records.add(new PatientRecord(1, 90, "Saturation", 1714376789010L)); // Low saturation
        records.add(new PatientRecord(1, 100, "Saturation", 1714376789020L));
        records.add(new PatientRecord(1, 89, "Saturation", 1714376789030L)); // Rapid drop

        Checkers.checkSaturationAlerts(patient, records, alerts);

        assertEquals(2, alerts.size());
        assertEquals("Alert for Patient ID: 1 - Low Saturation Alert at 1714376789010", alerts.get(0).toString());
        assertEquals("Alert for Patient ID: 1 - Rapid Drop Alert at 1714376789030", alerts.get(1).toString());
    }

    @Test
    void testCheckCombinedAlerts() {
        Patient patient = new Patient(1);
        List<PatientRecord> records = new ArrayList<>();
        List<Alert> alerts = new ArrayList<>();

        // Simulate Systolic Pressure and Saturation records
        records.add(new PatientRecord(1, 85, "SystolicPressure", 1714376789000L)); // Low systolic
        records.add(new PatientRecord(1, 91, "Saturation", 1714376789010L)); // Low saturation

        Checkers.checkCombinedAlerts(patient, records, alerts);

        assertEquals(1, alerts.size());
        assertEquals("Alert for Patient ID: 1 - Hypotensive Hypoxemia Alert at 1714376789000", alerts.get(0).toString());
    }

    @Test
    void testCheckECGAlerts() {
        Patient patient = new Patient(1);
        List<PatientRecord> records = new ArrayList<>();
        List<Alert> alerts = new ArrayList<>();

        // Simulate Heart Rate records
        records.add(new PatientRecord(1, 45, "HeartRate", 1714376789000L)); // Abnormal heart rate

        Checkers.checkECGAlerts(patient, records, alerts);

        assertEquals(1, alerts.size());
        assertEquals("Alert for Patient ID: 1 - Abnormal Heart Rate Alert at 1714376789000", alerts.get(0).toString());
    }

    @Test
    void testCheckIncreasingOrDecreasingTrendTimestamp() {
        List<PatientRecord> records = new ArrayList<>();

        // Simulate Systolic Pressure records with a trend
        records.add(new PatientRecord(1, 100, "SystolicPressure", 1714376789000L));
        records.add(new PatientRecord(1, 115, "SystolicPressure", 1714376789010L));
        records.add(new PatientRecord(1, 130, "SystolicPressure", 1714376789020L));

        Long trendTimestamp = Checkers.checkIncreasingOrDecreasingTrendTimestamp(records);

        assertNotNull(trendTimestamp);
        assertEquals(1714376789020L, trendTimestamp);
    }

    @Test
    void testCheckCriticalThresholdTimestamp() {
        List<PatientRecord> records = new ArrayList<>();

        // Simulate Systolic Pressure records with a critical value
        records.add(new PatientRecord(1, 100, "SystolicPressure", 1714376789000L));
        records.add(new PatientRecord(1, 190, "SystolicPressure", 1714376789010L)); // Above upper threshold

        Long thresholdTimestamp = Checkers.checkCriticalThresholdTimestamp(records, 180, 90);

        assertNotNull(thresholdTimestamp);
        assertEquals(1714376789010L, thresholdTimestamp);
    }

    @Test
    void testCheckLowSaturationTimestamp() {
        List<PatientRecord> records = new ArrayList<>();

        // Simulate Saturation records with low value
        records.add(new PatientRecord(1, 95, "Saturation", 1714376789000L));
        records.add(new PatientRecord(1, 89, "Saturation", 1714376789010L)); // Below threshold

        Long lowSaturationTimestamp = Checkers.checkLowSaturationTimestamp(records);

        assertNotNull(lowSaturationTimestamp);
        assertEquals(1714376789010L, lowSaturationTimestamp);
    }

    @Test
    void testCheckRapidDropTimestamp() {
        List<PatientRecord> records = new ArrayList<>();

        // Simulate Saturation records with rapid drop
        records.add(new PatientRecord(1, 95, "Saturation", 1714376789000L));
        records.add(new PatientRecord(1, 85, "Saturation", 1714376789010L)); // Rapid drop

        Long rapidDropTimestamp = Checkers.checkRapidDropTimestamp(records);

        assertNotNull(rapidDropTimestamp);
        assertEquals(1714376789010L, rapidDropTimestamp);
    }

    @Test
    void testCheckHypotensiveHypoxemiaTimestamp() {
        List<PatientRecord> systolicRecords = new ArrayList<>();
        List<PatientRecord> saturationRecords = new ArrayList<>();

        // Simulate Systolic Pressure and Saturation records
        systolicRecords.add(new PatientRecord(1, 85, "SystolicPressure", 1714376789000L)); // Low systolic
        saturationRecords.add(new PatientRecord(1, 89, "Saturation", 1714376789010L)); // Low saturation

        Long hypoHypoxTimestamp = Checkers.checkHypotensiveHypoxemiaTimestamp(systolicRecords, saturationRecords);

        assertNotNull(hypoHypoxTimestamp);
        assertEquals(1714376789000L, hypoHypoxTimestamp);
    }

    @Test
    void testCheckAbnormalHeartRateTimestamp() {
        List<PatientRecord> records = new ArrayList<>();

        // Simulate Heart Rate records with abnormal value
        records.add(new PatientRecord(1, 45, "HeartRate", 1714376789000L)); // Below threshold

        Long abnormalHeartRateTimestamp = Checkers.checkAbnormalHeartRateTimestamp(records);

        assertNotNull(abnormalHeartRateTimestamp);
        assertEquals(1714376789000L, abnormalHeartRateTimestamp);
    }
}

