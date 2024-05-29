package com.cardio_generator.generators;

import java.util.Random;
import com.cardio_generator.outputs.OutputStrategy;

public class ECGDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private final double[] heartRate;

    public ECGDataGenerator(int patientCount) {
        heartRate = new double[patientCount + 1];
        // Initialize the last ECG value for each patient
        for (int i = 1; i <= patientCount; i++) {
            heartRate[i] = 70; // Initial ECG value can be set to 70
        }
    }

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            double rand = random.nextDouble()*2;
            boolean rand2 = random.nextBoolean();
            double newHeartRate;
            if (rand2) {
                newHeartRate = heartRate[patientId] + rand; // new heart rate is previous +2
            } else {
                newHeartRate = heartRate[patientId] - rand; // new heart rate is previous -2
            } outputStrategy.output(patientId, System.currentTimeMillis(), "ECG", Double.toString(newHeartRate));
            heartRate[patientId] = newHeartRate;
        } catch (Exception e) {
            System.err.println("An error occurred while generating ECG data for patient " + patientId);
        }
    }


}