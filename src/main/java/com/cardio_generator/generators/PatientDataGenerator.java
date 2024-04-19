package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Interface for generating patient-specific health data.
 * Implementations of this interface should provide the mechanism to generate data for a single patient
 * and output it using the specified output strategy. This could include data like ECG readings,
 * blood pressure levels, blood saturation levels, and more.
 */
public interface PatientDataGenerator {

    /**
     * Generates health data for a specified patient and outputs it through the given output strategy.
     * The type of data generated can vary based on the implementation (e.g., ECG, blood pressure).
     *
     * @param patientId The unique identifier for the patient for whom data is to be generated.
     * @param outputStrategy The strategy (e.g., console, file, network) used to output the generated data.
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
