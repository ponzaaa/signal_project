package com.data_management;

import com.cardio_generator.HealthDataSimulator;
import com.cardio_generator.outputs.OutputStrategy;
import com.cardio_generator.outputs.WebSocketOutputStrategy;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main {

    public static void main(String[] args) throws IOException {
        start(args);
    }

    public static void start(String[] args) throws IOException {

        // Default settings
        int patientCount = 100;
        OutputStrategy outputStrategy = new WebSocketOutputStrategy(8080);

        // Initialization of the System
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(patientCount * 4);
        HealthDataSimulator simulator = HealthDataSimulator.getHealthDataSimulator(patientCount, scheduler
                , outputStrategy);
        simulator.parseArguments(args);
        List<Integer> patientIds = simulator.initializePatientIds(patientCount);
        Collections.shuffle(patientIds);

        // Proper output
        simulator.scheduleTasksForPatients(patientIds);
    }

}
