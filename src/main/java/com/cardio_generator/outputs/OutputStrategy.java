package com.cardio_generator.outputs;

/**
 * Interface for outputting patient health data. Implementations of this interface
 * define specific methods for handling the output of health data.
 */
public interface OutputStrategy {

    /**
     * Outputs health data for a given patient at a specified timestamp.
     * The method is responsible for formatting and directing the data to an appropriate output medium
     * as defined by the implementation
     *
     * @param patientId The identifier for the patient whose data is to be outputted.
     * @param timestamp The timestamp at which the data was generated, typically represented as
     * @param label     A label describing the type of data being output
     * @param data      The actual data to be output, formatted as a String.
     */
    void output(int patientId, long timestamp, String label, String data);
}
