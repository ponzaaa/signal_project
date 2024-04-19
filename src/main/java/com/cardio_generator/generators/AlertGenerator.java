package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Implements {@link PatientDataGenerator} to simulate the generation of alert conditions for patients.
 * This class manages alert states for each patient, where an alert can either be triggered or resolved.
 * The alert logic includes a stochastic model to determine alert transitions, which can be adjusted by
 * modifying the lambda parameter for rate of alerts per period.
 */
public class AlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random();
    private boolean[] alertStates; /* false = resolved, true = pressed. Changed variable name from AlertStates,
                                    and everywhere it appeared */

    /**
     * Constructs an AlertGenerator for the specified number of patients.
     * Initializes all patient alert states to false, indicating that there are no alerts initially.
     *
     * @param patientCount The number of patients to manage alerts for.
     */
    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

    /**
     * Generates and outputs alert data for a specific patient. This method manages the state transitions
     * of alerts based on random probabilities: resolving active alerts and triggering new alerts.
     * Resolved alerts are marked with a "resolved" message, and new alerts are marked with a "triggered" message.
     *
     * @param patientId The ID for the patient whose alert data is to be generated.
     * @param outputStrategy The output strategy to use for sending the generated data.
     */
    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                double lambda = 0.1; /* Average rate (alerts per period), adjust based on desired frequency
                                        Changed the variable name from Lambda, so that it follows the convention
                                        */

                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
