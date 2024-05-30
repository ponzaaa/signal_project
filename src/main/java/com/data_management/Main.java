package com.data_management;

import com.cardio_generator.HealthDataSimulator;
import com.cardio_generator.outputs.OutputStrategy;
import com.cardio_generator.outputs.WebSocketOutputStrategy;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main {

    public static void main(String[] args) throws IOException {
        int patientCount = 100;
        ScheduledExecutorService scheduler=Executors.newScheduledThreadPool(patientCount * 4);
        OutputStrategy outputStrategy = new WebSocketOutputStrategy(8080);
        DataStorage dataStorage = DataStorage.getDataStorage();
        HealthDataSimulator simulator = HealthDataSimulator.getHealthDataSimulator(patientCount, scheduler
                , outputStrategy);
    }
}
