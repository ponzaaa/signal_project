package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A file-based implementation of the {@link OutputStrategy} for outputting patient health data.
 * This class writes the output data to files, organizing data by type (label) into separate files
 * within a specified base directory.
 *
 * This strategy ensures that data for the same metrics are collated in the same file, making it easier
 * to review and analyze data by category.
 */
public class FileOutputStrategy implements OutputStrategy {

    private String baseDirectory; /*Changed from BaseDirectory, such that it now follows Google Java convention,
                                    and everywhere it appeared */

    public final ConcurrentHashMap<String, String> file_map = new ConcurrentHashMap<>();

    /**
     * Constructs a with the specified base directory for file output.
     *
     * @param baseDirectory The directory path where output files will be created and data will be written.
     */
    public void fileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs health data to files organized by data type. Each call to this method writes a single
     * entry formatted as "Patient ID: <id>, Timestamp: <timestamp>, Label: <label>, Data: <data>"
     * to the appropriate file based on the label.
     *
     * @param patientId The unique identifier for the patient whose data is to be outputted.
     * @param timestamp The timestamp at which the data was generated.
     * @param label     A label describing the type of data being output.
     * @param data      The actual data to be output, formatted as a String.
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the FilePath variable
        String FilePath = file_map.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(FilePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + FilePath + ": " + e.getMessage());
        }
    }
}