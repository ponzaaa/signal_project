package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Implements {@link PatientDataGenerator} to simulate the generation of blood saturation data for patients.
 * This class generates realistic fluctuations in blood saturation levels, maintaining values within a healthy range.
 * The generated data is output through the specified {@link OutputStrategy}, making it versatile for different output needs.
 */
public class BloodSaturationDataGenerator implements PatientDataGenerator {
    private static final Random random = new Random();
    private int[] lastSaturationValues;

    /**
     * Constructs a BloodSaturationDataGenerator for the specified number of patients.
     * Initializes the saturation values randomly between 95 and 100 to simulate a baseline healthy saturation level.
     *
     * @param patientCount The number of patients to track and generate saturation data for.
     */
    public BloodSaturationDataGenerator(int patientCount) {
        lastSaturationValues = new int[patientCount + 1];

        // Initialize with baseline saturation values for each patient
        for (int i = 1; i <= patientCount; i++) {
            lastSaturationValues[i] = 95 + random.nextInt(6); // Initializes with a value between 95 and 100
        }
    }

    /**
     * Generates and outputs a new saturation data point for a specific patient.
     * This method simulates small random fluctuations in saturation and ensures the values remain within
     * a realistic range (90 to 100%). The updated data is then output using the provided {@link OutputStrategy}.
     *
     * @param patientId The ID for the patient whose saturation data is to be generated.
     * @param outputStrategy The output strategy to use for sending the generated data.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            // Simulate blood saturation values
            int variation = random.nextInt(3) - 1; // -1, 0, or 1 to simulate small fluctuations
            int newSaturationValue = lastSaturationValues[patientId] + variation;

            // Ensure the saturation stays within a realistic and healthy range
            newSaturationValue = Math.min(Math.max(newSaturationValue, 90), 100);
            lastSaturationValues[patientId] = newSaturationValue;
            outputStrategy.output(patientId, System.currentTimeMillis(), "Saturation",
                    Double.toString(newSaturationValue) + "%");
        } catch (Exception e) {
            System.err.println("An error occurred while generating blood saturation data for patient " + patientId);
            e.printStackTrace(); // This will print the stack trace to help identify where the error occurred.
        }
    }
}
